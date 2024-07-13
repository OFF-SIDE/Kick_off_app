package com.test.kick_off_app.network

import android.util.Log
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val BASE_URL = "http://13.209.73.252:8080/"

    val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)

            // 서버 응답 로그 출력
            val responseBody = response.body?.string()
            Log.d("ServerResponse", responseBody ?: "Empty response body")

            response.newBuilder()
                .body(responseBody?.toResponseBody(response.body?.contentType()))
                .build()
        }
        .addInterceptor(AuthInterceptor())
        .build()

    val retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addCallAdapterFactory(NetworkResponseAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getInstance(): Retrofit {
        return retrofit
    }
}