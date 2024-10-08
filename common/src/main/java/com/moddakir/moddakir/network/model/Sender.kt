package com.moddakir.moddakir.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sender(
    var id: String,
    val fullName: String,
    var avatarUrl: String
) : Parcelable