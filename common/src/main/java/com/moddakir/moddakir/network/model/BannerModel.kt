package com.moddakir.moddakir.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BannerModel(
    var id: String,
    val url: String,
    var image: String,
    val type: String,
) : Parcelable
