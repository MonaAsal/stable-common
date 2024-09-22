package com.moddakir.moddakir.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moddakir.moddakir.model.base.BaseViewModel
import com.moddakir.moddakir.model.base.error.ErrorManager
import com.moddakir.moddakir.model.base.error.ErrorMapper
import com.moddakir.moddakir.model.response.BaseResponse
import com.moddakir.moddakir.model.response.ModdakirResponse
import com.moddakir.moddakir.model.response.OTPResponseModel
import com.moddakir.moddakir.model.response.ResponseModel
import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.useCase.AuthenticationUseCase
import com.moddakir.moddakir.utils.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AutViewModel @Inject constructor(
    private val authUseCase: AuthenticationUseCase) : BaseViewModel() {

    override val errorManager: ErrorManager get() = ErrorManager(ErrorMapper())
    private var validationUtils: ValidationUtils = ValidationUtils()
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

    fun login(email: String, password: String, lang: String) {
        viewModelScope.launch {
            if (validationUtils.isEmailValid(email)) {
                authUseCase.requestLogin(email, null, password, lang)
            } else {
                authUseCase.requestLogin(null, email, password, lang)
            }
        }

    }

    fun loginTeacher(email: String, password: String, lang: String) {
        viewModelScope.launch {
            if (validationUtils.isEmailValid(email)) {
                authUseCase.requestLoginTeacher(email, null, password, lang)
            } else {
                authUseCase.requestLoginTeacher(null, email, password, lang)
            }
        }
    }
    fun sendForgetPassword(email: String?, phone: String?, lang: String,token: String) {
        viewModelScope.launch {
            if (phone == null) {
                authUseCase.requestForgetPassword(email, null, lang, token)
            } else {
                authUseCase.requestForgetPassword(null, phone, lang, token)
            }
        }
    }
    fun sendPhoneVerifyNum(phone: String,token: String) {
        viewModelScope.launch {
            authUseCase.sendPhoneVerifyNum(phone, token)
        }
    }
    fun loginWithMobile(phone: String,token: String,isFromSocialRegister:Boolean) {
        viewModelScope.launch {
            authUseCase.loginWithMobile(phone, token, isFromSocialRegister)
        }
    }

    fun validateOTP(code: String,providerType: String?,providerUserId: String?,deviceUUID: String,isSocialMediaAccountPending:Boolean?) {
        viewModelScope.launch {
            authUseCase.validateOTP(
                code,
                providerType,
                providerUserId,
                deviceUUID,
                isSocialMediaAccountPending
            )
        }
    }
    fun validatePhoneNumber(code: String,providerType: String?,providerUserId: String?,deviceUUID: String,isSocialMediaAccountPending:Boolean?) {
        viewModelScope.launch {
            authUseCase.validatePhoneNumber(code, providerType,providerUserId,deviceUUID,isSocialMediaAccountPending)
        }
    }
    fun resetPassword(password: String) {
        viewModelScope.launch {
            authUseCase.resetPassword(password)
        }

    }
}