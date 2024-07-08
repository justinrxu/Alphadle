package com.alphadle.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal expect fun ShareButton(
    text: () -> String,
    modifier: Modifier = Modifier
)