package com.moddakir.moddakir.network.remote

import android.content.Intent
import com.moddakir.moddakir.App
import com.moddakir.moddakir.network.Session
import com.moddakir.moddakir.ui.bases.MainActivity
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {
    private val CONTENT_TYPE = "Content-Type"
    private val Authorization = "Authorization"
    private val Language = "Accept-Language"
    private val CONTENT_TYPE_VALUE = "application/json"

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authorisedRequestBuilder = originalRequest.newBuilder()
            .header(CONTENT_TYPE, CONTENT_TYPE_VALUE)
            .addHeader(Language, Session.xLang)
            .addHeader(
                Authorization,
                Session.xAccessToken
            )
           // .method(originalRequest.method, originalRequest.body)

        if (Session.xAccessToken.isEmpty()
        ) {
            var intent = Intent(App.context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            App.context.startActivity(intent)
        }

        val response = chain.proceed(authorisedRequestBuilder.build())
        return response
    }
}