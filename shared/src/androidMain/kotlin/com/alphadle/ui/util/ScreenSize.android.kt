package com.alphadle.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
@ReadOnlyComposable
internal actual fun getScreenSizeInfo(): Pair<Dp, Dp> {
    val config = LocalConfiguration.current

    return Pair(config.screenHeightDp.dp, config.screenWidthDp.dp)
}