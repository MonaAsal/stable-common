package com.moddakir.moddakir.model.response

import com.moddakir.moddakir.model.ErrorModel

data class ErrorResponse (
    var errors: List<ErrorModel>? = null
):BaseResponse()