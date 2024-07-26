package com.test.kick_off_app.network

import com.haroldadmin.cnradapter.NetworkResponse
import com.test.kick_off_app.data.Response
import com.test.kick_off_app.data.SignupInfo
import com.test.kick_off_app.data.Stadium
import com.test.kick_off_app.data.StadiumDetail
import com.test.kick_off_app.data.UserInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

interface KakaoLoginApi{
    @POST("auth/login/kakao")
    suspend fun kakaoLogin(@Body oauthId:String): NetworkResponse<SuccessfulResponse<AccessToken>, ErrorResponse>
}

interface AuthApi{
    @GET("auth")
    suspend fun auth(): NetworkResponse<SuccessfulResponse<UserInfo>, ErrorResponse>
}

interface KakaoSignupApi{
    @POST("auth/signup/kakao")
    suspend fun kakaoSignup(@Body signupInfo: SignupInfo): NetworkResponse<SuccessfulResponse<AccessToken>, ErrorResponse>
}