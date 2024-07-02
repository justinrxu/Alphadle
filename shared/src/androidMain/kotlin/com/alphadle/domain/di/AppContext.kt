package com.alphadle.domain.di

import android.app.Application
import android.content.Context

object AppContext {
    private var _application: Application? = null

    var context: Context
        get() = _application?.applicationContext
            ?: throw Exception("Application context isn't initialized")
        set(value) { _application = value as Application }
}