package com.moddakir.moddakir.network.model.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OTPResponseModel(
    val message: String,
    val action: String,
    val accessToken: String,
    val isSkippedVerification: Boolean,
    val statusCode: Int =0
) : Parcelable