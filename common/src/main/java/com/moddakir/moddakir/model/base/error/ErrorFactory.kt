package com.moddakir.moddakir.model.base.error

interface ErrorFactory {
    fun getError(errorCode: Int): Error
}