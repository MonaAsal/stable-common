package com.moddakir.moddakir.network.remote.services

import com.moddakir.moddakir.model.response.ModdakirResponse
import com.moddakir.moddakir.model.response.OTPResponseModel
import com.moddakir.moddakir.model.response.ResponseModel
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface NonAuthService {
    @FormUrlEncoded
    @POST("student-sign-in")
    suspend fun login(
        @Field("email") email: String?,
        @Field("username") username: String?,
        @Field("password") password: String,
        @Field("lang") lang: String,
        @Field("deviceUUID") deviceUUID: String,
    ): Response<ModdakirResponse<ResponseModel>>

    @FormUrlEncoded
    @POST("teacher-signin")
    suspend fun loginTeacher(
        @Field("email") email: String?,
        @Field("username") username: String?,
        @Field("password") password: String,
        @Field("lang") lang: String,
    ): Response<ModdakirResponse<ResponseModel>>


    @FormUrlEncoded
    @POST("send-reset-password-code")
    suspend fun forgetPassword(
        @Field("email") email: String?,
        @Field("phone") phone: String?,
        @Field("lang") lang: String,
        @Field("captcha-token") token: String,
    ): Response<ModdakirResponse<ResponseModel>>


    @FormUrlEncoded
    @POST("send-phone-verification-code")
    suspend fun sendPhoneVerifyNum(
        @Field("phone") phone: String?,
        @Field("captcha-token") token: String,
    ): Response<ModdakirResponse<OTPResponseModel>>

    @FormUrlEncoded
    @POST("new-send-verification-code")
    suspend fun loginWithMobile(
        @Field("phone") phone: String?,
        @Field("captcha-token") token: String,
        @Field("isRegister") isRegister: Boolean,

    ): Response<ModdakirResponse<OTPResponseModel>>



}