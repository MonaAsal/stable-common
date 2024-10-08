package com.moddakir.moddakir.network.model.response

import android.os.Parcelable
import com.moddakir.moddakir.network.model.User
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SocialResponse(
    var statusCode: Int = 0,
    var isSocialMediaAccountPending: Boolean? = null,
    var providerUserId: String? = null,
    var providerType: String? = null,
    val accessToken: String? = null,
    var message: String? = null,
    val isDependentManager: Boolean = false,
    val isEmailValidate: Boolean = false,
    val isPhoneValidate: Boolean = false,
    val student: com.moddakir.moddakir.network.model.User? = null

) : Parcelable