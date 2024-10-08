package com.test.kick_off_app.ui.main.stadium

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.test.kick_off_app.data.Stadium
import com.test.kick_off_app.functions.BaseViewModel
import com.test.kick_off_app.repository.Repository
import com.test.kick_off_app.ui.login.LoginViewModel
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch

class StadiumViewModel : BaseViewModel() {
    private val repository = Repository()

    private val _stadiums = MutableLiveData<List<Stadium>>()
    val stadiums: LiveData<List<Stadium>>
        get() = _stadiums

    // 현재 ui에서 선택된 지역들
    private val _selectedLocations = MutableLiveData("마포구")

    val selectedLocations: LiveData<String>
        get() = _selectedLocations

    companion object {
        const val EVENT_INVALID_LOCATION = 10001
        const val EVENT_WRONG_TOKEN = 10002
    }

    fun getStadium(locations: String?, category: String?) = viewModelScope.launch {
        when(val res = repository.getStadium(locations, category)){
            is NetworkResponse.Success -> {
                // 성공시
                Log.d("success code", res.body.code.toString())
                Log.d("success message", res.body.message ?: "")
                Log.d("locations", locations ?: "")
                Log.d("category", category ?: "")

                // 구장 리스트 저장
                _stadiums.value = res.body.data ?: listOf()
            }
            is NetworkResponse.ServerError -> {
                // 서버 에러시
                res.body?.let{
                    Log.d("ServerError code", it.errorCode.toString())
                    Log.d("ServerError message", it.message ?: "")
                }


                when(val errorCode = res.body!!.errorCode){
                    0 -> {
                        // location 누락 시
                        viewEvent(EVENT_INVALID_LOCATION)
                    }
                    1, 2, 3 -> {
                        // 토큰 문제
                        viewEvent(EVENT_WRONG_TOKEN)
                    }
                }
            }
            is NetworkResponse.NetworkError -> {
                // 네트워크 에러시
                res.body?.let{
                    Log.d("NetworkError code", it.errorCode.toString())
                    Log.d("NetworkError message", it.message ?: "")
                }

            }
            is NetworkResponse.UnknownError -> {
                // 언노운 에러시
                res.body?.let{
                    Log.d("UnknownError code", it.errorCode.toString())
                    Log.d("UnknownError message", it.message ?: "")
                }

            }
        }
    }

    fun setLocations(locations: String){
        _selectedLocations.value = locations
    }
}