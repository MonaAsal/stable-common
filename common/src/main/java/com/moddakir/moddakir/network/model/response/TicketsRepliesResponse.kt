package com.moddakir.moddakir.network.model.response

import android.os.Parcelable
import com.moddakir.moddakir.network.model.Item
import com.moddakir.moddakir.network.model.Reply
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TicketsRepliesResponse(
    private var statusCode: Int = 0 ,
    var items: List<Reply>

) : Parcelable