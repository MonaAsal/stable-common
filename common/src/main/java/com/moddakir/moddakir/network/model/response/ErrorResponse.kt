package com.moddakir.moddakir.network.model.response

import com.moddakir.moddakir.network.model.ErrorModel

data class ErrorResponse (
    var errors: List<com.moddakir.moddakir.network.model.ErrorModel>? = null
):BaseResponse()