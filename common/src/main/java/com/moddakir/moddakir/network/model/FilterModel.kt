package com.moddakir.moddakir.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilterModel(
    val id: String,
    val name: String,
    val Namecode: String?,
    val isSelected: Boolean?
): Parcelable