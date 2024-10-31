package com.moddakir.moddakir.network.model.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EvaluateTeacherResponse(
    private var message: String,
    val action: String,
    val reviewId: String,
    val statusCode: Int = 0,
    val messageBody: String,
    val messageTitle: String,
    val shouldDisplayMessage: Boolean,
    val messageType: String
) : Parcelable