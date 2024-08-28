package com.test.kick_off_app.ui.main.stadium

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.test.kick_off_app.data.StadiumDetail
import com.test.kick_off_app.functions.BaseViewModel
import com.test.kick_off_app.repository.Repository
import kotlinx.coroutines.launch

class StadiumDetailViewModel : BaseViewModel() {
    private val repository = Repository()

    private val _result = MutableLiveData<StadiumDetail>()
    val result: LiveData<StadiumDetail>
        get() = _result

    companion object{
        const val EVENT_STAR_STADIUM = 10001
        const val EVENT_UNSTAR_STADIUM = 10002
    }

    fun getStadiumDetail(stadiumId: Int) = viewModelScope.launch {
        when(val res = repository.getStadiumDetail(stadiumId)){
            is NetworkResponse.Success -> {
                // 성공시
                Log.d("success code", res.body.code.toString())
                Log.d("success message", res.body.message ?: "")

                res.body.data?.run {
                    _result.value = this
                }
            }
            is NetworkResponse.ServerError -> {
                // 서버 에러시
                res.body?.run{
                    Log.d("ServerError code", errorCode.toString())
                    Log.d("ServerError message", message ?: "")
                }

                when(val errorCode = res.body!!.errorCode){
                    0 -> {
                        // location 누락 시
                        viewEvent(StadiumViewModel.EVENT_INVALID_LOCATION)
                    }
                    1, 2, 3 -> {
                        // 토큰 문제
                        viewEvent(StadiumViewModel.EVENT_WRONG_TOKEN)
                    }
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

    fun starStadium(stadiumId: Int) = viewModelScope.launch {
        when(val res = repository.starStadium(stadiumId)){
            is NetworkResponse.Success -> {
                // 성공시
                Log.d("success code", res.body.code.toString())
                Log.d("success message", res.body.message ?: "")

                viewEvent(EVENT_STAR_STADIUM)
            }
            is NetworkResponse.ServerError -> {
                // 서버 에러시
                res.body?.run{
                    Log.d("ServerError code", errorCode.toString())
                    Log.d("ServerError message", message ?: "")
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

    fun unStarStadium(stadiumId: Int) = viewModelScope.launch {
        when(val res = repository.unStarStadium(stadiumId)){
            is NetworkResponse.Success -> {
                // 성공시
                Log.d("success code", res.body.code.toString())
                Log.d("success message", res.body.message ?: "")

                viewEvent(EVENT_UNSTAR_STADIUM)
            }
            is NetworkResponse.ServerError -> {
                // 서버 에러시
                res.body?.run{
                    Log.d("ServerError code", errorCode.toString())
                    Log.d("ServerError message", message ?: "")
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
}