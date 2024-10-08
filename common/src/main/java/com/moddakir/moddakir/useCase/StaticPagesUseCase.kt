package com.moddakir.moddakir.useCase

import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.AboutModel
import com.moddakir.moddakir.network.model.TicketReplyResponse
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.model.response.ResponseModel
import com.moddakir.moddakir.network.model.response.TicketRepliesResponse
import com.moddakir.moddakir.network.model.response.TicketResponse
import com.moddakir.moddakir.network.model.response.TicketsResponse


interface StaticPagesUseCase {
    suspend fun getAboutUs(
    ): Resource<ModdakirResponse<AboutModel>>

    suspend fun contactUsForm(message:String,title:String): Resource<ModdakirResponse<ResponseModel>>

    suspend fun getListContactUs(page:Int): Resource<ModdakirResponse<TicketsResponse>>


    suspend fun sendReplay(message:String,ticketId:String): Resource<ModdakirResponse<TicketReplyResponse>>

    suspend fun getTicketReplies(page:Int,messageId:String): Resource<ModdakirResponse<TicketRepliesResponse>>

    suspend fun getTicketById(ticketId:String): Resource<ModdakirResponse<TicketResponse>>


}
