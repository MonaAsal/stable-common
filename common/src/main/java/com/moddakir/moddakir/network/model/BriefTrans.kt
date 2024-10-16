package com.moddakir.moddakir.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BriefTrans(
    var ar: String? = null,
    val en: String? = null
) : Parcelable