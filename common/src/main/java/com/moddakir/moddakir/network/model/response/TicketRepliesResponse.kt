package com.moddakir.moddakir.network.model.response

import com.moddakir.moddakir.network.model.Reply


open class TicketRepliesResponse {

    private var statusCode: Int = 0
    private var items: List<Reply>? = null
}