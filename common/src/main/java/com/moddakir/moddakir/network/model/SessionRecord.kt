package com.moddakir.moddakir.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SessionRecord(
    private var id: String?,
    val type: String,
    val fromSurah: String,
    val fromVerse: String,
    val toSurah: String,
    val toVerse: String,
    val rate: String,
    var comment: String,
    val startDateTime: String,
    val commentIDs: ArrayList<String>,
    val comments: ArrayList<String>,
    val wordErrorCount: Int = 0,
    val assignmentValue: Float = 0f,
    var isDone: Boolean = false,
    var isLesson: Boolean = false,
    val hesitationErrorCount: String,
    val tajweedErrorCount: Int = 0,
    val isExpanded: Boolean = true,
    private val sessionNames: SessionNames

) : Parcelable