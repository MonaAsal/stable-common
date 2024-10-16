package com.moddakir.moddakir.network.remote.services

import com.moddakir.moddakir.network.model.response.DependentMangersModel
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.model.response.OTPResponseModel
import com.moddakir.moddakir.network.model.response.ResponseModel
import com.moddakir.moddakir.network.model.response.SocialResponse
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

    @FormUrlEncoded
    @POST("sign-in-social")
    suspend fun signInWithSocial(
        @Field("email") email: String?,
        @Field("fullName") name: String,
        @Field("providerUserId") providerUserId: String,
        @Field("gender") gender: String?,
        @Field("avatarUrl") avatarUrl: String?,
        @Field("providerType") providerType: String,
        @Field("providerToken") providerToken: String,
        @Field("lang") lang: String,
        @Field("deviceUUID") deviceUUID: String,
    ): Response<ModdakirResponse<SocialResponse>>

    @FormUrlEncoded
    @POST("student-join-us")
    suspend fun submitJoinUs(
        @Field("firstName") firstName: String,
        @Field("managerId") managerId: String,
        @Field("programType") programType: String,
        @Field("username") username: String?,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("nationality") nationality: String,
        @Field("educationLevel") educationLevel: String,
        @Field("deviceUUID") deviceUUID: String,
        @Field("gender") gender: String,
        @Field("education") education: com.moddakir.moddakir.network.model.Education,
        @Field("password") password: String,
    ): Response<ModdakirResponse<ResponseModel>>


    @FormUrlEncoded
    @POST("dependent-managers")
    suspend fun getDependentManagers(
        @Field("studentId") studentId: String,
        @Field("forSwitchingProgramsPage") forSwitchingProgramsPage: Boolean,
        @Field("page") page: Int,
        @Field("pageSize") pageSize: Int,

        ): Response<ModdakirResponse<DependentMangersModel>>

}