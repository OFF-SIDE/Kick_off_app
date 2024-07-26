package com.test.kick_off_app.ui.login

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.test.kick_off_app.MainActivity
import com.test.kick_off_app.RegisterActivity
import com.test.kick_off_app.functions.SharedPrefManager
import com.test.kick_off_app.databinding.ActivityLoginBinding
import com.test.kick_off_app.functions.BaseViewModel
import com.test.kick_off_app.functions.SingleLiveEvent
import com.test.kick_off_app.repository.Repository
import kotlinx.coroutines.launch

class LoginViewModel: BaseViewModel() {
    private val repository = Repository()
    val manager: SharedPrefManager by lazy {
        SharedPrefManager.getInstance()
    }

    companion object {
        const val EVENT_KAKAO_LOGIN_SUCCESS = 10001
        const val EVENT_AUTH_SUCCESS = 10002
        const val EVENT_AUTH_WRONG_TOKEN = 10003
        const val EVENT_KAKAO_LOGIN_NO_USER = 10004
    }


    fun kakaoLogin(oauthId: String) = viewModelScope.launch {
        when(val res = repository.kakaoLogin(oauthId)){
            is NetworkResponse.Success -> {
                // 성공시
                Log.d("success code", res.body.code.toString())
                Log.d("success message", res.body.message)

                // 토큰 저장
                manager.putAccessToken(res.body.data.accessToken)

                //viewEvent(EVENT_KAKAO_LOGIN_SUCCESS)
                viewEvent(EVENT_KAKAO_LOGIN_NO_USER)
            }
            is NetworkResponse.ServerError -> {
                // 서버 에러시
                Log.d("ServerError code", res.body!!.errorCode.toString())
                Log.d("ServerError message", res.body!!.message+"카카오로그인실패")


                if(res.body!!.errorCode == 1001){
                    //유저가 존재하지 않을 때
                    viewEvent(EVENT_KAKAO_LOGIN_NO_USER)
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
                Log.d("UnknownError code", res.body!!.errorCode.toString())
                Log.d("UnknownError message", res.body!!.message)
            }
        }
    }

    fun auth() = viewModelScope.launch {
        when(val res = repository.auth()){
            is NetworkResponse.Success -> {
                Log.d("id", res.body!!.data.id.toString())
                Log.d("name", res.body!!.data.name.toString())
                Log.d("nickname", res.body!!.data.nickname.toString())
                Log.d("location", res.body!!.data.location.toString())
                Log.d("category", res.body!!.data.category.toString())


                manager.putUserInfo(res.body!!.data)

                viewEvent(EVENT_AUTH_SUCCESS)
            }
            is NetworkResponse.ServerError -> {
                // 서버 에러시
                Log.e("ServerError code", res.body!!.errorCode.toString())
                Log.e("ServerError message", res.body!!.message)

                viewEvent(EVENT_AUTH_WRONG_TOKEN)
            }
            is NetworkResponse.NetworkError -> {
                // 네트워크 에러시
                Log.e("NetworkError code", res.body!!.errorCode.toString())
                Log.e("NetworkError message", res.body!!.message)
            }
            is NetworkResponse.UnknownError -> {
                // 언노운 에러시
                Log.e("UnknownError code", res.body!!.errorCode.toString())
                Log.e("UnknownError message", res.body!!.message)
            }
        }
    }

}