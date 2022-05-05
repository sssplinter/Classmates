package com.breaktime.classmates

import android.app.Application

class App : Application() {
    init {
        _instance = this
    }

    companion object {
        private var _instance: App? = null

        val instance: App
            get() = _instance as App

    }
}