package com.test.kick_off_app.ui.main.referee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RefereeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is referee Fragment"
    }
    val text: LiveData<String> = _text
}