package com.moddakir.moddakir.network.remote


import com.moddakir.moddakir.model.response.BaseResponse
import com.moddakir.moddakir.model.response.ModdakirResponse
import com.moddakir.moddakir.model.response.OTPResponseModel
import com.moddakir.moddakir.model.response.ResponseModel
import com.moddakir.moddakir.network.Resource
 interface RemoteSource {
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


}
