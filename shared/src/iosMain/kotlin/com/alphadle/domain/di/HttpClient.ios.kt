package com.alphadle.domain.di

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.Darwin

internal actual val httpClientEngineFactory: HttpClientEngineFactory<HttpClientEngineConfig>
    get() = Darwin