package com.moddakir.moddakir.model.response

import android.os.Parcelable
import com.moddakir.moddakir.model.ConfigModel
import com.moddakir.moddakir.model.User
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseModel(
    val message: String,
    val action: String,
    val forceValidatePhone: Boolean,
    val accessToken: String,
    val item: String,
    val statusCode: Int,
    val teacher: User,
    val registrationInfoCompleted: Boolean,
    val student: User,
    val suggestedUsername: String,
    val uploadURL: String,
    val fileUrl: String,
    val forceUpdate: Boolean,
    val isDependentManager: Boolean,
    val showRateAppMessage: Boolean,
    val showSurvey: Boolean,
    val config: ConfigModel,
    val unreadnotification: Int,
    val isNewUser: Boolean,
    val freeMinutes: Float,
    val userInfo: StudentInfoResponse,
    val isEmailValidate: Boolean,
    val isPhoneValidate: Boolean,
    val timeZoneOffset: Int,

): Parcelable