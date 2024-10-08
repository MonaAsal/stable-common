package com.moddakir.moddakir.network.model.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class StudentInfoResponse(
    val address: String,
    val city: String,
    val countryCode: String,
    val email: String,
    val phone: String,
    val fullName: String,
) : Parcelable