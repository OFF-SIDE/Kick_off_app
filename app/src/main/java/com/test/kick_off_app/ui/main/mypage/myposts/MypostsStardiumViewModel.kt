package com.test.kick_off_app.ui.main.mypage.myposts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.test.kick_off_app.data.Stadium
import com.test.kick_off_app.functions.BaseViewModel
import com.test.kick_off_app.repository.Repository
import kotlinx.coroutines.launch

class MypostsStardiumViewModel : BaseViewModel() {
    private val repository = Repository()

    private val _stadiums = MutableLiveData<List<Stadium>>()
    val stadiums: LiveData<List<Stadium>>
        get() = _stadiums

    companion object{
        const val EVENT_GET_STARRED_STADIUM_SUCCESS = 10001
        const val EVENT_GET_STARRED_STADIUM_FAIL = 10002
        const val EVENT_WRONG_TOKEN = 10003
    }

    fun getStarredStadium() = viewModelScope.launch {
        // todo: getMyStadium으로 변경할 것
        when(val res = repository.getStarredStadium()){
            is NetworkResponse.Success -> {
                Log.d("success code", res.body.code.toString())
                Log.d("success message", res.body.message ?: "")

                _stadiums.value = res.body.data ?: listOf()
                //viewEvent(EVENT_GET_STARRED_STADIUM_SUCCESS)
            }
            is NetworkResponse.ServerError -> {
                res.body?.run{
                    Log.d("ServerError code", errorCode.toString())
                    Log.d("ServerError message", message ?: "")
                }

                when(val errorCode = res.body!!.errorCode){
                    1, 2, 3 -> {
                        // 토큰 문제
                        viewEvent(EVENT_WRONG_TOKEN)
                    }
                    else -> {
                        viewEvent(EVENT_GET_STARRED_STADIUM_FAIL)
                    }
                }
            }
            is NetworkResponse.NetworkError -> {
                // 네트워크 에러시
                res.body?.run{
                    Log.d("NetworkError code", errorCode.toString())
                    Log.d("NetworkError message", message ?: "")
                }
                viewEvent(EVENT_GET_STARRED_STADIUM_FAIL)
            }
            is NetworkResponse.UnknownError -> {
                // 언노운 에러시
                res.body?.run{
                    Log.d("UnknownError code", errorCode.toString())
                    Log.d("UnknownError message", message ?: "")
                }
                viewEvent(EVENT_GET_STARRED_STADIUM_FAIL)
            }
        }
    }
}