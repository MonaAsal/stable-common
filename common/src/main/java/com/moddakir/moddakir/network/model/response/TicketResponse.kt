package com.moddakir.moddakir.network.model.response

import android.os.Parcelable
import com.moddakir.moddakir.network.model.Item
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TicketResponse(
    private var statusCode: Int? = null,
    val item: Item

) : Parcelable