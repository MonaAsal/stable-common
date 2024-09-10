package com.moddakir.moddakir.model.response

data class ModdakirResponse<T>(
    val data: T? = null
) : BaseResponse()
