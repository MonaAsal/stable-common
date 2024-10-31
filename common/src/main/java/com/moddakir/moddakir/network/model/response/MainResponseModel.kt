package com.moddakir.moddakir.network.model.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MainResponseModel(
    var message: String,
    var action: String,
    var statusCode: Int = 0
) : Parcelable