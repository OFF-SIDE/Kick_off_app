package com.test.kick_off_app

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.test.kick_off_app.databinding.ActivityLoginBinding
import com.test.kick_off_app.functions.LoadingDialog
import com.test.kick_off_app.functions.SharedPrefManager
import com.test.kick_off_app.functions.showToast
import com.test.kick_off_app.ui.login.LoginViewModel


/*
카카오 로그인 화면.
1. 저장된 액세스 토큰이 있다면, 권한 확인을 통해 로그인. 만료시 2번
2. 저장된 카카오 토큰이 있다면 kakaoLogin api를 통해 액세스 토큰 요청 후 로그인
3. 둘다 없다면 카카오 토큰을 얻고 2번

로그인
kakaoLogin api에서 에러코드를 통해 회원정보가 없을 시 회원가입화면으로
*/

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    val manager: SharedPrefManager by lazy {
        SharedPrefManager.getInstance()
    }
    lateinit var signUpLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // 뒤로 가기 시 실패로 결과 전달
                val intent = Intent().apply {
                    putExtra("SIGNUP_SUCCESS", false)
                }
                setResult(RESULT_OK, intent)
                finish()
            }
        })

        //RegisterActivityResult(Contract자료형, 콜백메서드)를 이용해서
        //ActivityResultLauncher를 초기화 해준다.

        signUpLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
                if (result.resultCode == RESULT_OK) {
                    result.data?.let {data ->
                        val isSuccess = data.getBooleanExtra("SIGNUP_SUCCESS", false)
                        if (isSuccess) {
                            // 회원가입 성공 시
                            showToast("회원가입 성공! 자동 로그인 중...")

                            loginViewModel.auth()
                        } else {
                            // 회원가입 실패 시 실행할 코드
                            val errorCode = data.getIntExtra("ERROR_CODE", -1)
                            if(errorCode==1002){
                                showToast("이미 가입된 회원이 있습니다")
                            }
                            else{
                                showToast("회원가입을 실패했습니다.")
                            }
                        }
                    }
                }
            }

        val dialog = LoadingDialog(this)

        // 1. 저장된 액세스 토큰이 있으면 자동로그인
        dialog.show()
        loginViewModel.auth()

        loginViewModel.viewEvent.observe(this) {
            it.getContentIfNotHandled()?.let { event ->
                when (event) {
                    LoginViewModel.EVENT_AUTH_SUCCESS -> {
                        Log.d("Access token", "valid")
                        showToast("액세스 토큰 유효. 자동 로그인.")

                        dialog.dismiss()

                        val intent = Intent().apply {
                            // 전달할 데이터
                            putExtra("LOGIN_SUCCESS", true)
                        }
                        setResult(RESULT_OK, intent)

                        //엑티비티 종료
                        if (!isFinishing) finish()
                    }
                    LoginViewModel.EVENT_AUTH_WRONG_TOKEN -> {
                        Log.d("Auth fail", "wrong access token")
                        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                            if (error == null && tokenInfo != null) {
                                // 카카오 토큰은 있는 경우
                                Log.d("kakao token", "exist")
                                showToast("토큰 정보 보기 성공" +
                                        "\n회원번호: ${tokenInfo.id}" +
                                        "\n만료시간: ${tokenInfo.expiresIn} 초")

                                // 서비스 서버에 회원번호를 보내 인증
                                loginViewModel.kakaoLogin(tokenInfo.id.toString())
                            }
                            else{
                                // 카카오 토큰도 없는 경우
                                Log.d("kakao token", "not exist")

                                dialog.dismiss()
                            }
                        }
                    }
                    LoginViewModel.EVENT_KAKAO_LOGIN_SUCCESS -> {
                        loginViewModel.auth()
                    }
                    LoginViewModel.EVENT_KAKAO_LOGIN_NO_USER -> {
                        // 회원가입 필요
                        /*
                        val options = ActivityOptions.makeSceneTransitionAnimation(
                            this,
                            android.util.Pair(binding.textTitle, "titleTran"),
                            android.util.Pair(binding.imageLogo, "imageTran")
                        )

                        val intent = Intent(this, RegisterActivity::class.java)
                        startActivity(intent, options.toBundle())
                         */

                        dialog.dismiss()
                        val intent = Intent(this, RegisterActivity::class.java)
                        signUpLauncher.launch(intent)
                    }
                }
            }
        }


        // 카카오 계정 로그인용 콜백
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Toast.makeText(this, "카카오 계정 로그인 실패", Toast.LENGTH_SHORT).show()
            }
            else if (token != null){
                UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                    if (error == null && tokenInfo != null) {
                        showToast("카카오 계정 로그인 성공 후 토큰보기 성공")

                        loginViewModel.kakaoLogin(tokenInfo.id.toString())
                    }
                }
            }
        }

        binding.buttonLogin.setOnClickListener {
            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (error != null) {
                        Toast.makeText(this, "카카오톡 로그인 실패", Toast.LENGTH_SHORT).show()

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                    } else if (token != null) {
                        Toast.makeText(this, "카카오톡 로그인 성공", Toast.LENGTH_SHORT).show()


                        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                            if (error == null && tokenInfo != null) {
                                showToast("카카오톡 로그인 성공 후 토큰보기 성공")

                                loginViewModel.kakaoLogin(tokenInfo.id.toString())
                            }
                        }
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }

        }

    }
}