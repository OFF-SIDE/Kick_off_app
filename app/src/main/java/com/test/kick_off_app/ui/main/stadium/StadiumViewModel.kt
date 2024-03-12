package com.test.kick_off_app.ui.main.stadium

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.kick_off_app.data.Stadium
import com.test.kick_off_app.repository.Repository
import kotlinx.coroutines.launch

class StadiumViewModel : ViewModel() {
    private val repository = Repository()

    private val _text = MutableLiveData<String>().apply {
        value = "This is stadium Fragment"
    }
    val text: LiveData<String> = _text

    private val _result = MutableLiveData<List<Stadium>>()
    val result: LiveData<List<Stadium>>
        get() = _result

    fun getStadium(location: String?, category: String?) = viewModelScope.launch {
        try{
            _result.value = repository.getStadium(location, category)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}