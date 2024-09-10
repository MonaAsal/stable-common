package com.moddakir.moddakir.helper

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import com.moddakir.moddakir.network.Session
import java.util.*

class LocaleHelper {


    companion object {
        fun getLocale(context: Context): Locale? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !context.resources.configuration.locales.isEmpty) {
                context.resources.configuration.locales[0]
            } else {
                context.resources.configuration.locale
            }
        }
        fun setLocale(context: Context, language: String): Context? {
            Session.setUserLocale(language)
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                updateResources(context, language)
            } else updateResourcesLegacy(context, language)
        }

        @TargetApi(Build.VERSION_CODES.N)
        private fun updateResources(context: Context, language: String): Context? {
            val locale = Locale(language)
            Locale.setDefault(locale)

            val res: Resources = context.resources
            val config = Configuration(res.configuration)
            if (Build.VERSION.SDK_INT >= 17) {
                config.setLocale(locale)
                return context.createConfigurationContext(config)
            } else {
                config.locale = locale
                res.updateConfiguration(config, res.displayMetrics)
            }
            return context
        }

        private fun updateResourcesLegacy(context: Context, language: String): Context? {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val resources = context.resources
            val configuration = resources.configuration
            configuration.locale = locale
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                configuration.setLayoutDirection(locale)
            }
            resources.updateConfiguration(configuration, resources.displayMetrics)
            return context
        }

        fun onAttach(context: Context): Context? {
            val lang = Session.getUserLocale()
            return setLocale(context, lang)
        }

    }

}