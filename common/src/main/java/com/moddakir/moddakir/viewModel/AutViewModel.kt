package com.moddakir.moddakir.viewModel

import com.moddakir.moddakir.model.base.BaseViewModel
import com.moddakir.moddakir.model.base.error.ErrorManager
import com.moddakir.moddakir.model.base.error.ErrorMapper
import com.moddakir.moddakir.useCase.AuthUseCase
import com.moddakir.moddakir.utils.ValidationUtils
import dagger.assisted.AssistedFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AutViewModel @Inject constructor(private val authUseCase: AuthUseCase) : BaseViewModel() {

    override val errorManager: ErrorManager get() = ErrorManager(ErrorMapper())
    private var validationUtils: ValidationUtils = ValidationUtils()
    var loginLiveData = authUseCase.loginLiveData
    var loginTeacherLiveData = authUseCase.loginTeacherLiveData
    var sendForgetPasswordLiveData = authUseCase.sendForgetPasswordLiveData

    fun login(email: String, password: String, lang: String) {
        if (validationUtils.isEmailValid(email)) {
            authUseCase.login(email, null, password, lang)
        } else {
            authUseCase.login(null, email, password, lang)
        }
    }

    fun loginTeacher(email: String, password: String, lang: String) {
        if (validationUtils.isEmailValid(email)) {
            authUseCase.loginTeacher(email, null, password, lang)
        } else {
            authUseCase.loginTeacher(null, email, password, lang)
        }
    }
    fun sendForgetPassword(email: String?, phone: String?, lang: String,token: String) {
        if(phone==null) {
            authUseCase.forgetPassword(email, null, lang,token)
        }
        else{
            authUseCase.forgetPassword(null, phone, lang,token)
        }
    }
    fun sendPhoneVerifyNum(phone: String,token: String) {
            authUseCase.sendPhoneVerifyNum(phone, token)
    }
    fun loginWithMobile(phone: String,token: String,isFromSocialRegister:Boolean) {
        authUseCase.loginWithMobile(phone, token,isFromSocialRegister)
    }

    fun validateOTP(code: String,providerType: String?,providerUserId: String?,deviceUUID: String,isSocialMediaAccountPending:Boolean?) {
        authUseCase.validateOTP(code, providerType,providerUserId,deviceUUID,isSocialMediaAccountPending)
    }
    fun validatePhoneNumber(code: String,providerType: String?,providerUserId: String?,deviceUUID: String,isSocialMediaAccountPending:Boolean?) {
        authUseCase.validatePhoneNumber(code, providerType,providerUserId,deviceUUID,isSocialMediaAccountPending)
    }
    fun resetPassword(password: String) {
        authUseCase.resetPassword(password)
    }
}