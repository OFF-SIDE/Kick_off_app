package com.test.kick_off_app.ui.main.stadium

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.kick_off_app.data.StadiumDetail
import com.test.kick_off_app.repository.Repository
import kotlinx.coroutines.launch

class StadiumDetailViewModel : ViewModel() {
    private val repository = Repository()

    private val _result = MutableLiveData<StadiumDetail>()
    val result: LiveData<StadiumDetail>
        get() = _result

    fun getStadiumDetail(stadiumId: Int, userId: Int) = viewModelScope.launch {
        try{
            //_result.value = repository.getStadiumDetail(stadiumId, userId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}