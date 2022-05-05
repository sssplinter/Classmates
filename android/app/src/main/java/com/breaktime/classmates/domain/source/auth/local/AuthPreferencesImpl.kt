package com.breaktime.classmates.domain.source.auth.local

import java.util.prefs.Preferences

private const val AUTH_TOKEN = "auth token"

class AuthPreferencesImpl : AuthPreferences {
    private val preferences = Preferences.userRoot().node(this::javaClass.name)

    override fun getAuthToken(): String = preferences.get(AUTH_TOKEN, "")

    override fun setAuthToken(authToken: String) {
        preferences.put(AUTH_TOKEN, authToken)
    }
}