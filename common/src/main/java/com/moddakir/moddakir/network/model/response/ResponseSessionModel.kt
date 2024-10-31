package com.moddakir.moddakir.network.model.response

import android.os.Parcelable
import com.moddakir.moddakir.network.model.Session
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseSessionModel(
    var message: String,
    var action: String,
    var statusCode: Int = 0,
    var items: ArrayList<Session>?,
    val isBlocked: Boolean = false,
    var count: Int = 0
) : Parcelable