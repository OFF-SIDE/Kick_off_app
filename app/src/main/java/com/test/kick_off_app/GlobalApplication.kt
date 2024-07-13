package com.test.kick_off_app

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.test.kick_off_app.functions.SharedPrefManager

class GlobalApplication : Application() {
    override fun onCreate() {
        SharedPrefManager.init(this)
        super.onCreate()
        KakaoSdk.init(this, getString(R.string.native_app_key))
    }
}