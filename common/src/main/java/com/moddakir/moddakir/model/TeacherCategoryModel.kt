package com.moddakir.moddakir.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeacherCategoryModel(
    val id: String,
    val value: String
): Parcelable
