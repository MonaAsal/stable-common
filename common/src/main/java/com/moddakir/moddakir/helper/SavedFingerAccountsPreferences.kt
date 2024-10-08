package com.moddakir.moddakir.helper

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.moddakir.moddakir.App.Companion.context
import com.moddakir.moddakir.network.model.SavedFingerAccount
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.io.IOException
import java.security.GeneralSecurityException

 class SavedFingerAccountsPreferences {
    private val ACCOUNTS_KEY: String = "savedAccounts"

    fun getSavedAccounts(): ArrayList<com.moddakir.moddakir.network.model.SavedFingerAccount?>? {
        try {
            var users: ArrayList<com.moddakir.moddakir.network.model.SavedFingerAccount?> = ArrayList<com.moddakir.moddakir.network.model.SavedFingerAccount?>()
            val serializedObject: String? =  createSecurityPreference().getString(ACCOUNTS_KEY, null);

            if (serializedObject != null && !serializedObject.isEmpty()) {
                val gson = Gson()
                val type = object : TypeToken<List<com.moddakir.moddakir.network.model.SavedFingerAccount?>?>() {
                }.type
                users = gson.fromJson<ArrayList<com.moddakir.moddakir.network.model.SavedFingerAccount?>>(serializedObject, type)
            }
            return users
        } catch (e: Exception) {
            e.printStackTrace()
            Timber.v("Saved account " + e.message)
        }
        return null
    }

    @SuppressLint("CheckResult")
    fun saveAccount(newAccount: com.moddakir.moddakir.network.model.SavedFingerAccount) {
        Single.fromCallable {
            val pref: SharedPreferences =createSecurityPreference()
            var users: ArrayList<com.moddakir.moddakir.network.model.SavedFingerAccount?>? = getSavedAccounts()
            if (users == null) users = ArrayList<com.moddakir.moddakir.network.model.SavedFingerAccount?>()
            val position: Int = getEmailPositionIfExist(newAccount.userName)
            if (position != -1) users!!.add(position, newAccount)
            else users!!.add(newAccount) //first one is that already used account
             saveUsersList(users, pref)
            true
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { resultObject -> }
    }

    private fun saveUsersList(
        accounts: ArrayList<com.moddakir.moddakir.network.model.SavedFingerAccount?>?,
        preferences: SharedPreferences
    ) {
        val usersJson = Gson().toJson(accounts)
        val editor = preferences.edit()
        editor.putString(ACCOUNTS_KEY, usersJson)
        editor.apply()
    }

    @Synchronized
    fun getEmailPositionIfExist(email: String?): Int {
        val users: ArrayList<com.moddakir.moddakir.network.model.SavedFingerAccount?> = getSavedAccounts() ?: return -1
        for (i in users.indices) { //remove any duplicates accounts
            if (users[i]?.userName != null && users[i]?.userName.equals(email)) return i
        }
        return -1
    }

    @Synchronized
    fun getEmailAndPasswordPositionIfExist(email: String?, password: String?): Int {
        val users: ArrayList<com.moddakir.moddakir.network.model.SavedFingerAccount?> = getSavedAccounts() ?: return -1
        for (i in users.indices) { //remove any duplicates accounts
            if (users[i]?.userName == email && users[i]?.password == password
            ) return i
        }
        return -1
    }


    @Synchronized
    @Throws(GeneralSecurityException::class, IOException::class)
    fun createSecurityPreference(): SharedPreferences {
        val masterKey = MasterKey.Builder(
            context,
            MasterKey.DEFAULT_MASTER_KEY_ALIAS
        )
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            "secret_shared_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

}