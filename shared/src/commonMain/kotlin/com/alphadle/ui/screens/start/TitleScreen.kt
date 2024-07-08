package com.alphadle.ui.screens.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.alphadle.ui.theme.LocalDimensions
import com.alphadle.ui.util.stringDate

@Composable
internal fun TitleScreen(
    navController: NavController,
    titleViewModel: TitleViewModel
) {
    LaunchedEffect(true) {
        titleViewModel.checkDailyReset()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(LocalDimensions.current.largeSpacing),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Alphadle", style = MaterialTheme.typography.displayLarge)
            Text(
                text = titleViewModel.date.stringDate(),
                style = MaterialTheme.typography.bodyMedium
            )
            Button(
                onClick = { navController.navigate("/gameScreen") },
                modifier = Modifier.padding(horizontal = LocalDimensions.current.largePadding)
            ) {
                Text(text = "Play", style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}