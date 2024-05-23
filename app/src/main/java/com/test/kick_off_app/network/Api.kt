package com.test.kick_off_app.network

import com.haroldadmin.cnradapter.NetworkResponse
import com.skydoves.sandwich.ApiResponse
import com.test.kick_off_app.data.Response
import com.test.kick_off_app.data.Stadium
import com.test.kick_off_app.data.StadiumDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetStadiumApi{
    @GET("stadium/location")
    suspend fun getStadium(@Query("location") location: String? = null, @Query("category") category: String? = null): NetworkResponse<SuccessfulResponse<List<Stadium>>, ErrorResponse>
}

interface GetStadiumDetailApi{
    @GET("stadium/{stadiumId}")
    suspend fun getStadiumDetail(
        @Path("stadiumId") stadiumId: Int,
        @Query("userId") userId: Int?
    ) : Response<StadiumDetail>
}