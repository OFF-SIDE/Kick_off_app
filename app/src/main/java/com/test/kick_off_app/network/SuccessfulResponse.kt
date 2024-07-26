package com.test.kick_off_app.network

data class SuccessfulResponse<T>(
    val code: Int,
    val message: String,
    val data: T
)

data class ErrorResponse(
    val errorCode: Int,
    val httpStatus: Int,
    val message: String
)

data class AccessToken(val accessToken: String)