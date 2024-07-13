package com.test.kick_off_app

import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.getSystemService
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.ViewModelProvider
import com.test.kick_off_app.data.UserInfo
import com.test.kick_off_app.databinding.ActivityRegisterBinding
import com.test.kick_off_app.functions.showToast
import com.test.kick_off_app.ui.login.LoginViewModel
import com.test.kick_off_app.ui.register.OnRegisterButtonClickListener
import com.test.kick_off_app.ui.register.OnUserDataPassFirst
import com.test.kick_off_app.ui.register.OnUserDataPassSecond
import com.test.kick_off_app.ui.register.OnUserDataPassThird
import com.test.kick_off_app.ui.register.RegisterViewModel


class RegisterActivity : AppCompatActivity(), OnUserDataPassFirst, OnUserDataPassSecond, OnUserDataPassThird, OnRegisterButtonClickListener {
    lateinit var binding: ActivityRegisterBinding
    private lateinit var mDetector: GestureDetectorCompat

    private var prevFocus: View? = null

    var userInfo = UserInfo()

    private lateinit var registerViewModel:RegisterViewModel
    override fun onUserDataPassFirst(bundle: Bundle) {
        val id = bundle.getLong("id")
        val name = bundle.getString("name")
        val nickname = bundle.getString("nickname")

        /*
        userInfo.id = id
        userInfo.name = name
        userInfo.nickname = nickname


         */
        registerViewModel.updateId(id)
        registerViewModel.updateName(name!!)
        registerViewModel.updateNickname(nickname!!)

        userInfo = registerViewModel.userInfo.value!!

        Log.e("id", id.toString())
        Log.e("name", name!!)
        Log.e("nickname", nickname!!)
    }

    override fun onUserDataPassSecond(bundle: Bundle) {
        val location = bundle.getString("location")

        /*
        userInfo.location = location

         */

        registerViewModel.updateLocation(location!!)

        userInfo = registerViewModel.userInfo.value!!

        Log.e("location", location!!)
    }

    override fun onUserDataPassThird(bundle: Bundle) {
        val category = bundle.getString("category")
        /*
        userInfo.category = category

         */

        registerViewModel.updateCategory(category!!)

        userInfo = registerViewModel.userInfo.value!!

        Log.e("category", category!!)
    }

    override fun onRegisterButtonClick() {
        if(registerViewModel.isUserInfoValid()){
            // userInfo의 필드가 차있는 경우
            // 회원정보 post
            userInfo = registerViewModel.userInfo.value!!

            registerViewModel.kakaoSignup(userInfo)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mDetector = GestureDetectorCompat(this, SingleTapListener())

        registerViewModel = ViewModelProvider(this)[com.test.kick_off_app.ui.register.RegisterViewModel::class.java]

        val loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        registerViewModel.viewEvent.observe(this){
            it.getContentIfNotHandled()?.let { event ->
                when(event){
                    RegisterViewModel.EVENT_KAKAO_SIGNUP_SUCCESS -> {
                        showToast("회원가입 성공. 자동 로그인")
                        val intent = Intent(this, MainActivity::class.java).apply {
                            //엑티비티에서 갖고올 데이터
                            putExtra("KEY1", "bbbbb")
                            //데이터 전달이 성공했을 때의 변수 값 저장
                            // Result_ok = -1 일 때 엑티비티에 전달된다.
                        }
                        setResult(RESULT_OK, intent)
                        //엑티비티 종료
                        if (!isFinishing) finish()
                    }
                }
            }
        }
        /*
        binding.buttonRegister.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent)
            finish()
        }

         */
    }


    // 터치 영역에 따라 키보드를 숨기기 위해 구현
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        // Activity에서 터치 이벤트가 발생할 때 현재 포커스를 가진 뷰를 저장
        if (ev.action == MotionEvent.ACTION_UP)
            prevFocus = currentFocus
        val result = super.dispatchTouchEvent(ev)
        // dispatchTouchEvent 호출 후 singleTapUp 제스처 탐지
        mDetector.onTouchEvent(ev)
        return result
    }

    private inner class SingleTapListener : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            // ACTION_UP 이벤트에서 포커스를 가진 뷰가 EditText일 때 터치 영역을 확인하여 키보드를 토글
            if (e.action == MotionEvent.ACTION_UP && prevFocus is EditText) {
                val prevFocus = prevFocus ?: return false
                // 포커를 가진 EditText의 터치 영역 계산
                val hitRect = Rect()
                prevFocus.getGlobalVisibleRect(hitRect)

                // 터치 이벤트가 EditText의 터치 영역에 속하지 않을 때 키보드를 숨길지 결정
                if (!hitRect.contains(e.x.toInt(), e.y.toInt())) {
                    if (currentFocus is EditText && currentFocus != prevFocus) {
                        // 터치한 영역의 뷰가 다른 EditText일 때는 키보드를 가리지 않는다.
                        return false
                    } else {
                        // 터치한 영역이 EditText의 터치 영역 밖이면서 다른 EditText가 아닐 때 키보드 hide
                        getSystemService<InputMethodManager>()?.hideSoftInputFromWindow(prevFocus.windowToken, 0)
                        prevFocus.clearFocus()
                    }
                }
            }
            return super.onSingleTapUp(e)
        }
    }
}