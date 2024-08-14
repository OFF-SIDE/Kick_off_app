package com.test.kick_off_app.repository

import android.util.Log
import com.haroldadmin.cnradapter.NetworkResponse
import com.test.kick_off_app.data.SignupInfo
import com.test.kick_off_app.data.Stadium
import com.test.kick_off_app.data.StadiumDetail
import com.test.kick_off_app.data.UserInfo
import com.test.kick_off_app.network.AccessToken
import com.test.kick_off_app.network.AuthApi
import com.test.kick_off_app.network.ErrorResponse
import com.test.kick_off_app.network.GetStadiumApi
import com.test.kick_off_app.network.GetStadiumDetailApi
import com.test.kick_off_app.network.KakaoLoginApi
import com.test.kick_off_app.network.KakaoSignupApi
import com.test.kick_off_app.network.RetrofitInstance
import com.test.kick_off_app.network.SuccessfulResponse
import retrofit2.create

class Repository {
    private val getStadiumClient = RetrofitInstance.getInstance().create(GetStadiumApi::class.java)
    private val getStadiumDetailApi = RetrofitInstance.getInstance().create(GetStadiumDetailApi::class.java)
    private val kakaoLoginClient = RetrofitInstance.getInstance().create(KakaoLoginApi::class.java)
    private val authClient = RetrofitInstance.getInstance().create(AuthApi::class.java)
    private val kakaoSignupClient = RetrofitInstance.getInstance().create(KakaoSignupApi::class.java)

    suspend fun getStadium(locations: String?, category: String?): NetworkResponse<SuccessfulResponse<List<Stadium>>, ErrorResponse>{
        return getStadiumClient.getStadium(locations, category)
    }

    suspend fun kakaoLogin(oauthId:String): NetworkResponse<SuccessfulResponse<AccessToken>, ErrorResponse>{
        return kakaoLoginClient.kakaoLogin(oauthId)
    }

    suspend fun auth(): NetworkResponse<SuccessfulResponse<UserInfo>, ErrorResponse>{
        return authClient.auth()
    }

    suspend fun kakaoSignup(signupInfo: SignupInfo): NetworkResponse<SuccessfulResponse<AccessToken>, ErrorResponse>{
        return kakaoSignupClient.kakaoSignup(signupInfo)
    }

}