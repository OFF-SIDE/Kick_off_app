package com.test.kick_off_app.repository

import android.util.Log
import com.haroldadmin.cnradapter.NetworkResponse
import com.test.kick_off_app.data.FileData
import com.test.kick_off_app.data.FileTypeEnum
import com.test.kick_off_app.data.FileUploadRequest
import com.test.kick_off_app.data.Referee
import com.test.kick_off_app.data.SignupInfo
import com.test.kick_off_app.data.Stadium
import com.test.kick_off_app.data.StadiumDetail
import com.test.kick_off_app.data.UserInfo
import com.test.kick_off_app.network.AccessToken
import com.test.kick_off_app.network.AuthApi
import com.test.kick_off_app.network.ErrorResponse
import com.test.kick_off_app.network.GetMyRefereeApi
import com.test.kick_off_app.network.GetPreSignedUrlApi
import com.test.kick_off_app.network.GetStadiumApi
import com.test.kick_off_app.network.GetStadiumDetailApi
import com.test.kick_off_app.network.GetStarredRefereeApi
import com.test.kick_off_app.network.GetStarredStadiumApi
import com.test.kick_off_app.network.ImageUploadApi
import com.test.kick_off_app.network.KakaoLoginApi
import com.test.kick_off_app.network.KakaoSignupApi
import com.test.kick_off_app.network.RetrofitInstance
import com.test.kick_off_app.network.StarStadiumApi
import com.test.kick_off_app.network.SuccessfulResponse
import com.test.kick_off_app.network.UnstarStadiumApi
import okhttp3.RequestBody
import retrofit2.create

class Repository {
    private val getStadiumClient = RetrofitInstance.getInstance().create(GetStadiumApi::class.java)
    private val getStadiumDetailClient = RetrofitInstance.getInstance().create(GetStadiumDetailApi::class.java)
    private val kakaoLoginClient = RetrofitInstance.getInstance().create(KakaoLoginApi::class.java)
    private val authClient = RetrofitInstance.getInstance().create(AuthApi::class.java)
    private val kakaoSignupClient = RetrofitInstance.getInstance().create(KakaoSignupApi::class.java)
    private val getPreSignedUrlClient = RetrofitInstance.getInstance().create(GetPreSignedUrlApi::class.java)
    private val imageUploadClient = RetrofitInstance.getInstance().create(ImageUploadApi::class.java)
    private val getStarredStadiumClient = RetrofitInstance.getInstance().create(GetStarredStadiumApi::class.java)
    private val starStadiumClient = RetrofitInstance.getInstance().create(StarStadiumApi::class.java)
    private val unStarStadiumClient = RetrofitInstance.getInstance().create(UnstarStadiumApi::class.java)
    private val getStarredRefereeClient = RetrofitInstance.getInstance().create(GetStarredRefereeApi::class.java)
    private val getMyRefereeClient = RetrofitInstance.getInstance().create(GetMyRefereeApi::class.java)

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

    suspend fun getStadiumDetail(stadiumId: Int): NetworkResponse<SuccessfulResponse<StadiumDetail>, ErrorResponse>{
        return getStadiumDetailClient.getStadiumDetail(stadiumId)
    }

    suspend fun getPreSignedUrl(fileType: FileTypeEnum, fileName: String): NetworkResponse<SuccessfulResponse<FileData>, ErrorResponse>{
        val request = FileUploadRequest(fileType, fileName)
        return getPreSignedUrlClient.getPreSignedUrl(request)
    }

    suspend fun imageUpload(preSignedUrl: String, image: RequestBody): NetworkResponse<Unit, ErrorResponse>{
        return imageUploadClient.imageUpload(preSignedUrl, image)
    }

    suspend fun getStarredStadium(): NetworkResponse<SuccessfulResponse<List<Stadium>>, ErrorResponse>{
        return getStarredStadiumClient.getStarredStadium()
    }

    suspend fun starStadium(stadiumId: Int): NetworkResponse<SuccessfulResponse<Unit>, ErrorResponse>{
        return starStadiumClient.starStadium(stadiumId)
    }

    suspend fun unStarStadium(stadiumId: Int): NetworkResponse<SuccessfulResponse<Unit>, ErrorResponse>{
        return unStarStadiumClient.unStarStadium(stadiumId)
    }

    suspend fun getStarredReferee(isHiring: Boolean): NetworkResponse<SuccessfulResponse<List<Referee>>, ErrorResponse>{
        return getStarredRefereeClient.getStarredReferee(isHiring)
    }

    suspend fun getMyReferee(isHiring: Boolean): NetworkResponse<SuccessfulResponse<List<Referee>>, ErrorResponse>{
        return getMyRefereeClient.getMyReferee(isHiring)
    }
}