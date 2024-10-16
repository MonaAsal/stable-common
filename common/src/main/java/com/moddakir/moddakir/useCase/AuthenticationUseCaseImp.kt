package com.moddakir.moddakir.useCase

import android.content.Context
import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.response.BaseResponse
import com.moddakir.moddakir.network.model.response.DependentMangersModel
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.model.response.OTPResponseModel
import com.moddakir.moddakir.network.model.response.ResponseModel
import com.moddakir.moddakir.network.model.response.SocialResponse
import com.moddakir.moddakir.network.remote.RemoteRepository
import javax.inject.Inject

class AuthenticationUseCaseImp @Inject constructor(
    private val repository: RemoteRepository
) : AuthenticationUseCase {
    override suspend fun requestLogin(
        email: String?,
        userName: String?,
        password: String,
        lang: String
    ): Resource<ModdakirResponse<ResponseModel>> {
        return repository.requestLogin(email,userName,password,lang)
    }


    override suspend fun requestLoginTeacher(
        email: String?,
        userName: String?,
        password: String,
        lang: String
    ): Resource<ModdakirResponse<ResponseModel>> {
        return repository.requestLoginTeacher(email,userName,password,lang)
    }

    override suspend fun requestForgetPassword(
        email: String?,
        phone: String?,
        lang: String,
        token: String
    ): Resource<ModdakirResponse<ResponseModel>> {
        return repository.requestForgetPassword(email,phone,lang,token)
    }

    override suspend fun sendPhoneVerifyNum(
        phone: String?,
        token: String
    ): Resource<ModdakirResponse<OTPResponseModel>> {
        return repository.sendPhoneVerifyNum(phone,token)
    }

    override suspend fun loginWithMobile(
        phone: String?,
        token: String,
        isFromSocialRegister: Boolean
    ): Resource<ModdakirResponse<OTPResponseModel>> {
        return repository.loginWithMobile(phone,token,isFromSocialRegister)
    }

    override suspend fun validateOTP(
        code: String,
        providerType: String?,
        providerUserId: String?,
        deviceUUID: String,
        isSocialMediaAccountPending: Boolean?
    ): Resource<ModdakirResponse<ResponseModel>> {
        return repository.validateOTP(code,providerType,providerUserId,deviceUUID,isSocialMediaAccountPending)
    }

    override suspend fun validatePhoneNumber(
        code: String,
        providerType: String?,
        providerUserId: String?,
        deviceUUID: String,
        isSocialMediaAccountPending: Boolean?
    ): Resource<ModdakirResponse<BaseResponse>> {
        return repository.validatePhoneNumber(code,providerType,providerUserId,deviceUUID,isSocialMediaAccountPending)
    }

    override suspend fun resetPassword(password: String): Resource<ModdakirResponse<ResponseModel>> {
        return repository.resetPassword(password)
    }

    override suspend fun signInWithSocial(
        email: String,
        name: String,
        id: String,
        gender: String,
        avatarUrl: String,
        provider: String,
        token: String,
        lang: String,
        context: Context
    ): Resource<ModdakirResponse<SocialResponse>> {
        return repository.signInWithSocial(email,name,id,gender,avatarUrl,provider,token,lang,context)
    }

    override suspend fun getDependentManagers(
        studentId: String,
        forSwitchingProgramsPage: Boolean,
        page: Int,
        pageSize: Int
    ): Resource<ModdakirResponse<DependentMangersModel>> {
        return repository.getDependentManagers(studentId,forSwitchingProgramsPage,page,pageSize)
    }

    override suspend fun submitJoinUs(
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
    ): Resource<ModdakirResponse<ResponseModel>> {
        return repository.submitJoinUs(firstName,managerId,programType,username,email,phone,nationality,educationLevel,deviceUUID,gender,education,password)

    }

    override suspend fun logout(): Resource<ModdakirResponse<ResponseModel>> {
        return repository.logout()
    }


}