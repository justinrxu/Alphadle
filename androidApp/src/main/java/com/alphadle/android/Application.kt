package com.alphadle.android

import android.app.Application
import com.alphadle.domain.di.AppContext
import com.alphadle.domain.di.initApp

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        AppContext.context = this
        initApp()
    }
}