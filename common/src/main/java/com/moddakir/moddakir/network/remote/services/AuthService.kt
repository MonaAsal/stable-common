package com.moddakir.moddakir.network.remote.services

import com.moddakir.moddakir.network.model.TicketReplyResponse
import com.moddakir.moddakir.network.model.response.BannerResponseModel
import com.moddakir.moddakir.network.model.response.BaseResponse
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.model.response.ResponseModel
import com.moddakir.moddakir.network.model.response.TicketResponse
import com.moddakir.moddakir.network.model.response.TicketsRepliesResponse
import com.moddakir.moddakir.network.model.response.TicketsResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
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


    @FormUrlEncoded
    @POST("student-logout")
    fun logout(): Response<ModdakirResponse<ResponseModel>>


    @FormUrlEncoded
    @GET("about-us")
    fun aboutUs(): Response<ModdakirResponse<com.moddakir.moddakir.network.model.AboutModel>>

    @FormUrlEncoded
    @POST("contact-us")
    fun contactUsForm(
        @Field("message") message: String,
        @Field("title") title: String
    ): Response<ModdakirResponse<ResponseModel>>

    @FormUrlEncoded
    @POST("student-set-call-setting")
    fun changeSettings(
        @Field("enableVoiceRecording") message: String,
        @Field("enableVideoRecording") title: String
    ): Response<ModdakirResponse<ResponseModel>>


    @FormUrlEncoded
    @POST("get-list-contactUs")
    fun getListContactUs(
        @Field("pageIndex") pageIndex: Int,
        @Field("pageSize") pageSize: Int
    ): Response<ModdakirResponse<TicketsResponse>>

    @FormUrlEncoded
    @POST("get-list-replies-contactUs")
    fun getTicketReplies(
        @Field("pageIndex") pageIndex: Int,
        @Field("pageSize") pageSize: Int,
        @Field("messageId") messageId: String
    ): Response<ModdakirResponse<TicketsRepliesResponse>>

    @FormUrlEncoded
    @POST("reply-message")
    fun sendReplay(
        @Field("message") message: String,
        @Field("id") id: String,
    ): Response<ModdakirResponse<TicketReplyResponse>>


    @FormUrlEncoded
    @POST("get-contact-us-by-id")
    fun getTicketById(
        @Field("id") id: String,
    ): Response<ModdakirResponse<TicketResponse>>

    @FormUrlEncoded
    @GET("get-banners")
    fun getBanners(): Response<ModdakirResponse<BannerResponseModel>>



}