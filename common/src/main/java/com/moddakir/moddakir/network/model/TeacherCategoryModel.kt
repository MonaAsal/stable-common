package com.moddakir.moddakir.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeacherCategoryModel(
    val id: String,
    val value: String
): Parcelable
