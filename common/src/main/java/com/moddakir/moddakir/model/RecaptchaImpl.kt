package com.moddakir.moddakir.model

import android.app.Activity
import android.app.Application
import android.content.Context
import com.example.moddakirapps.BuildConfig
import com.google.android.recaptcha.Recaptcha.getTasksClient
import com.google.android.recaptcha.RecaptchaAction
import com.google.android.recaptcha.RecaptchaTasksClient

class RecaptchaImpl {
    var isGetRecaptchaClient: Boolean = false
    var token: String? = null
    companion object {
        var recaptchaTasksClient: RecaptchaTasksClient? = null

    }
    fun getRecaptchaTasksClient(
        application: Application?,
        activity: Activity?,
        action: RecaptchaAction?
    ) {
        isGetRecaptchaClient = false
        if (recaptchaTasksClient == null) {
            getTasksClient(application!!, BuildConfig.CAPTCHA_KEY_ID)
                .addOnSuccessListener { recaptchaTasksClient1 ->

                   recaptchaTasksClient = recaptchaTasksClient1
                    isGetRecaptchaClient = true
                }.addOnFailureListener(

                    activity!!
                ) {
                    // Handle communication errors ...
                    // See "Handle communication errors" section
                }
        }
    }
}