package com.moddakir.moddakir.model.base.error

import com.example.moddakirapps.R
import com.moddakir.moddakir.App
import javax.inject.Inject

class ErrorMapper @Inject constructor() : ErrorMapperInterface {

    override fun getErrorString(errorId: Int): String {
        return App.context.resources.getString(errorId)

    }

    override val errorsMap: Map<Int, String>
        get() = mapOf(
            Pair(Error.NO_INTERNET_CONNECTION, getErrorString(R.string.no_internet)),
            Pair(Error.NETWORK_ERROR, getErrorString(R.string.network_error))
        ).withDefault { getErrorString(R.string.network_error) }
}