package com.alphadle.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.Dp

// (Height, Width)
@Composable
@ReadOnlyComposable
internal expect fun getScreenSizeInfo(): Pair<Dp, Dp>