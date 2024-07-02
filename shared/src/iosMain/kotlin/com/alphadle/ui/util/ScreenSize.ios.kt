package com.alphadle.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@ReadOnlyComposable
internal actual fun getScreenSizeInfo(): Pair<Dp, Dp> {
    val density = LocalDensity.current
    val config = LocalWindowInfo.current.containerSize


    return Pair(
        with(density) { config.height.toDp() },
        with(density) { config.width.toDp() }
    )
}