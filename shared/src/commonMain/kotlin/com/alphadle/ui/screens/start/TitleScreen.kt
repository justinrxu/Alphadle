package com.alphadle.ui.screens.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.alphadle.ui.theme.LocalDimensions
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun TitleScreen(
    navController: NavController,
    titleViewModel: TitleViewModel = koinViewModel()
) {
    LaunchedEffect(true) {
        titleViewModel.checkDailyReset()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(LocalDimensions.current.spacing),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Alphadle", style = MaterialTheme.typography.displayLarge)
            Button(
                onClick = { navController.navigate("/gameScreen") }
            ) {
                Text(text = "Play", style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}