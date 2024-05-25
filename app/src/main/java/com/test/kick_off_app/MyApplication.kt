package com.test.kick_off_app

import android.app.Application

class MyApplication: Application() {
    override fun onCreate() {
        SharedPrefManager.init(this)
        super.onCreate()
    }
}