package com.moddakir.moddakir.ui.bases.holyQuran

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import com.example.moddakirapps.BuildConfig
import com.google.gson.Gson
import com.moddakir.moddakir.App
import com.moddakir.moddakir.helper.LocaleHelper
import com.moddakir.moddakir.utils.AccountPreference
import com.moddakir.moddakir.utils.AccountPreference.Companion.getUser
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel
import java.util.*

@SuppressLint("StaticFieldLeak")
object QuranInstance {
    private var flutterEngine: FlutterEngine? = null

    private const val CHANNEL = "com.moddakir.app/quran"
    private var context: Context? = null

    fun init(contextQuran: Context) {
        context = contextQuran
        flutterEngine = FlutterEngine(contextQuran)
        // Start executing Dart code to pre-warm the FlutterEngine.
        flutterEngine!!.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )
        // Cache the FlutterEngine to be used by FlutterActivity.

        FlutterEngineCache
            .getInstance()
            .put("my_engine_id", flutterEngine)

        startMethodsCallHandler()

    }

    private val methodChannel: MethodChannel? by lazy {
        MethodChannel(flutterEngine!!.dartExecutor.binaryMessenger, CHANNEL)
    }

    fun updateLanguage() {
        methodChannel?.invokeMethod(
            OutgoingFlutterMethodNames.updateAppLanguage.name,
            LocaleHelper.getLocale(context!!).toString()
        )
    }


    private fun startMethodsCallHandler() {
        methodChannel?.setMethodCallHandler { call, result ->

            when (call.method) {
                IncomingFlutterMethodNames.getBaseUrl.name -> {
                    result.success(BuildConfig.BASE_URL)
                }

                IncomingFlutterMethodNames.getUserData.name -> {
                    result.success(getUserData())
                }

                else -> {
                    result.notImplemented()
                }

            }
        }
    }

    private fun getUserData(): String {
        val data = HashMap<String, Any>()
        val user = getUser()
        data["apiToken"] = AccountPreference.getAccessToken().toString()
        data["id"] = user!!.id
        data["name"] = user.username
        data["isStudent"] = true
        val gson = Gson()
        return gson.toJson(data)
    }

    fun logout() {
        methodChannel?.invokeMethod(OutgoingFlutterMethodNames.logout.name, null)
    }

    fun loginQuran() {
        methodChannel?.invokeMethod(OutgoingFlutterMethodNames.login.name, null)
    }

    enum class IncomingFlutterMethodNames {
        getUserData, getBaseUrl

    }


    enum class OutgoingFlutterMethodNames {

        updateAppLanguage, logout, login

    }


}