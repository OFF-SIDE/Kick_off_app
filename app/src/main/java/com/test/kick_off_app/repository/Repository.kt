package com.test.kick_off_app.repository

import android.util.Log
import com.test.kick_off_app.data.Stadium
import com.test.kick_off_app.data.StadiumDetail
import com.test.kick_off_app.network.GetStadiumApi
import com.test.kick_off_app.network.GetStadiumDetailApi
import com.test.kick_off_app.network.RetrofitInstance
import retrofit2.create

class Repository {
    private val getStadiumClient = RetrofitInstance.getInstance().create(GetStadiumApi::class.java)
    private val getStadiumDetailApi = RetrofitInstance.getInstance().create(GetStadiumDetailApi::class.java)

    suspend fun getStadium(location: String?, category: String?): List<Stadium>? {
        try {
            val res = getStadiumClient.getStadium(location, category)
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
            // fail : no response
            e.printStackTrace()
            return null
        }
    }

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
}