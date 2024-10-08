package com.moddakir.moddakir.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TicketReply(
    private var page: Int? = null,

    val itemsPerPage: Int? = null,

    val ticketId: String? = null,

    ) : Parcelable