package com.moddakir.moddakir.network.remote.services

import com.moddakir.moddakir.model.response.BaseResponse
import com.moddakir.moddakir.model.response.ModdakirResponse
import com.moddakir.moddakir.model.response.OTPResponseModel
import com.moddakir.moddakir.model.response.ResponseModel
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface AuthService {
    @FormUrlEncoded
    @POST("validate-code-parent-login")
    suspend fun validateOTP(
        @Field("code") code: String?,
        @Field("providerType") providerType: String?,
        @Field("providerUserId") providerUserId: String?,
        @Field("deviceUUID") deviceUUID: String,
        @Field("isSocialMediaAccountPending") isSocialMediaAccountPending: Boolean?,

        ): Response<ModdakirResponse<ResponseModel>>

    @FormUrlEncoded
    @POST("verify-phone-number")
    suspend fun validatePhoneNumber(
        @Field("code") code: String,
        @Field("providerType") providerType: String?,
        @Field("providerUserId") providerUserId: String?,
        @Field("deviceUUID") deviceUUID: String,
        @Field("isSocialMediaAccountPending") isSocialMediaAccountPending: Boolean?,

        ): Response<ModdakirResponse<BaseResponse>>

    @FormUrlEncoded
    @POST("reset-password")
    suspend fun resetPassword(
        @Field("password") password: String
        ): Response<ModdakirResponse<ResponseModel>>


}