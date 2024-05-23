package com.test.kick_off_app.ui.main.stadium

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.kick_off_app.data.Stadium
import com.test.kick_off_app.repository.Repository
import kotlinx.coroutines.flow.merge
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
            //_result.value = repository.getStadium(location, category)
            repository.getStadium(location, category)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun getStadium2(location1: String?, location2:String?, category: String?) = viewModelScope.launch {
        try{
            //val result1 = repository.getStadium(location1, category) ?: listOf()
            //val result2 = repository.getStadium(location2, category) ?: listOf()

            //_result.value = result1 + result2
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}