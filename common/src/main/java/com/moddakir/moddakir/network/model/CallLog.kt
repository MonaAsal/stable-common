package com.moddakir.moddakir.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CallLog(
    var callId: String,
    val studentId: String,
    val studentName: String,
    val studentCountry: String,
    val teacherId: String,
    val teacherName: String,
    val status: String,
    val startDateTime: String,
    val endDateTime: String,
    val duration: String,
    val studentAvatarUrl: String,
    val teacherAvatarUrl: String,
    val createdAt: String,
    val recordUrl: String,
    val type: String,
    val errorCount: Int = 0,
    val shareUrl: String
) : Parcelable