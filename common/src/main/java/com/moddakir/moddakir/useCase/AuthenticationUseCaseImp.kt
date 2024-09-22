package com.moddakir.moddakir.useCase

import com.moddakir.moddakir.model.response.BaseResponse
import com.moddakir.moddakir.model.response.ModdakirResponse
import com.moddakir.moddakir.model.response.OTPResponseModel
import com.moddakir.moddakir.model.response.ResponseModel
import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.remote.RemoteRepository
import com.moddakir.moddakir.network.remote.RemoteRepositoryImp
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

}