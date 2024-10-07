package com.test.kick_off_app.ui.main.matching

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MatchingViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "추후 업데이트될 서비스를 기대해주세요!"
    }
    val text: LiveData<String> = _text
}