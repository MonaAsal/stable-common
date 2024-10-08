package com.moddakir.moddakir.network.model.response

data class ModdakirResponse<T>(
    val data: T? = null
) : BaseResponse()
