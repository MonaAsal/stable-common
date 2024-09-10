package com.moddakir.moddakir.network.remote

import com.moddakir.moddakir.network.remote.services.NonAuthService
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val nonAuthService: NonAuthService
) :
    Authenticator {

    var count = 0
    override fun authenticate(route: Route?, response: Response): Request? {
        var token = ""
        runBlocking {

        }

        return response.request.newBuilder()
            .header(
                "Authorization",
                token
            )
            .build()
    }



}