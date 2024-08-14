package com.test.kick_off_app.ui.register

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.test.kick_off_app.data.CategoryEnum
import com.test.kick_off_app.data.Constants
import com.test.kick_off_app.data.LocationEnum
import com.test.kick_off_app.data.SignupInfo
import com.test.kick_off_app.data.Stadium

import com.test.kick_off_app.data.UserInfo
import com.test.kick_off_app.data.getCategory
import com.test.kick_off_app.data.getLocation
import com.test.kick_off_app.functions.BaseViewModel
import com.test.kick_off_app.functions.SharedPrefManager
import com.test.kick_off_app.repository.Repository
import com.test.kick_off_app.ui.login.LoginViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class RegisterViewModel : BaseViewModel() {
    private val repository = Repository()
    val manager: SharedPrefManager by lazy {
        SharedPrefManager.getInstance()
    }

    // 회원가입 시 제출하는 정보
    private val _signupInfo: MutableLiveData<SignupInfo> by lazy{
        MutableLiveData<SignupInfo>(SignupInfo(-1, "", "", "", ""))
    }
    val signupInfo: LiveData<SignupInfo>
        get() = _signupInfo

    // 지역 boolean list
    private val _locations: MutableLiveData<Array<Boolean>>by lazy{
        MutableLiveData<Array<Boolean>>(Array(Constants.LOCATION_SIZE) {false})
    }
    val locations: LiveData<Array<Boolean>>
        get() = _locations

    // 선택된 지역 번호. 선택되지 않으면 Null
    private val _selectLocationPos = MutableLiveData<Int?>()
    val selectLocationPos: LiveData<Int?>
        get() = _selectLocationPos

    // 종목 boolean list
    private val _categories: MutableLiveData<Array<Boolean>>by lazy{
        MutableLiveData<Array<Boolean>>(Array(Constants.CATEGORY_SIZE) {false})
    }
    val categories: LiveData<Array<Boolean>>
        get() = _categories

    // 선택된 종목 번호. 선택되지 않으면 Null
    private val _selectCategoryPos = MutableLiveData<Int?>()
    val selectCategoryPos: LiveData<Int?>
        get() = _selectCategoryPos

    private val _profileImage = MutableLiveData<Uri?>()
    val profileImage: LiveData<Uri?>
        get() = _profileImage

    private val _profileImageServerUrl = MutableLiveData<String?>()
    val profileImageServerUrl: LiveData<String?>
        get() = _profileImageServerUrl

    companion object{
        const val EVENT_KAKAO_SIGNUP_SUCCESS = 10001
        const val EVENT_KAKAO_SIGNUP_FAIL = 10002
        const val EVENT_KAKAO_SIGNUP_ALREADY_EXISTS = 10003
        const val EVENT_IMAGE_UPLOAD_SUCCESS = 10004
        const val EVENT_IMAGE_UPLOAD_FAIL = 10005
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
                res.body?.let{
                    Log.d("ServerError code", it.errorCode.toString())
                    Log.d("ServerError message", it.message)
                }

                if(res.body!!.errorCode == 1002){
                    //유저가 존재할 때
                    viewEvent(EVENT_KAKAO_SIGNUP_ALREADY_EXISTS)
                }
            }
            is NetworkResponse.NetworkError -> {
                // 네트워크 에러시
                res.body?.let{
                    Log.d("NetworkError code", it.errorCode.toString())
                    Log.d("NetworkError message", it.message)
                }
            }
            is NetworkResponse.UnknownError -> {
                // 언노운 에러시
                res.body?.let{
                    Log.d("UnknownError code", it.errorCode.toString())
                    Log.d("UnknownError message", it.message)
                }
            }
        }
    }

    fun uploadImage(image: MultipartBody.Part) = viewModelScope.launch {
        // 이미지 업로드 후 서버 uri를 String으로 받아서 profileImageServerUrl에 저장
        Log.e("image upload", "t")
        viewEvent(EVENT_IMAGE_UPLOAD_SUCCESS)
    }

    fun updateId(newId: Long){
        _signupInfo.value?.id = newId
    }

    fun updateName(newName: String){
        _signupInfo.value?.name = newName
    }

    fun updateNickname(newNickname: String){
        _signupInfo.value?.nickname = newNickname
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

    // locations, selectedLocation 갱신
    // 그에 맞게 signupInfo 갱신
    fun updateLocation(pos: Int){
        _locations.value = _locations.value?.apply{
            if(pos != _selectLocationPos.value) {
                this[pos] = true
                _selectLocationPos.value?.let {
                    this[it] = false
                }
                _selectLocationPos.value = pos

                _signupInfo.value = _signupInfo.value?.apply {
                    location = getLocation(LocationEnum.values()[pos])
                }
            }
            else{
                this[pos] = false
                _selectLocationPos.value = null

                _signupInfo.value = _signupInfo.value?.apply {
                    location = ""
                }
            }
        }
        Log.d("location", _signupInfo.value?.location!!)
    }

    fun resetLocation(){
        _locations.value = Array(Constants.LOCATION_SIZE) {false}
        _selectLocationPos.value = null
        _signupInfo.value = _signupInfo.value?.apply {
            location = ""
        }
    }

    fun updateCategory(pos: Int){
        _categories.value = _categories.value?.apply{
            if(pos != _selectCategoryPos.value) {
                this[pos] = true
                _selectCategoryPos.value?.let {
                    this[it] = false
                }
                _selectCategoryPos.value = pos

                _signupInfo.value = _signupInfo.value?.apply {
                    category = getCategory(CategoryEnum.values()[pos])
                }
            }
            else{
                this[pos] = false
                _selectCategoryPos.value = null

                _signupInfo.value = _signupInfo.value?.apply {
                    category = ""
                }
            }
        }
        Log.d("category", _signupInfo.value?.category!!)
    }

    fun resetCategory(){
        _categories.value = Array(Constants.CATEGORY_SIZE) {false}
        _selectCategoryPos.value = null
        _signupInfo.value = _signupInfo.value?.apply {
            category = ""
        }
    }

    fun updateProfileImage(image: Uri?){
        _profileImage.value = image
    }
}