package com.moddakir.moddakir.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.moddakir.moddakir.App
import com.moddakir.moddakir.helper.SharedPrefHelper.Companion.getFromSharedPref
import com.moddakir.moddakir.helper.SharedPrefHelper.Companion.setIntoSharedPref
import com.moddakir.moddakir.network.model.User

class AccountPreference {
    companion object{
        const val USER_KEY: String = "user"
        const val USER_LIST_KEY: String = "user_list"
        const val ACCESS_TOKEN_KEY: String = "accessToken"
        private var Pref_Name: String = "student"
        val GOOGLE_AD_ID_KEY: String = "googleId"
        private var currentLoggedUser: User? = null

        fun getUser(): User? {
            val user = getFromSharedPref(App.context,USER_KEY!!)
            if (user == null || user.isEmpty()) {
                currentLoggedUser = null
                return null
            }
            currentLoggedUser = Gson().fromJson(user, User::class.java)
            return currentLoggedUser
        }


        private fun getSavedAccounts(): ArrayList<User?>? {
            try {
                var users = ArrayList<User?>()
                val serializedObject =  getFromSharedPref(App.context,USER_LIST_KEY!!)
                if (!serializedObject.isNullOrEmpty()) {
                    val gson = Gson()
                    val type = object : TypeToken<List<User?>?>() {
                    }.type
                    users = gson.fromJson(serializedObject, type)
                }
                return users
            } catch (e: Exception) {
                e.printStackTrace()
                //            completeLogout();
            }
            return null
        }

        fun registerData(newUser: User?) {
            try {
                if (newUser == null) return
                var oldAccessToken: String? = null
                val users = getSavedAccounts()
                val validUsers = ArrayList<User?>()
                if (!users.isNullOrEmpty()) {
                    for (i in users.indices) { //remove any duplicates accounts
                        users[i]!!.currentlyLogged=(false)
                        if (newUser.id != null && users[i] != null && users[i]!!.id != null && users[i]!!.id == newUser.id) {
                            oldAccessToken = users[i]!!.accessToken
                        } else validUsers.add(users[i])
                    }
                } else if (newUser.accessToken == null || newUser.accessToken
                        .isEmpty()
                ) newUser.accessToken=(oldAccessToken!!)
                newUser.currentlyLogged=(true)
                validUsers.add(0, newUser) //first one is that already used account
                saveUsersList(validUsers)
                saveCurrentLoggedUser(newUser)
                setAccessToken(newUser.accessToken)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }

        private fun saveCurrentLoggedUser(user: User) {
            val userStr = Gson().toJson(user)
            setIntoSharedPref(App.context,USER_KEY, userStr)
            currentLoggedUser = user
        }

        private fun saveUsersList(users: ArrayList<User?>?) {
            val usersJson = Gson().toJson(users)
            setIntoSharedPref(App.context,USER_LIST_KEY, usersJson)

        }

        fun getAccessToken(): String? {
            return  getFromSharedPref(App.context,ACCESS_TOKEN_KEY!!)
        }

        private fun setAccessToken(accessToken: String?) {
            setIntoSharedPref(App.context,ACCESS_TOKEN_KEY, accessToken!!)
        }


        fun completeLogout() { // when user select to logout and clear all data

            val pref =
                App.context.getSharedPreferences(
                    Pref_Name, Context.MODE_PRIVATE
                )
                    ?: return
            val editor = pref.edit()
            editor.remove(USER_KEY)
            editor.remove(USER_LIST_KEY)
            editor.remove(ACCESS_TOKEN_KEY)
            editor.apply()
        }

        private fun removeCurrentLoggedUser() {
            val pref =
                App.context.getSharedPreferences(
                    Pref_Name, Context.MODE_PRIVATE
                )
                    ?: return
            val editor = pref.edit()
            editor.remove(USER_KEY)
            editor.remove(ACCESS_TOKEN_KEY)
            editor.apply()
        }

        fun removeCurrentAccount() {
            val users = getSavedAccounts()
            if (users != null && users.size > 0) {
                var i = 0
                while (i < users.size) {
                    if (users[i] != null && users[i]!!.currentlyLogged) {
                        users.removeAt(i)
                        i--
                    }

                    i++
                }
                removeCurrentLoggedUser()
                saveUsersList(users)
            }
        }


        fun deleteAccount(user: User) {
            val users = getSavedAccounts()

            if (users != null && users.size > 0) {

                for (i in users.indices) {
                    if (user.id == users[i]!!.id) {
                        users.removeAt(i)
                        break
                    }
                }
            }

            saveUsersList(users)
        }

        fun setStringKeyAndValue(key: String?, value: String?) {

            setIntoSharedPref(App.context,key!!, value!!)

        }

        fun getStringKeyValue(key: String?): String? {
            return  getFromSharedPref(App.context,key!!)
        }
    }

}