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

        val requestBuilder = chain.request().newBuilder()

        // 토큰이 null이 아닐 때만 헤더를 추가
        if (token != null) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}