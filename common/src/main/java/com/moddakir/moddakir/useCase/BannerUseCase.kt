package com.moddakir.moddakir.useCase

import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.AboutModel
import com.moddakir.moddakir.network.model.TicketReplyResponse
import com.moddakir.moddakir.network.model.response.BannerResponseModel
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.model.response.ResponseModel
import com.moddakir.moddakir.network.model.response.TicketResponse
import com.moddakir.moddakir.network.model.response.TicketsResponse


interface BannerUseCase {
    suspend fun getBanners(
    ): Resource<ModdakirResponse<BannerResponseModel>>



}
