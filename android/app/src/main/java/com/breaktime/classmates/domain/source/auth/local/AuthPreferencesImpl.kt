package com.breaktime.classmates.domain.source.auth.local

import android.content.Context
import android.content.SharedPreferences

private const val AUTH_TOKEN = "AUTH_TOKEN"
private const val AUTH_PREFERENCES = "AUTH_PREFERENCES"

class AuthPreferencesImpl(context: Context) : AuthPreferences {
    private val sharedPref = context.getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE)

    override fun getAuthToken(): String = sharedPref.getString(AUTH_TOKEN, "").orEmpty()

    override fun setAuthToken(authToken: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(AUTH_TOKEN, authToken)
        editor.apply()
    }
}