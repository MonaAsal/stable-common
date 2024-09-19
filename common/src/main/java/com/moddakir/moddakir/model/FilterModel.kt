package com.moddakir.moddakir.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilterModel(
    val id: String,
    val name: String,
    val Namecode: String?,
    val isSelected: Boolean?
): Parcelable {
}