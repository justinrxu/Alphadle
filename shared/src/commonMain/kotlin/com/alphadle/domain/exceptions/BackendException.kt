package com.alphadle.domain.exceptions

internal abstract class BackendException(override val message: String? = null): Exception(message)
