package com.moddakir.moddakir.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Education(
    val paths: List<String>?,
    val level: String,
    val isEducationDialogDisplayed: Boolean?
): Parcelable