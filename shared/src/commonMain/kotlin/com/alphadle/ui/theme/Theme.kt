package com.alphadle.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
internal fun AppTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalDimensions provides dimensions
    ) {
        MaterialTheme(
            colorScheme = lightScheme,
            typography = typography,
            content = content
        )
    }
}