package com.moddakir.moddakir.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SurveyModel(

    val type: String,
    val name: String,
    val submitUrl: String,
    val deleted: Boolean

): Parcelable
