package com.alphadle.ui.util

internal fun String.capitalize() = lowercase().replaceFirstChar { it.titlecase() }
