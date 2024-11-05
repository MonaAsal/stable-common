package com.moddakir.moddakir

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.moddakirapps.databinding.ActivityLoginStudentBinding
import com.example.moddakirapps.databinding.ActivityRegisterMobileBinding
import com.moddakir.moddakir.helper.LocaleHelper
import com.moddakir.moddakir.utils.Utils
import dagger.hilt.android.HiltAndroidApp



public class App : Application(), DefaultLifecycleObserver{

    override fun onCreate() {
        super<Application>.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        context = applicationContext
        utils=Utils()
        AppName=""
        helper=LocaleHelper()
       // timeZoneOffset = data.getTimeZoneOffset() / 60
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

    }
    companion object {
        lateinit var context: Context
        lateinit var AppName:String
        lateinit var ApplicationVersion:String
        lateinit var WhatsAppNum:String
        lateinit var ColorPrimary:String
        lateinit var SecondColor:String
        lateinit var utils: Utils
        lateinit var helper:LocaleHelper
        lateinit var bindingStudent: ActivityLoginStudentBinding
        lateinit var binding: ActivityRegisterMobileBinding
        var timeZoneOffset: Int = 0

    }

    override fun attachBaseContext(context1: Context) {
        context = context1
        super.attachBaseContext(LocaleHelper.onAttach(context1))
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        Log.e("AppClass", "onResume")
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Log.e("AppClass", "onTrimMemory")
    }

    override fun onDestroy(owner: LifecycleOwner) {// not guaranteed to be called
        super.onDestroy(owner)
        Log.e("AppClass", "onDestroy")
    }

    override fun onLowMemory() {
        super.onLowMemory()

        Log.e("AppClass", "onLowMemory")
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        Log.e("AppClass", "onPause")
    }
    enum class AppVersion {
        Version1, //Student App
        Version2, //Teacher App
        Version3  //White Label App
    }

}

