package com.alphadle.ui.screens.end

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.alphadle.ui.theme.LocalDimensions
import com.alphadle.ui.util.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import kotlin.math.roundToInt

@Composable
internal fun EndScreen(
    endViewModel: EndViewModel,
    navController: NavController
) {
    val uiState: EndViewModel.EndUiState by endViewModel.endUiState.collectAsStateWithLifecycle()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(LocalDimensions.current.appPadding)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        }
        (uiState as? EndViewModel.EndUiState.Loaded)?.let { uiState ->
            Column(
                verticalArrangement = Arrangement.spacedBy(LocalDimensions.current.largeSpacing),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.85f)
            ) {
                Text(
                    text =
                        if (!uiState.savedGameData.completed) ""
                        else if (uiState.savedGameData.didWin) "You win!"
                        else "Nice try...",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "STATISTICS",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.Start)
                )
                Statistics(
                    stats = with(uiState.totalStats) {
                        mapOf(
                            "Games Played" to gamesPlayed,
                            "Win %" to (gamesWon.toFloat() / gamesPlayed * 100).roundToInt(),
                            "Current Streak" to currentStreak
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                uiState.totalStats.distribution[uiState.savedGameData.difficulty]
                    ?.let { distribution ->
                        Text(
                            text = "GUESS DISTRIBUTION",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.align(Alignment.Start)
                        )
                        FrequencyGraph(
                            currentTries = uiState.savedGameData.attempts,
                            distribution = distribution
                        )
                    }
                if (uiState.savedGameData.completed) {
                    Button(
                        onClick = {

                        },
                        modifier = Modifier.height(IntrinsicSize.Min)
                    ) {
                        Text(text = "Share ")
                        Icon(imageVector = Icons.Default.Share, contentDescription = "Share")
                    }
                }
            }
        }
    }
}

@Composable
private fun Statistics(
    stats: Map<String, Int>,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        stats.forEach { (name, stat) ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(IntrinsicSize.Min)
            ) {
                Text(text = "$stat")
                Text(text = name, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
private fun FrequencyGraph(
    currentTries: Int,
    distribution: List<Int>,
    modifier: Modifier = Modifier
) {
    val totalWins = distribution.sum()

    println(distribution)
    Column(
        verticalArrangement = Arrangement.spacedBy(LocalDimensions.current.spacing),
        modifier = modifier
    ) {
        distribution.forEachIndexed { i, frequency ->
            val tries = i + 1
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                Text(text = "$tries", style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.width(LocalDimensions.current.spacing))
                Box(
                    contentAlignment = Alignment.CenterEnd,
                    modifier = Modifier
                        .fillMaxWidth(frequency.toFloat() / totalWins)
                        .background(
                            if (tries == currentTries) MaterialTheme.colorScheme.primaryContainer
                            else MaterialTheme.colorScheme.surfaceContainer
                        )
                        .padding(LocalDimensions.current.padding)
                ) {
                    Text(text = "$frequency", style = MaterialTheme.typography.titleSmall)
                }
            }
        }
    }
}
