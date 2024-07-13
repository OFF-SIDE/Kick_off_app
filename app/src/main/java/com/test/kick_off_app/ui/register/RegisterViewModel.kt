package com.test.kick_off_app.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.test.kick_off_app.data.Stadium

import com.test.kick_off_app.data.UserInfo
import com.test.kick_off_app.functions.BaseViewModel
import com.test.kick_off_app.functions.SharedPrefManager
import com.test.kick_off_app.repository.Repository
import com.test.kick_off_app.ui.login.LoginViewModel
import kotlinx.coroutines.launch

class RegisterViewModel : BaseViewModel() {
    private val repository = Repository()
    val manager: SharedPrefManager by lazy {
        SharedPrefManager.getInstance()
    }

    private val _userInfo: MutableLiveData<UserInfo> by lazy{
        MutableLiveData<UserInfo>(UserInfo(-1, "", "", "", ""))
    }
    val userInfo: LiveData<UserInfo>
        get() = _userInfo

    companion object{
        const val EVENT_KAKAO_SIGNUP_SUCCESS = 10001
    }

    fun kakaoSignup(userInfo: UserInfo) = viewModelScope.launch {
        when(val res = repository.kakaoSignup(userInfo)){
            is NetworkResponse.Success -> {
                Log.d("success code", res.body.code.toString())
                Log.d("success message", res.body.message)

                // 토큰 저장
                manager.putAccessToken(res.body.data)

                viewEvent(EVENT_KAKAO_SIGNUP_SUCCESS)
            }
            is NetworkResponse.ServerError -> {
                // 서버 에러시
                Log.d("ServerError code", res.body!!.errorCode.toString())
                Log.d("ServerError message", res.body!!.message+"카카오회원가입실패")


                if(res.body!!.errorCode == 1001){
                    //유저가 존재하지 않을 때

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

    fun updateId(newId: Long){
        val currentUserInfo = _userInfo.value
        if (currentUserInfo != null) {
            val updatedUserInfo = currentUserInfo.copy(id = newId)
            _userInfo.value = updatedUserInfo
        }
    }

    fun updateName(newName: String){
        val currentUserInfo = _userInfo.value
        if (currentUserInfo != null) {
            val updatedUserInfo = currentUserInfo.copy(name = newName)
            _userInfo.value = updatedUserInfo
        }
    }

    fun updateNickname(newNickname: String){
        val currentUserInfo = _userInfo.value
        if (currentUserInfo != null) {
            val updatedUserInfo = currentUserInfo.copy(nickname = newNickname)
            _userInfo.value = updatedUserInfo
        }
    }

    fun updateLocation(newLocation: String){
        val currentUserInfo = _userInfo.value
        if (currentUserInfo != null) {
            val updatedUserInfo = currentUserInfo.copy(location = newLocation)
            _userInfo.value = updatedUserInfo
        }
    }

    fun updateCategory(newCategory: String){
        val currentUserInfo = _userInfo.value
        if (currentUserInfo != null) {
            val updatedUserInfo = currentUserInfo.copy(category = newCategory)
            _userInfo.value = updatedUserInfo
        }
    }

    fun isUserInfoValid(): Boolean{
        val currentUserInfo = _userInfo.value
        return currentUserInfo?.let {
            it.id != null &&
            it.name != null &&
            it.nickname != null &&
            it.location != null &&
            it.category != null
        } ?: false
    }
}