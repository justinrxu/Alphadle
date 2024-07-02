package com.alphadle.data.util

import com.alphadle.domain.di.AppContext

internal actual fun getDataStorePath(): String =
    AppContext.context.filesDir.resolve(dataStoreFileName).absolutePath