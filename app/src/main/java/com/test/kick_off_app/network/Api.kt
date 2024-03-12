package com.test.kick_off_app.network

import com.test.kick_off_app.data.Response
import com.test.kick_off_app.data.Stadium
import retrofit2.http.GET
import retrofit2.http.Query

interface GetStadiumApi{
    @GET("stadium/location")
    suspend fun getStadium(@Query("location") location: String? = null, @Query("category") category: String? = null): Response<List<Stadium>>
}