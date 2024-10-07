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
                Log.d("success message", res.body.message ?: "")

                // 토큰 저장
                res.body.data?.run{
                    manager.putAccessToken(accessToken)
                }

                //viewEvent(EVENT_KAKAO_LOGIN_SUCCESS)
                viewEvent(EVENT_KAKAO_LOGIN_NO_USER)
            }
            is NetworkResponse.ServerError -> {
                // 서버 에러시
                res.body?.run{
                    Log.d("ServerError code", errorCode.toString())
                    Log.d("ServerError message", message ?: "")
                }


                if(res.body!!.errorCode == 1001){
                    //유저가 존재하지 않을 때
                    viewEvent(EVENT_KAKAO_LOGIN_NO_USER)
                }
            }
            is NetworkResponse.NetworkError -> {
                // 네트워크 에러시
                res.body?.run{
                    Log.d("NetworkError code", errorCode.toString())
                    Log.d("NetworkError message", message ?: "")
                }
            }
            is NetworkResponse.UnknownError -> {
                // 언노운 에러시
                res.body?.run{
                    Log.d("UnknownError code", errorCode.toString())
                    Log.d("UnknownError message", message ?: "")
                }
            }
        }
    }

    fun auth() = viewModelScope.launch {
        when(val res = repository.auth()){
            is NetworkResponse.Success -> {
                res.body.data?.run{
                    Log.d("id", id.toString())
                    Log.d("name", name.toString())
                    Log.d("nickname", nickname.toString())
                    Log.d("location", location.toString())
                    Log.d("category", category.toString())

                    manager.putUserInfo(this)
                }

                viewEvent(EVENT_AUTH_SUCCESS)
            }
            is NetworkResponse.ServerError -> {
                // 서버 에러시
                res.body?.run{
                    Log.e("ServerError code", errorCode.toString())
                    Log.e("ServerError message", message ?: "")
                }


                viewEvent(EVENT_AUTH_WRONG_TOKEN)
            }
            is NetworkResponse.NetworkError -> {
                // 네트워크 에러시
                res.body?.run{
                    Log.e("NetworkError code", errorCode.toString())
                    Log.e("NetworkError message", message ?: "")
                }
            }
            is NetworkResponse.UnknownError -> {
                // 언노운 에러시
                res.body?.run{
                    Log.e("UnknownError code", errorCode.toString())
                    Log.e("UnknownError message", message ?: "")
                }
            }
        }
    }

}