package com.moddakir.moddakir.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AboutModel(

    private var message: String,
    val action: String,
    val statusCode: Int = 0,
    val html: String?
) : Parcelable