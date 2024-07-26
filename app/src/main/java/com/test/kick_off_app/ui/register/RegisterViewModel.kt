package com.test.kick_off_app.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.test.kick_off_app.data.SignupInfo
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

    private val _signupInfo: MutableLiveData<SignupInfo> by lazy{
        MutableLiveData<SignupInfo>(SignupInfo(-1, "", "", "", ""))
    }
    val signupInfo: LiveData<SignupInfo>
        get() = _signupInfo

    companion object{
        const val EVENT_KAKAO_SIGNUP_SUCCESS = 10001
    }

    fun kakaoSignup(signupInfo: SignupInfo) = viewModelScope.launch {
        when(val res = repository.kakaoSignup(signupInfo)){
            is NetworkResponse.Success -> {
                Log.d("success code", res.body.code.toString())
                Log.d("success message", res.body.message)

                // 토큰 저장
                manager.putAccessToken(res.body.data.accessToken)

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
        _signupInfo.value?.id = newId
        /*
        val currentUserInfo = _signupInfo.value
        if (currentUserInfo != null) {
            val updatedUserInfo = currentUserInfo.copy(id = newId)
            _signupInfo.value = updatedUserInfo
        }
         */
    }

    fun updateName(newName: String){
        _signupInfo.value?.name = newName
        /*
        val currentUserInfo = _signupInfo.value
        if (currentUserInfo != null) {
            val updatedUserInfo = currentUserInfo.copy(name = newName)
            _signupInfo.value = updatedUserInfo
        }
        */
    }

    fun updateNickname(newNickname: String){
        _signupInfo.value?.nickname = newNickname
        /*
        val currentUserInfo = _signupInfo.value
        if (currentUserInfo != null) {
            val updatedUserInfo = currentUserInfo.copy(nickname = newNickname)
            _signupInfo.value = updatedUserInfo
        }
        */
    }

    fun updateLocation(newLocation: String){
        _signupInfo.value?.location = newLocation
        /*
        val currentUserInfo = _signupInfo.value
        if (currentUserInfo != null) {
            val updatedUserInfo = currentUserInfo.copy(location = newLocation)
            _signupInfo.value = updatedUserInfo
        }
        */
    }

    fun updateCategory(newCategory: String){
        _signupInfo.value?.category = newCategory
        /*
        val currentUserInfo = _signupInfo.value
        if (currentUserInfo != null) {
            val updatedUserInfo = currentUserInfo.copy(category = newCategory)
            _signupInfo.value = updatedUserInfo
        }
        */
    }

    fun isUserInfoValid(): Boolean{
        val currentUserInfo = _signupInfo.value
        return currentUserInfo?.let {
            it.id != null &&
            it.name != null &&
            it.nickname != null &&
            it.location != null &&
            it.category != null
        } ?: false
    }
}