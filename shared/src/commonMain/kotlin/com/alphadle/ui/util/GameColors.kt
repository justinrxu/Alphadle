package com.alphadle.ui.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

internal val correctColor: Color
    @Composable
    @ReadOnlyComposable
    get() = MaterialTheme.colorScheme.primaryContainer

internal val higherColor: Color
    @Composable
    @ReadOnlyComposable
    get() = MaterialTheme.colorScheme.errorContainer

internal val lowerColor: Color
    @Composable
    @ReadOnlyComposable
    get() = MaterialTheme.colorScheme.tertiaryContainer