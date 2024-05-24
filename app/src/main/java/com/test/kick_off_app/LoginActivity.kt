package com.test.kick_off_app

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.core.view.GestureDetectorCompat
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.test.kick_off_app.databinding.ActivityLoginBinding
import com.test.kick_off_app.databinding.ActivityMainBinding


class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 로그인 정보 확인
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Toast.makeText(this, "로그인 기록 없음", Toast.LENGTH_SHORT).show()
            } else if (tokenInfo != null) {
                Toast.makeText(this, "자동 로그인" +
                        "\n회원번호: ${tokenInfo.id}" +
                        "\n만료시간: ${tokenInfo.expiresIn} 초", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        }

        // 카카오 계정 로그인용 콜백
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Toast.makeText(this, "카카오 계정 로그인 실패", Toast.LENGTH_SHORT).show()
            }
            else if (token != null){
                // 로그인 성공
                Toast.makeText(this, "카카오 계정 로그인 성공", Toast.LENGTH_SHORT).show()
                /*
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                 */

                val options = ActivityOptions.makeSceneTransitionAnimation(
                    this,
                    android.util.Pair(binding.textTitle, "titleTran"),
                    android.util.Pair(binding.imageLogo, "imageTran")
                )

                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent, options.toBundle())

                finish()
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

                        /*
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))

                         */

                        // 토큰 정보 보기
                        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                            if (error != null) {
                                //Log.e(TAG, "토큰 정보 보기 실패", error)
                            }
                            else if (tokenInfo != null) {
                                //Log.i(TAG, "토큰 정보 보기 성공" +
                                //        "\n회원번호: ${tokenInfo.id}" +
                                //        "\n만료시간: ${tokenInfo.expiresIn} 초")
                                // 1
                            }
                        }



                        val options = ActivityOptions.makeSceneTransitionAnimation(
                            this,
                            android.util.Pair(binding.textTitle, "titleTran"),
                            android.util.Pair(binding.imageLogo, "imageTran")
                        )

                        val intent = Intent(this, RegisterActivity::class.java)
                        startActivity(intent, options.toBundle())

                        finish()
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }

            /*
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                android.util.Pair(binding.textTitle, "titleTran"),
                android.util.Pair(binding.imageLogo, "imageTran")
            )

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent, options.toBundle())

             */
        }

        binding.constraintLayoutSkip.setOnClickListener {
            finish()
        }
    }

    private fun isUserAMember() {

    }
}