package com.moddakir.moddakir.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CheckBoxModel(
    var id: String,
    val value: String,
    val level: String,
    var isSelected: Boolean? = null
) : Parcelable