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
import androidx.navigation.navArgument
import com.alphadle.domain.model.GameData
import com.alphadle.ui.screens.end.EndScreen
import com.alphadle.ui.theme.AppTheme
import com.alphadle.ui.screens.game.GameScreen
import com.alphadle.ui.screens.start.TitleScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.ParametersHolder

@OptIn(KoinExperimentalAPI::class)
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
                TitleScreen(
                    titleViewModel = koinViewModel(),
                    navController = navController
                )
            }
            composable(
                route = "/gameScreen",
                enterTransition = { slideInVertically() + fadeIn() },
                exitTransition = { slideOutVertically() + fadeOut() },
                arguments = listOf(
                    navArgument("difficulty") {
                        defaultValue = GameData.Difficulty.NORMAL.name
                    }
                )
            ) { entry ->
                val difficulty = GameData.Difficulty.valueOf(
                    entry.arguments?.getString("difficulty")
                        ?: GameData.Difficulty.NORMAL.name
                )

                GameScreen(
                    navController = navController,
                    gameViewModel = koinViewModel {
                        ParametersHolder(mutableListOf(difficulty))
                    }
                )
            }
            composable(route = "/endScreen") { entry ->
                val difficulty = GameData.Difficulty.valueOf(
                entry.arguments?.getString("difficulty")
                    ?: GameData.Difficulty.NORMAL.name
                )
                EndScreen(
                    endViewModel = koinViewModel {
                        ParametersHolder(mutableListOf(difficulty))
                    },
                    navController = navController
                )
            }
        }
    }
}