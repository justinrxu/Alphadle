package com.alphadle.ui

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alphadle.ui.screens.end.EndScreen
import com.alphadle.ui.theme.AppTheme
import com.alphadle.ui.screens.game.GameScreen
import com.alphadle.ui.screens.start.TitleScreen

@Composable
internal fun Application() {
    val navController = rememberNavController()

    AppTheme {
        NavHost(
            navController = navController,
            startDestination = "/titleScreen",
            modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars)
        ) {
            composable(
                route = "/titleScreen",
                enterTransition = { slideInVertically() + fadeIn() },
                exitTransition = { slideOutVertically() + fadeOut() }
            ) {
                TitleScreen(navController = navController)
            }
            composable(
                route = "/gameScreen",
                enterTransition = { slideInVertically() + fadeIn() },
                exitTransition = { slideOutVertically() + fadeOut() }
            ) {
                GameScreen(navController = navController)
            }
            composable(
                route = "/endScreen"
            ) {
                EndScreen(navController = navController)
            }
        }
    }
}