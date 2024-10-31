package com.moddakir.moddakir.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SessionNames(
    var fromSouraName: String,
    val toSouraName: CallLog,
    var sessionTypeName: String? = ""
) : Parcelable