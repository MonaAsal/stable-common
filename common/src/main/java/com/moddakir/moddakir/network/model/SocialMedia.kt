package com.moddakir.moddakir.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SocialMedia(
    val url: String,
    val image: String
): Parcelable