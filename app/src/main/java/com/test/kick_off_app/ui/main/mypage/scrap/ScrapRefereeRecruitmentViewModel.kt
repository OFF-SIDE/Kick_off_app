package com.test.kick_off_app.ui.main.mypage.scrap

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.test.kick_off_app.data.Referee
import com.test.kick_off_app.functions.BaseViewModel
import com.test.kick_off_app.repository.Repository
import kotlinx.coroutines.launch

class ScrapRefereeRecruitmentViewModel : BaseViewModel() {
    private val repository = Repository()

    private val _referees = MutableLiveData<List<Referee>>()
    val referees: LiveData<List<Referee>>
        get() = _referees

    companion object{
        const val EVENT_GET_STARRED_REFEREE_SUCCESS = 10001
        const val EVENT_GET_STARRED_REFEREE_FAIL = 10002
        const val EVENT_WRONG_TOKEN = 10003
    }

    fun getStarredReferee(isHiring: Boolean) = viewModelScope.launch {
        when(val res = repository.getStarredReferee(isHiring)){
            is NetworkResponse.Success -> {
                Log.d("success code", res.body.code.toString())
                Log.d("success message", res.body.message ?: "")

                _referees.value = res.body.data ?: listOf()
                //viewEvent(EVENT_GET_STARRED_REFEREE_SUCCESS)
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
                        viewEvent(EVENT_GET_STARRED_REFEREE_FAIL)
                    }
                }
            }
            is NetworkResponse.NetworkError -> {
                // 네트워크 에러시
                res.body?.run{
                    Log.d("NetworkError code", errorCode.toString())
                    Log.d("NetworkError message", message ?: "")
                }
                viewEvent(EVENT_GET_STARRED_REFEREE_FAIL)
            }
            is NetworkResponse.UnknownError -> {
                // 언노운 에러시
                res.body?.run{
                    Log.d("UnknownError code", errorCode.toString())
                    Log.d("UnknownError message", message ?: "")
                }
                viewEvent(EVENT_GET_STARRED_REFEREE_FAIL)
            }
        }
    }
}