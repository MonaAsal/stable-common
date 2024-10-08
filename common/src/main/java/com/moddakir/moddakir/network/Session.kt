package com.moddakir.moddakir.network

import android.util.Log
import com.moddakir.moddakir.App.Companion.context
import com.moddakir.moddakir.helper.SharedPrefHelper
import com.moddakir.moddakir.network.model.User
import com.google.gson.Gson
import java.util.*


object Session {

    var xAccessToken: String
    var xLang: String
    private val AccessTokenKEY = "AccessToken"
    private val KEY_LOCALE = "KEY_LOCALE"
    private val KEY_USER = "user"
    private val KEY_PREFS2 = "Moddakir.prefs"

    init {
        xAccessToken =
            getAccessToken()
        xLang = getUserLocale()
    }

    fun setAccessToken(token: String?) {
        token?.let {
            SharedPrefHelper.setIntoSharedPref(
                context,
                AccessTokenKEY,
                it
            )
        }

        xAccessToken = token!!
    }

    fun getUserLocale(): String {
        return SharedPrefHelper.getFromSharedPref(
            context,
            KEY_LOCALE, Locale.getDefault().language, KEY_PREFS2
        ).toString()
    }

    fun setUserLocale(lang: String) {
        lang?.let {
            SharedPrefHelper.setIntoSharedPref(
                context,
                KEY_LOCALE,
                it, KEY_PREFS2
            )
        }

        xLang = lang!!
    }


    fun getAccessToken(): String {
        val accessToken = SharedPrefHelper.getFromSharedPref(
            context,
            AccessTokenKEY, ""
        )
            .toString()
        return accessToken
    }

    fun updateTokens(token: String) {
        var user = getUserInfo()
        user?.accessToken = token
        saveUserInfo(user!!)
    }

    fun getUserInfo(): com.moddakir.moddakir.network.model.User? {
        val userItemJson = SharedPrefHelper.getFromSharedPref(context, KEY_USER, "")

        if (userItemJson!!.isNotEmpty()) {
            return Gson().fromJson(userItemJson, com.moddakir.moddakir.network.model.User::class.java)
        }

        return null
    }

    fun saveUserInfo(user: com.moddakir.moddakir.network.model.User) {
        val gson = Gson()
        SharedPrefHelper.setIntoSharedPref(context, KEY_USER, gson.toJson(user))
        Log.i("user", gson.toJson(user))

        setAccessToken(user?.accessToken)
    }


    fun clearSession() {
        setAccessToken("")
    }

    fun getSocialMediaType(): String? {
        val socialMediaType = SharedPrefHelper.getFromSharedPref(context, "SocialMediaType", "")
        if (socialMediaType!!.isNotEmpty()) {
            return socialMediaType
        }

        return null
    }

    fun saveSocialMediaType(socialMediaType: String) {
        SharedPrefHelper.setIntoSharedPref(context, "SocialMediaType",socialMediaType)
    }
}