package com.moddakir.moddakir.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Session(
    var id: String,
    val call: CallLog,
    val records: ArrayList<SessionRecord>,
    val assignmentValue: Float = 0f,
    var dependentChildRatingTeacher: Int = 0,
    var dependentChildCommentTeacher: String? = ""
) : Parcelable