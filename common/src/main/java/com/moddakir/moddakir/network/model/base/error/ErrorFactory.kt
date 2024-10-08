package com.moddakir.moddakir.network.model.base.error

interface ErrorFactory {
    fun getError(errorCode: Int): Error
}