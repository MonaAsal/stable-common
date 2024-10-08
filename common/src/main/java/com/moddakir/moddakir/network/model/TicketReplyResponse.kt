package com.moddakir.moddakir.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TicketReplyResponse(
    var statusCode: Int,
    val reply: Reply
) : Parcelable