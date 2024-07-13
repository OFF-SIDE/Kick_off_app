package com.test.kick_off_app.network

import com.test.kick_off_app.functions.SharedPrefManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    val manager: SharedPrefManager by lazy {
        SharedPrefManager.getInstance()
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = manager.getAccessToken()

        val request =
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        return chain.proceed(request)
    }
}