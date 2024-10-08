package com.moddakir.moddakir.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SendTicketReply(
    var ticketId: String,
    val reply: String
) : Parcelable