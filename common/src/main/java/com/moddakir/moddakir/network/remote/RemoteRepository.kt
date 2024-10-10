package com.moddakir.moddakir.network.remote

import android.content.Context
import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.TicketReplyResponse
import com.moddakir.moddakir.network.model.response.BannerResponseModel
import com.moddakir.moddakir.network.model.response.BaseResponse
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.model.response.OTPResponseModel
import com.moddakir.moddakir.network.model.response.ResponseModel
import com.moddakir.moddakir.network.model.response.SocialResponse
import com.moddakir.moddakir.network.model.response.TicketResponse
import com.moddakir.moddakir.network.model.response.TicketsRepliesResponse
import com.moddakir.moddakir.network.model.response.TicketsResponse


interface RemoteRepository {
    suspend fun requestLogin(
        email: String?,
        userName: String?,
        password: String,
        lang: String
    ): Resource<ModdakirResponse<ResponseModel>>

    suspend fun requestLoginTeacher(
        email: String?,
        userName: String?,
        password: String,
        lang: String
    ): Resource<ModdakirResponse<ResponseModel>>
    suspend fun requestForgetPassword(
        email: String?,
        phone: String?,
        lang: String,
        token: String
    ): Resource<ModdakirResponse<ResponseModel>>
    suspend fun sendPhoneVerifyNum(
        phone: String?,
        token: String
    ): Resource<ModdakirResponse<OTPResponseModel>>
    suspend fun loginWithMobile(
        phone: String?,
        token: String,
        isFromSocialRegister:Boolean
    ): Resource<ModdakirResponse<OTPResponseModel>>
    suspend fun validateOTP(
        code: String,
        providerType: String?,
        providerUserId: String?,
        deviceUUID: String,
        isSocialMediaAccountPending:Boolean?
    ): Resource<ModdakirResponse<ResponseModel>>

    suspend fun validatePhoneNumber(
        code: String,
        providerType: String?,
        providerUserId: String?,
        deviceUUID: String,
        isSocialMediaAccountPending:Boolean?
    ): Resource<ModdakirResponse<BaseResponse>>

    suspend fun resetPassword(
        password: String
    ): Resource<ModdakirResponse<ResponseModel>>

    suspend fun  signInWithSocial( email:String,  name:String,  id:String,  gender:String,
                                   avatarUrl:String,  provider:String,
                                   token:String,  lang:String,  context: Context
    ) : Resource<ModdakirResponse<SocialResponse>>

    suspend fun logout(): Resource<ModdakirResponse<ResponseModel>>
    suspend fun getAboutUs(): Resource<ModdakirResponse<com.moddakir.moddakir.network.model.AboutModel>>

    suspend fun contactUsForm(message: String,
                              title: String): Resource<ModdakirResponse<ResponseModel>>

    suspend fun submitJoinUs(firstName: String,
                             managerId: String,
                             programType: String,
                             username: String,
                             email: String,
                             phone: String,
                             nationality: String,
                             educationLevel: String,
                             deviceUUID: String,
                             gender: String,
                             education: com.moddakir.moddakir.network.model.Education,
                             password: String): Resource<ModdakirResponse<ResponseModel>>

    suspend fun changeSettings(enableVoiceRecording:String,enableVideoRecording:String): Resource<ModdakirResponse<ResponseModel>>

    suspend fun getListContactUs(page: Int): Resource<ModdakirResponse<TicketsResponse>>

    suspend fun sendReplay(message:String, ticketId: String): Resource<ModdakirResponse<TicketReplyResponse>>

    suspend fun getTicketReplies(page:Int,messageId:String): Resource<ModdakirResponse<TicketsRepliesResponse>>

    suspend fun getTicketById( ticketId: String): Resource<ModdakirResponse<TicketResponse>>

    suspend fun getBanners( ): Resource<ModdakirResponse<BannerResponseModel>>

}