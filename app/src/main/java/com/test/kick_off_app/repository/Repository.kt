package com.test.kick_off_app.repository

import android.util.Log
import com.haroldadmin.cnradapter.NetworkResponse
import com.test.kick_off_app.data.Stadium
import com.test.kick_off_app.data.StadiumDetail
import com.test.kick_off_app.network.ErrorResponse
import com.test.kick_off_app.network.GetStadiumApi
import com.test.kick_off_app.network.GetStadiumDetailApi
import com.test.kick_off_app.network.KakaoLoginApi
import com.test.kick_off_app.network.RetrofitInstance
import com.test.kick_off_app.network.SuccessfulResponse
import retrofit2.create

class Repository {
    private val getStadiumClient = RetrofitInstance.getInstance().create(GetStadiumApi::class.java)
    private val getStadiumDetailApi = RetrofitInstance.getInstance().create(GetStadiumDetailApi::class.java)
    private val kakaoLoginClient = RetrofitInstance.getInstance().create(KakaoLoginApi::class.java)

    suspend fun getStadium(location: String?, category: String?){
        when(val res = getStadiumClient.getStadium(location, category)){
            is NetworkResponse.Success -> {
                // 성공시
                Log.d("success code", res.body.code.toString())
                Log.d("success message", res.body.message)
            }
            is NetworkResponse.ServerError -> {
                // 서버 에러시
                Log.d("ServerError code", res.body!!.errorCode.toString())
                Log.d("ServerError message", res.body!!.message)
            }
            is NetworkResponse.NetworkError -> {
                // 네트워크 에러시
                Log.d("NetworkError code", res.body!!.errorCode.toString())
                Log.d("NetworkError message", res.body!!.message)
            }
            is NetworkResponse.UnknownError -> {
                // 언노운 에러시
                Log.d("UnknownError code", res.body!!.errorCode.toString())
                Log.d("UnknownError message", res.body!!.message)
            }
        }
    }
/*

    suspend fun getStadiumDetail(stadiumId: Int, userId: Int): StadiumDetail? {
        try {
            val res = getStadiumDetailApi.getStadiumDetail(stadiumId, userId)
            if(res.status == "ok"){
                // ok
                return res.data
            }
            else {
                // fail : error
                return null
            }
        }
        catch (e:Exception){
            e.printStackTrace()
            return null
        }
    }
*/

    suspend fun kakaoLogin(oauthId:String): NetworkResponse<SuccessfulResponse<String>, ErrorResponse>{
        return kakaoLoginClient.kakaoLogin(oauthId)
    }

}