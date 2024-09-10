package com.moddakir.moddakir.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ConfigModel(

    val sinchAppKey: String,
    val sinchSecretKey: String
): Parcelable