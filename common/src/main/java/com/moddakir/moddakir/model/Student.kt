package com.moddakir.moddakir.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Student(
    val address: String,
    val isSelect: Boolean,
    val assignmentValue: Int,
    val avatarUrl: String,
    val certificate: String,
    val city: String,
    val country: String,
    val email: String,
    val enableVideoRecording: Boolean,
    val enableVoiceRecording: Boolean,
    val fullName: String,
    val gender: String,
    val id: String,
    val phone: String,
    val profession: String,
    val rate: Int,
    val type: String,
    val username: String,
): Parcelable
