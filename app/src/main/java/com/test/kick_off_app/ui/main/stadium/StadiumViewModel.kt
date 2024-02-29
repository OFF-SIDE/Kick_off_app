package com.test.kick_off_app.ui.main.stadium

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.kick_off_app.data.StadiumInfo
import com.test.kick_off_app.repository.Repository
import kotlinx.coroutines.launch

class StadiumViewModel : ViewModel() {
    private val repository =Repository()

    private val _text = MutableLiveData<String>().apply {
        value = "This is stadium Fragment"
    }
    val text: LiveData<String> = _text

    private val _result = MutableLiveData<List<StadiumInfo>>()
    val result: LiveData<List<StadiumInfo>>
        get() = _result

    fun getStadiumData() = viewModelScope.launch {
        //Log.d("GroundMainViewModel", repository.getAllData().toString())
        try{
            //_result.value = repository.getTempStadium()
            //_result.value = repository.getGroundData(contactPhone, location)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}