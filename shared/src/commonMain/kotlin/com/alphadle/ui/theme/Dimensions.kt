package com.alphadle.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal data class Dimensions(
    val spacing: Dp,
    val largeSpacing: Dp,
    val padding: Dp,
    val largePadding: Dp,
    val appPadding: Dp
)

internal val dimensions = Dimensions(
    spacing = 5.dp,
    largeSpacing = 15.dp,
    padding = 5.dp,
    largePadding = 10.dp,
    appPadding = 15.dp
)

internal val LocalDimensions = staticCompositionLocalOf { dimensions }