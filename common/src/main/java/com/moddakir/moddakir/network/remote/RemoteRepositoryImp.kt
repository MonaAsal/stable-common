package com.moddakir.moddakir.network.remote

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.util.Log
import com.google.gson.Gson
import com.moddakir.moddakir.App
import com.moddakir.moddakir.App.Companion.context
import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.TicketReplyResponse
import com.moddakir.moddakir.network.model.response.BannerResponseModel
import com.moddakir.moddakir.network.model.response.BaseResponse
import com.moddakir.moddakir.network.model.response.ErrorResponse
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.model.response.OTPResponseModel
import com.moddakir.moddakir.network.model.response.ResponseModel
import com.moddakir.moddakir.network.model.response.SocialResponse
import com.moddakir.moddakir.network.model.response.TicketResponse
import com.moddakir.moddakir.network.model.response.TicketsRepliesResponse
import com.moddakir.moddakir.network.model.response.TicketsResponse
import com.moddakir.moddakir.network.remote.services.AuthService
import com.moddakir.moddakir.network.remote.services.NonAuthService
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RemoteRepositoryImp @Inject constructor() : RemoteRepository {
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

                        val list = ArrayList<com.moddakir.moddakir.network.model.ErrorModel>()
                        list.add(
                            com.moddakir.moddakir.network.model.ErrorModel(
                                "400",
                                baseResponse.message!!
                            )
                        )
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
        val authService = ServiceGenerator.createService(AuthService::class.java, true)
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
        val authService = ServiceGenerator.createService(AuthService::class.java, true)
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
        val authService = ServiceGenerator.createService(AuthService::class.java, true)
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

    @SuppressLint("HardwareIds")
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
        val nonAuthService = ServiceGenerator.createService(NonAuthService::class.java, false)
        val deviceUUID=Settings.Secure.getString(App.context.contentResolver, Settings.Secure.ANDROID_ID)
        val response = processCall {
            nonAuthService.signInWithSocial(
                email,name,id,gender,avatarUrl,provider,token,lang,deviceUUID
            )
        }
        return try {
            var res = response as ModdakirResponse<SocialResponse>
            Resource.Success(data = res)
        } catch (e: Exception) {
            Resource.DataError(errorResponse = response as ErrorResponse)
        }
    }

    override suspend fun logout(): Resource<ModdakirResponse<ResponseModel>> {
        val authService = ServiceGenerator.createService(AuthService::class.java, true)
        val response = processCall {
            authService.logout()
        }
        return try {
            var res = response as ModdakirResponse<ResponseModel>
            Resource.Success(data = res)
        } catch (e: Exception) {
            Resource.DataError(errorResponse = response as ErrorResponse)
        }
    }

    override suspend fun getAboutUs(): Resource<ModdakirResponse<com.moddakir.moddakir.network.model.AboutModel>> {
        val authService = ServiceGenerator.createService(AuthService::class.java, true)
        val response = processCall {
            authService.aboutUs()
        }
        return try {
            var res = response as ModdakirResponse<com.moddakir.moddakir.network.model.AboutModel>
            Resource.Success(data = res)
        } catch (e: Exception) {
            Resource.DataError(errorResponse = response as ErrorResponse)
        }
    }

    override suspend fun contactUsForm(
        message: String,
        title: String
    ): Resource<ModdakirResponse<ResponseModel>> {
        val authService = ServiceGenerator.createService(AuthService::class.java, true)
        val response = processCall {
            authService.contactUsForm(message,title)
        }
        return try {
            var res = response as ModdakirResponse<ResponseModel>
            Resource.Success(data = res)
        } catch (e: Exception) {
            Resource.DataError(errorResponse = response as ErrorResponse)
        }
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
        val nonAuthService = ServiceGenerator.createService(NonAuthService::class.java, false)
        val response = processCall {
            nonAuthService.submitJoinUs(firstName,managerId,programType,username,email,phone,nationality,educationLevel,deviceUUID,gender,education,password)
        }
        return try {
            var res = response as ModdakirResponse<ResponseModel>
            Resource.Success(data = res)
        } catch (e: Exception) {
            Resource.DataError(errorResponse = response as ErrorResponse)
        }
    }

    override suspend fun changeSettings(
        enableVoiceRecording: String,
        enableVideoRecording: String
    ): Resource<ModdakirResponse<ResponseModel>> {
        val authService = ServiceGenerator.createService(AuthService::class.java, true)
        val response = processCall {
            authService.changeSettings(enableVoiceRecording,enableVideoRecording)
        }
        return try {
            var res = response as ModdakirResponse<ResponseModel>
            Resource.Success(data = res)
        } catch (e: Exception) {
            Resource.DataError(errorResponse = response as ErrorResponse)
        }
    }

    override suspend fun getListContactUs(page: Int): Resource<ModdakirResponse<TicketsResponse>> {
        val authService = ServiceGenerator.createService(AuthService::class.java, true)
        val response = processCall {
            authService.getListContactUs(page,20)
        }
        return try {
            var res = response as ModdakirResponse<TicketsResponse>
            Resource.Success(data = res)
        } catch (e: Exception) {
            Resource.DataError(errorResponse = response as ErrorResponse)
        }
    }

    override suspend fun sendReplay(message: String, ticketId: String): Resource<ModdakirResponse<TicketReplyResponse>> {
        val authService = ServiceGenerator.createService(AuthService::class.java, true)
        val response = processCall {
            authService.sendReplay(message,ticketId)
        }
        return try {
            var res = response as ModdakirResponse<TicketReplyResponse>
            Resource.Success(data = res)
        } catch (e: Exception) {
            Resource.DataError(errorResponse = response as ErrorResponse)
        }
    }

    override suspend fun getTicketReplies(
        page: Int,
        messageId: String
    ): Resource<ModdakirResponse<TicketsRepliesResponse>> {
        val authService = ServiceGenerator.createService(AuthService::class.java, true)
        val response = processCall {
            authService.getTicketReplies(page,20,messageId)
        }
        return try {
            var res = response as ModdakirResponse<TicketsRepliesResponse>
            Resource.Success(data = res)
        } catch (e: Exception) {
            Resource.DataError(errorResponse = response as ErrorResponse)
        }
    }

    override suspend fun getTicketById(ticketId: String): Resource<ModdakirResponse<TicketResponse>> {
        val authService = ServiceGenerator.createService(AuthService::class.java, true)
        val response = processCall {
            authService.getTicketById(ticketId)
        }
        return try {
            var res = response as ModdakirResponse<TicketResponse>
            Resource.Success(data = res)
        } catch (e: Exception) {
            Resource.DataError(errorResponse = response as ErrorResponse)
        }
    }

    override suspend fun getBanners(): Resource<ModdakirResponse<BannerResponseModel>> {
        val authService = ServiceGenerator.createService(AuthService::class.java, true)
        val response = processCall {
            authService.getBanners()
        }
        return try {
            var res = response as ModdakirResponse<BannerResponseModel>
            Resource.Success(data = res)
        } catch (e: Exception) {
            Resource.DataError(errorResponse = response as ErrorResponse)
        }
    }


}