package com.test.kick_off_app.ui.login

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.test.kick_off_app.MainActivity
import com.test.kick_off_app.RegisterActivity
import com.test.kick_off_app.SharedPrefManager
import com.test.kick_off_app.databinding.ActivityLoginBinding
import com.test.kick_off_app.repository.Repository
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    private val repository = Repository()
    val manager: SharedPrefManager by lazy {
        SharedPrefManager.getInstance()
    }
    lateinit var binding: ActivityLoginBinding
    lateinit var context: Context

    fun kakaoLogin(oauthId: String) = viewModelScope.launch {
        when(val res = repository.kakaoLogin(oauthId)){
            is NetworkResponse.Success -> {
                // 성공시
                Log.d("success code", res.body.code.toString())
                Log.d("success message", res.body.message)

                // 토큰 저장

                manager.saveAccessToken(res.body.data)

                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            }
            is NetworkResponse.ServerError -> {
                // 서버 에러시
                Log.d("ServerError code", res.body!!.errorCode.toString())
                Log.d("ServerError message", res.body!!.message)

                if(res.body!!.errorCode == 1001){
                    //유저가 존재하지 않을 때
                    val options = ActivityOptions.makeSceneTransitionAnimation(
                        context as? Activity,
                        android.util.Pair(binding.textTitle, "titleTran"),
                        android.util.Pair(binding.imageLogo, "imageTran")
                    )

                    val intent = Intent(context, RegisterActivity::class.java)
                    context.startActivity(intent, options.toBundle())
                }
            }
            is NetworkResponse.NetworkError -> {
                // 네트워크 에러시
                Log.d("NetworkError code", res.body!!.errorCode.toString())
                Log.d("NetworkError message", res.body!!.message)
            }
            is NetworkResponse.UnknownError -> {
                // 언노운 에러시
                Log.d("UnknownError code", res.body!!.errorCode.toString())
                Log.d("UnknownError message", res.body!!.message)
            }
        }
    }

    fun setBinding2(binding: ActivityLoginBinding){
        this.binding = binding
    }

    fun setContext2(context: Context){
        this.context = context
    }

}