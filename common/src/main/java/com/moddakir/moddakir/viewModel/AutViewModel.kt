package com.moddakir.moddakir.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moddakirapps.R
import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.User
import com.moddakir.moddakir.network.model.base.BaseViewModel
import com.moddakir.moddakir.network.model.base.error.ErrorManager
import com.moddakir.moddakir.network.model.base.error.ErrorMapper
import com.moddakir.moddakir.network.model.response.BaseResponse
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.model.response.OTPResponseModel
import com.moddakir.moddakir.network.model.response.ResponseModel
import com.moddakir.moddakir.network.model.response.SocialResponse
import com.moddakir.moddakir.useCase.AuthenticationUseCase
import com.moddakir.moddakir.utils.AccountPreference
import com.moddakir.moddakir.utils.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AutViewModel @Inject constructor(
    private val authUseCase: AuthenticationUseCase) : BaseViewModel() {

    override val errorManager: ErrorManager get() = ErrorManager(ErrorMapper())
    private var validationUtils: ValidationUtils = ValidationUtils()
    private val loginTeacherMutableLiveData = MutableLiveData<Resource<ModdakirResponse<ResponseModel>>>()
    private val forgetPasswordMutableLiveData = MutableLiveData<Resource<ModdakirResponse<ResponseModel>>>()
    private val sendPhoneVerifyNumMutableLiveData = MutableLiveData<Resource<ModdakirResponse<OTPResponseModel>>>()
    private val validateOTPMutableLiveData = MutableLiveData<Resource<ModdakirResponse<ResponseModel>>>()
    private val validatePhoneNumberMutableLiveData = MutableLiveData<Resource<ModdakirResponse<BaseResponse>>>()
    private val resetPasswordMutableLiveData = MutableLiveData<Resource<ModdakirResponse<ResponseModel>>>()
    private val socialMutableLiveData = MutableLiveData<Resource<ModdakirResponse<SocialResponse>>>()
    private val submitJoinUsMutableLiveData = MutableLiveData<Resource<ModdakirResponse<ResponseModel>>>()


    private val loginMutableLiveData = MutableLiveData<Resource<ModdakirResponse<ResponseModel>>>()
    val loginLiveData: MutableLiveData<Resource<ModdakirResponse<ResponseModel>>> = loginMutableLiveData
    val loginTeacherLiveData: MutableLiveData<Resource<ModdakirResponse<ResponseModel>>> = loginTeacherMutableLiveData
    val sendForgetPasswordLiveData: MutableLiveData<Resource<ModdakirResponse<ResponseModel>>> = forgetPasswordMutableLiveData
    val sendPhoneVerifyNumLiveData: MutableLiveData<Resource<ModdakirResponse<OTPResponseModel>>> = sendPhoneVerifyNumMutableLiveData
    val validateOTPLiveData: MutableLiveData<Resource<ModdakirResponse<ResponseModel>>> = validateOTPMutableLiveData
    val validatePhoneNumberLiveData: MutableLiveData<Resource<ModdakirResponse<BaseResponse>>> = validatePhoneNumberMutableLiveData
    val resetPasswordLiveData: MutableLiveData<Resource<ModdakirResponse<ResponseModel>>> = resetPasswordMutableLiveData
    val socialLiveData: MutableLiveData<Resource<ModdakirResponse<SocialResponse>>> = socialMutableLiveData
    val submitJoinUsLiveData: MutableLiveData<Resource<ModdakirResponse<ResponseModel>>> = submitJoinUsMutableLiveData

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

    fun signInWithSocial( email:String,  name:String,  id:String,  gender:String,
                          avatarUrl:String,  provider:String,
                          token:String,  lang:String,  context:Context) {
        viewModelScope.launch {
            authUseCase.signInWithSocial(email,name,id,gender,avatarUrl,provider,token,lang,context)
        }

    }
    fun getDependentManagers(studentId: String, forSwitchingProgramsPage: Boolean, page: Int,pageSize: Int) {
        viewModelScope.launch {
                authUseCase.getDependentManagers(studentId, forSwitchingProgramsPage, page, pageSize)
        }
    }

    fun submitJoinUs(
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
    ) {
        viewModelScope.launch {
            authUseCase.submitJoinUs(firstName,managerId,programType,username,email,phone,nationality,educationLevel,deviceUUID,gender,education,password)
        }

    }


    fun requestLogout() {
        viewModelScope.launch {
            authUseCase.logout()
        }
    }

    fun getGenderType(typeCheckedId: Int): String? {
        return when (typeCheckedId) {
            R.id.male_rb -> { "male" }
            R.id.female_rb -> { "female" }
            R.id.boy_rb -> { "boy" }
            R.id.girl_rb -> { "girl" }
            else -> null
        }
    }
    fun handleLoggedUser(data: ResponseModel) {
        val user: User = data.student
        user.accessToken=(data.accessToken)
        user.isDependentManager=(data.isDependentManager)
        user.isMailActivated=(data.isEmailValidate)
        user.isMobileActivated=(data.isPhoneValidate)
        AccountPreference.registerData(user)
    }
}