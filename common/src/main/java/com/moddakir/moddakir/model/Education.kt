package com.moddakir.moddakir.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Education(
    val paths: List<String>,
    val level: String,
    val isEducationDialogDisplayed: Boolean
): Parcelable