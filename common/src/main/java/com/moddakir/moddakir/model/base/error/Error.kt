package com.moddakir.moddakir.model.base.error

class Error(val code: Int, val description: String) {
    constructor(exception: Exception) : this(code = NETWORK_ERROR, description = exception.message
        ?: "")

    companion object {
        const val NO_INTERNET_CONNECTION = -1
        const val NETWORK_ERROR = -2
    }
}