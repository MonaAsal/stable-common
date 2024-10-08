package com.moddakir.moddakir.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ConfigModel(

    val sinchAppKey: String,
    val sinchSecretKey: String
): Parcelable