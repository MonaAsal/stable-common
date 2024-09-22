package com.moddakir.moddakir.helper

import android.content.Context
import androidx.security.crypto.MasterKeys

class SharedPrefHelper {
    companion object {
        private val KEY_PREFS = "Moddakir.prefs"


        fun setIntoSharedPref(context: Context?, key: String, value: String, prefName: String) {

            val sharedPref =
                context?.getSharedPreferences(
                    prefName,
                    Context.MODE_PRIVATE
                )
                    ?: return
            with(sharedPref.edit()) {
                putString(key, value)
                apply()
            }
        }

        fun getFromSharedPref(
            context: Context?,
            key: String,
            default: String,
            prefName: String
        ): String? {

            val sharedPref =
                context?.getSharedPreferences(
                    prefName,
                    Context.MODE_PRIVATE
                )
            return sharedPref?.getString(key, default)
        }


        fun setIntoSharedPref(context: Context?, key: String, value: String) {
            val sharedPref =
                context?.getSharedPreferences(
                    KEY_PREFS,
                    Context.MODE_PRIVATE
                )
                    ?: return
            with(sharedPref.edit()) {
                putString(key, value)
                apply()
            }
        }

        fun getFromSharedPref(context: Context?, key: String): String? {

            val sharedPref =
                context?.getSharedPreferences(
                    KEY_PREFS,
                    Context.MODE_PRIVATE
                )
            return sharedPref?.getString(key, "")
        }

        fun getFromSharedPref(context: Context?, key: String, default: String): String? {

            val sharedPref =
                context?.getSharedPreferences(
                    KEY_PREFS,
                    Context.MODE_PRIVATE
                )
            return sharedPref?.getString(key, default)
        }

        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC


        fun clearCache(context: Context) {
            val sharedPref =
                context?.getSharedPreferences(
                    KEY_PREFS,
                    Context.MODE_PRIVATE
                )
            sharedPref?.edit()?.clear()?.commit()
        }
    }


}