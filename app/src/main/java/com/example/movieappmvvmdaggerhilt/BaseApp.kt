package com.example.movieappmvvmdaggerhilt

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApp: Application() {

    override fun onCreate() {
        super.onCreate()
//        appContext = this
    }
}