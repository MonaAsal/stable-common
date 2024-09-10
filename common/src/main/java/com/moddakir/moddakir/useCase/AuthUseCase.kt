package com.moddakir.moddakir.useCase

import androidx.lifecycle.MutableLiveData
import com.google.android.datatransport.runtime.dagger.Module
import com.moddakir.moddakir.model.base.error.Error.Companion.NETWORK_ERROR
import com.moddakir.moddakir.model.response.BaseResponse
import com.moddakir.moddakir.model.response.ModdakirResponse
import com.moddakir.moddakir.model.response.OTPResponseModel
import com.moddakir.moddakir.model.response.ResponseModel
import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.remote.RemoteSource
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AuthUseCase  @Inject
constructor(
    private val dataSource: RemoteSource,
    override val coroutineContext: CoroutineContext
) : CoroutineScope {
    private val loginMutableLiveData = MutableLiveData<Resource<ModdakirResponse<ResponseModel>>>()
    private val loginTeacherMutableLiveData = MutableLiveData<Resource<ModdakirResponse<ResponseModel>>>()
    private val forgetPasswordMutableLiveData = MutableLiveData<Resource<ModdakirResponse<ResponseModel>>>()
    private val sendPhoneVerifyNumMutableLiveData = MutableLiveData<Resource<ModdakirResponse<OTPResponseModel>>>()
    private val validateOTPMutableLiveData = MutableLiveData<Resource<ModdakirResponse<ResponseModel>>>()
    private val validatePhoneNumberMutableLiveData = MutableLiveData<Resource<ModdakirResponse<BaseResponse>>>()
    private val resetPasswordMutableLiveData = MutableLiveData<Resource<ModdakirResponse<ResponseModel>>>()

    val loginLiveData: MutableLiveData<Resource<ModdakirResponse<ResponseModel>>> = loginMutableLiveData
    val loginTeacherLiveData: MutableLiveData<Resource<ModdakirResponse<ResponseModel>>> = loginTeacherMutableLiveData
    val sendForgetPasswordLiveData: MutableLiveData<Resource<ModdakirResponse<ResponseModel>>> = forgetPasswordMutableLiveData
    val sendPhoneVerifyNumLiveData: MutableLiveData<Resource<ModdakirResponse<OTPResponseModel>>> = sendPhoneVerifyNumMutableLiveData
    val validateOTPLiveData: MutableLiveData<Resource<ModdakirResponse<ResponseModel>>> = validateOTPMutableLiveData
    val validatePhoneNumberLiveData: MutableLiveData<Resource<ModdakirResponse<BaseResponse>>> = validatePhoneNumberMutableLiveData
    val resetPasswordLiveData: MutableLiveData<Resource<ModdakirResponse<ResponseModel>>> = resetPasswordMutableLiveData


    fun login(email: String?, userName: String?, password: String, lang: String) {
        var serviceResponse: Resource<ModdakirResponse<ResponseModel>>?
        loginMutableLiveData.postValue(Resource.Loading())
        launch {
            try {
                serviceResponse = dataSource.requestLogin(email, userName, password, lang)
                if (serviceResponse!!.data != null) {

                }
                loginMutableLiveData.postValue(serviceResponse!!)
            } catch (e: Exception) {
                loginMutableLiveData.postValue(Resource.NetworkError(NETWORK_ERROR))
            }
        }
    }

    fun loginTeacher(email: String?, userName: String?, password: String, lang: String) {
        var serviceResponse: Resource<ModdakirResponse<ResponseModel>>?
        loginTeacherMutableLiveData.postValue(Resource.Loading())
        launch {
            try {
                serviceResponse = dataSource.requestLoginTeacher(email, userName, password, lang)
                if (serviceResponse!!.data != null) {

                }
                loginTeacherMutableLiveData.postValue(serviceResponse!!)
            } catch (e: Exception) {
                loginTeacherMutableLiveData.postValue(Resource.NetworkError(NETWORK_ERROR))
            }
        }
    }

    fun forgetPassword(email: String?, phone: String?, lang: String,token:String) {
        var serviceResponse: Resource<ModdakirResponse<ResponseModel>>?
        forgetPasswordMutableLiveData.postValue(Resource.Loading())
        launch {
            try {
                serviceResponse = dataSource.requestForgetPassword(email, phone, lang,token)
                if (serviceResponse!!.data != null) {

                }
                forgetPasswordMutableLiveData.postValue(serviceResponse!!)
            } catch (e: Exception) {
                forgetPasswordMutableLiveData.postValue(Resource.NetworkError(NETWORK_ERROR))
            }
        }
    }

    fun sendPhoneVerifyNum(phone: String,token:String) {
        var serviceResponse: Resource<ModdakirResponse<OTPResponseModel>>?
        sendPhoneVerifyNumMutableLiveData.postValue(Resource.Loading())
        launch {
            try {
                serviceResponse = dataSource.sendPhoneVerifyNum(phone,token)
                if (serviceResponse!!.data != null) {

                }
                sendPhoneVerifyNumMutableLiveData.postValue(serviceResponse!!)
            } catch (e: Exception) {
                sendPhoneVerifyNumMutableLiveData.postValue(Resource.NetworkError(NETWORK_ERROR))
            }
        }
    }
    fun loginWithMobile(phone: String,token:String,isFromSocialRegister:Boolean) {
        var serviceResponse: Resource<ModdakirResponse<OTPResponseModel>>?
        sendPhoneVerifyNumMutableLiveData.postValue(Resource.Loading())
        launch {
            try {
                serviceResponse = dataSource.loginWithMobile(phone,token,isFromSocialRegister)
                if (serviceResponse!!.data != null) {

                }
                sendPhoneVerifyNumMutableLiveData.postValue(serviceResponse!!)
            } catch (e: Exception) {
                sendPhoneVerifyNumMutableLiveData.postValue(Resource.NetworkError(NETWORK_ERROR))
            }
        }
    }
    fun validateOTP(code: String,providerType: String?,providerUserId: String?,deviceUUID: String,isSocialMediaAccountPending:Boolean?) {
        var serviceResponse: Resource<ModdakirResponse<ResponseModel>>?
        validateOTPMutableLiveData.postValue(Resource.Loading())
        launch {
            try {
                serviceResponse = dataSource.validateOTP(code, providerType,providerUserId,deviceUUID,isSocialMediaAccountPending)
                if (serviceResponse!!.data != null) {

                }
                validateOTPMutableLiveData.postValue(serviceResponse!!)
            } catch (e: Exception) {
                validateOTPMutableLiveData.postValue(Resource.NetworkError(NETWORK_ERROR))
            }
        }
    }

    fun validatePhoneNumber(code: String,providerType: String?,providerUserId: String?,deviceUUID: String,isSocialMediaAccountPending:Boolean?) {
        var serviceResponse: Resource<ModdakirResponse<BaseResponse>>?
        validatePhoneNumberMutableLiveData.postValue(Resource.Loading())
        launch {
            try {
                serviceResponse = dataSource.validatePhoneNumber(code, providerType,providerUserId,deviceUUID,isSocialMediaAccountPending)
                if (serviceResponse!!.data != null) {

                }
                validatePhoneNumberMutableLiveData.postValue(serviceResponse!!)
            } catch (e: Exception) {
                validatePhoneNumberMutableLiveData.postValue(Resource.NetworkError(NETWORK_ERROR))
            }
        }
    }
    fun resetPassword(password: String) {
        var serviceResponse: Resource<ModdakirResponse<ResponseModel>>?
        resetPasswordMutableLiveData.postValue(Resource.Loading())
        launch {
            try {
                serviceResponse = dataSource.resetPassword(password)
                if (serviceResponse!!.data != null) {

                }
                resetPasswordMutableLiveData.postValue(serviceResponse!!)
            } catch (e: Exception) {
                resetPasswordMutableLiveData.postValue(Resource.NetworkError(NETWORK_ERROR))
            }
        }
    }







}