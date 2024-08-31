package com.test.kick_off_app.network

import com.haroldadmin.cnradapter.NetworkResponse
import com.test.kick_off_app.data.FileData
import com.test.kick_off_app.data.FileTypeEnum
import com.test.kick_off_app.data.FileUploadRequest
import com.test.kick_off_app.data.Referee
import com.test.kick_off_app.data.Response
import com.test.kick_off_app.data.SignupInfo
import com.test.kick_off_app.data.Stadium
import com.test.kick_off_app.data.StadiumDetail
import com.test.kick_off_app.data.UserInfo
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

// 지역구 기중 구장 리스트 받기
interface GetStadiumApi{
    @GET("stadium/location")
    suspend fun getStadium(@Query("location") locations: String? = null, @Query("category") category: String? = null): NetworkResponse<SuccessfulResponse<List<Stadium>>, ErrorResponse>
}

// 구장 상세보기
interface GetStadiumDetailApi{
    @GET("stadium/{stadiumId}")
    suspend fun getStadiumDetail(
        @Path("stadiumId") stadiumId: Int
    ) : NetworkResponse<SuccessfulResponse<StadiumDetail>, ErrorResponse>
}

// 카카오 토큰(내의 oauthId)을 통해 로그인
interface KakaoLoginApi{
    @POST("auth/login/kakao")
    suspend fun kakaoLogin(@Body oauthId:String): NetworkResponse<SuccessfulResponse<AccessToken>, ErrorResponse>
}

// 내 정보 불러오기
interface AuthApi{
    @GET("auth")
    suspend fun auth(): NetworkResponse<SuccessfulResponse<UserInfo>, ErrorResponse>
}

// 입력받은 정보를 통해 카카오 회원가입
interface KakaoSignupApi{
    @POST("auth/signup/kakao")
    suspend fun kakaoSignup(@Body signupInfo: SignupInfo): NetworkResponse<SuccessfulResponse<AccessToken>, ErrorResponse>
}

// 이미지 업로드를 위한 preSignedUrl 요청
interface GetPreSignedUrlApi{
    @POST("file/upload")
    suspend fun getPreSignedUrl(@Body fileUploadRequest: FileUploadRequest): NetworkResponse<SuccessfulResponse<FileData>, ErrorResponse>
}

// preSignedUrl을 통해 이미지 업로드
interface ImageUploadApi{
    @PUT("{preSignedUrl}")
    suspend fun imageUpload(
        @Path("preSignedUrl") preSignedUrl: String,
        @Body image: RequestBody
    ): NetworkResponse<Unit,ErrorResponse>
}

// 즐겨찾기 된 구장 리스트 불러오기
interface GetStarredStadiumApi{
    @GET("stadium/star")
    suspend fun getStarredStadium(): NetworkResponse<SuccessfulResponse<List<Stadium>>, ErrorResponse>
}

// 구장 즐겨찾기 추가
interface StarStadiumApi{
    @POST("stadium/{stadiumId}/star")
    suspend fun starStadium(
        @Path("stadiumId") stadiumId: Int
    ): NetworkResponse<SuccessfulResponse<Unit>, ErrorResponse>
}

// 구장 즐겨찾기 해제
interface UnstarStadiumApi{
    @POST("stadium/{stadiumId}/unstar")
    suspend fun unStarStadium(
        @Path("stadiumId") stadiumId: Int
    ): NetworkResponse<SuccessfulResponse<Unit>, ErrorResponse>
}

interface GetStarredRefereeApi{
    @POST("referee/star")
    suspend fun getStarredReferee(
        @Query("isHiring") isHiring: Boolean
    ): NetworkResponse<SuccessfulResponse<List<Referee>>, ErrorResponse>
}

interface GetMyRefereeApi{
    @GET("referee/me")
    suspend fun getMyReferee(
        @Query("isHiring") isHiring: Boolean
    ): NetworkResponse<SuccessfulResponse<List<Referee>>, ErrorResponse>
}