package com.moddakir.moddakir.network.remote

import android.annotation.SuppressLint
import android.provider.Settings
import android.util.Log
import com.google.android.datatransport.runtime.dagger.Module
import com.google.android.datatransport.runtime.dagger.Provides
import com.google.gson.Gson
import com.moddakir.moddakir.App.Companion.context
import com.moddakir.moddakir.model.ErrorModel
import com.moddakir.moddakir.model.response.BaseResponse
import com.moddakir.moddakir.model.response.ErrorResponse
import com.moddakir.moddakir.model.response.ModdakirResponse
import com.moddakir.moddakir.model.response.OTPResponseModel
import com.moddakir.moddakir.model.response.ResponseModel
import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.remote.services.AuthService
import com.moddakir.moddakir.network.remote.services.NonAuthService
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RemoteRepository
@Inject constructor() : RemoteSource {
    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            Log.e("responseCode", responseCode.toString())
            if (response.isSuccessful) {
                if (response.body().toString().contains("status=true")
                    || (response.body() is BaseResponse && (response.body() as BaseResponse).status
                            && (response.body() as BaseResponse).status)
                ) {
                    response.body()
                } else {
                    try {
                        val baseResponse = (response.body() as BaseResponse)

                        val list = ArrayList<ErrorModel>()
                        list.add(ErrorModel("400", baseResponse.message!!))
                        var errorResponse = ErrorResponse(list.toList())
                        errorResponse.status = false
                        errorResponse
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            } else {
                val errorBody =
                    response.errorBody()

                try {
                    val errorResponse =
                        Gson().fromJson<ErrorResponse>(
                            errorBody?.string(),
                            ErrorResponse::class.java
                        )
                    errorResponse
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    @SuppressLint("HardwareIds")
    override suspend fun requestLogin(
        email: String?,
        userName: String?,
        password: String,
        lang: String,

        ): Resource<ModdakirResponse<ResponseModel>> {
        val nonAuthService = ServiceGenerator.createService(NonAuthService::class.java, false)
        val deviceUUID =
            Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        val response =
            processCall { nonAuthService.login(email, userName, password, lang, deviceUUID) }
        return try {
            var res = response as ModdakirResponse<ResponseModel>
            Resource.Success(data = res)
        } catch (e: Exception) {
            Resource.DataError(errorResponse = response as ErrorResponse)
        }
    }

    override suspend fun requestLoginTeacher(
        email: String?,
        userName: String?,
        password: String,
        lang: String,

        ): Resource<ModdakirResponse<ResponseModel>> {
        val nonAuthService = ServiceGenerator.createService(NonAuthService::class.java, false)
        val response = processCall { nonAuthService.loginTeacher(email, userName, password, lang) }
        return try {
            var res = response as ModdakirResponse<ResponseModel>
            Resource.Success(data = res)
        } catch (e: Exception) {
            Resource.DataError(errorResponse = response as ErrorResponse)
        }
    }


    override suspend fun requestForgetPassword(
        email: String?,
        phone: String?,
        lang: String,
        token: String,

        ): Resource<ModdakirResponse<ResponseModel>> {
        val nonAuthService = ServiceGenerator.createService(NonAuthService::class.java, false)
        val response = processCall { nonAuthService.forgetPassword(email, phone, lang, token) }
        return try {
            var res = response as ModdakirResponse<ResponseModel>
            Resource.Success(data = res)
        } catch (e: Exception) {
            Resource.DataError(errorResponse = response as ErrorResponse)
        }
    }

    override suspend fun sendPhoneVerifyNum(
        phone: String?,
        token: String,

        ): Resource<ModdakirResponse<OTPResponseModel>> {
        val nonAuthService = ServiceGenerator.createService(NonAuthService::class.java, false)
        val response = processCall { nonAuthService.sendPhoneVerifyNum(phone, token) }
        return try {
            var res = response as ModdakirResponse<OTPResponseModel>
            Resource.Success(data = res)
        } catch (e: Exception) {
            Resource.DataError(errorResponse = response as ErrorResponse)
        }
    }

    override suspend fun loginWithMobile(
        phone: String?,
        token: String,
        isFromSocialRegister: Boolean
    ): Resource<ModdakirResponse<OTPResponseModel>> {
        val nonAuthService = ServiceGenerator.createService(NonAuthService::class.java, false)
        val response =
            processCall { nonAuthService.loginWithMobile(phone, token, isFromSocialRegister) }
        return try {
            var res = response as ModdakirResponse<OTPResponseModel>
            Resource.Success(data = res)
        } catch (e: Exception) {
            Resource.DataError(errorResponse = response as ErrorResponse)
        }
    }

    override suspend fun validateOTP(
        code: String,
        providerType: String?,
        providerUserId: String?,
        deviceUUID: String,
        isSocialMediaAccountPending: Boolean?
    ): Resource<ModdakirResponse<ResponseModel>> {
        val authService = ServiceGenerator.createService(AuthService::class.java, false)
        val response = processCall {
            authService.validateOTP(
                code,
                providerType,
                providerUserId,
                deviceUUID,
                isSocialMediaAccountPending
            )
        }
        return try {
            var res = response as ModdakirResponse<ResponseModel>
            Resource.Success(data = res)
        } catch (e: Exception) {
            Resource.DataError(errorResponse = response as ErrorResponse)
        }
    }

    override suspend fun validatePhoneNumber(
        code: String,
        providerType: String?,
        providerUserId: String?,
        deviceUUID: String,
        isSocialMediaAccountPending: Boolean?
    ): Resource<ModdakirResponse<BaseResponse>> {
        val authService = ServiceGenerator.createService(AuthService::class.java, false)
        val response = processCall {
            authService.validatePhoneNumber(
                code,
                providerType,
                providerUserId,
                deviceUUID,
                isSocialMediaAccountPending
            )
        }
        return try {
            var res = response as ModdakirResponse<BaseResponse>
            Resource.Success(data = res)
        } catch (e: Exception) {
            Resource.DataError(errorResponse = response as ErrorResponse)
        }
    }

    override suspend fun resetPassword(
        password: String
    ): Resource<ModdakirResponse<ResponseModel>> {
        val authService = ServiceGenerator.createService(AuthService::class.java, false)
        val response = processCall {
            authService.resetPassword(
                password
            )
        }
        return try {
            var res = response as ModdakirResponse<ResponseModel>
            Resource.Success(data = res)
        } catch (e: Exception) {
            Resource.DataError(errorResponse = response as ErrorResponse)
        }
    }


}