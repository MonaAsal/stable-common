package com.moddakir.moddakir.useCase

import android.content.Context
import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.response.BaseResponse
import com.moddakir.moddakir.network.model.response.DependentMangersModel
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.model.response.OTPResponseModel
import com.moddakir.moddakir.network.model.response.ResponseModel
import com.moddakir.moddakir.network.model.response.SocialResponse


interface AuthenticationUseCase {
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
        isFromSocialRegister: Boolean
    ): Resource<ModdakirResponse<OTPResponseModel>>

    suspend fun validateOTP(
        code: String,
        providerType: String?,
        providerUserId: String?,
        deviceUUID: String,
        isSocialMediaAccountPending: Boolean?
    ): Resource<ModdakirResponse<ResponseModel>>

    suspend fun validatePhoneNumber(
        code: String,
        providerType: String?,
        providerUserId: String?,
        deviceUUID: String,
        isSocialMediaAccountPending: Boolean?
    ): Resource<ModdakirResponse<BaseResponse>>

    suspend fun resetPassword(
        password: String
    ): Resource<ModdakirResponse<ResponseModel>>

    suspend fun signInWithSocial(
        email: String, name: String, id: String, gender: String,
        avatarUrl: String, provider: String,
        token: String, lang: String, context: Context
    ): Resource<ModdakirResponse<SocialResponse>>

    suspend fun getDependentManagers(
        studentId: String,
        forSwitchingProgramsPage: Boolean,
        page: Int,
        pageSize: Int
    ): Resource<ModdakirResponse<DependentMangersModel>>

    suspend fun submitJoinUs(
        firstName: String,
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
        password: String
    ): Resource<ModdakirResponse<ResponseModel>>



    suspend fun logout(): Resource<ModdakirResponse<ResponseModel>>
}
