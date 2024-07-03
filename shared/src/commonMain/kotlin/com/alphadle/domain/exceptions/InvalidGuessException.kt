package com.alphadle.domain.exceptions

internal class InvalidGuessException(
    override val message: String? = "Invalid word"
): BackendException(message)