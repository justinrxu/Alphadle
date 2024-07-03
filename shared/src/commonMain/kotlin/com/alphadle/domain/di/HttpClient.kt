package com.alphadle.domain.di

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory

internal expect val httpClientEngineFactory: HttpClientEngineFactory<HttpClientEngineConfig>