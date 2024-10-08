package com.moddakir.moddakir.useCase

import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.AboutModel
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.model.response.ResponseModel
import com.moddakir.moddakir.network.model.response.TicketsResponse


interface StaticPagesUseCase {
    suspend fun getAboutUs(
    ): Resource<ModdakirResponse<AboutModel>>

    suspend fun contactUsForm(message:String,title:String): Resource<ModdakirResponse<ResponseModel>>

    suspend fun getListContactUs(page:Int): Resource<ModdakirResponse<TicketsResponse>>



}
