package com.moddakir.moddakir.network.model.response

import android.os.Parcelable
import com.moddakir.moddakir.network.model.Student
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DependentMangersModel(
    val action: String,
    val message: String,
    val statusCode: Int,
    val students: List<Student> = listOf()

) : Parcelable