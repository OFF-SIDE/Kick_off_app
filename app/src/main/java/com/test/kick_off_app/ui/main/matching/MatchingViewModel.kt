package com.test.kick_off_app.ui.main.matching

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MatchingViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is matching Fragment"
    }
    val text: LiveData<String> = _text
}