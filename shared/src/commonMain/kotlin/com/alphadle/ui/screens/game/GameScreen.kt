package com.alphadle.ui.screens.game

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.navigation.NavController
import com.alphadle.domain.model.GameData
import com.alphadle.ui.theme.LocalDimensions
import com.alphadle.ui.util.collectAsStateWithLifecycle
import com.alphadle.ui.util.correctColor
import com.alphadle.ui.util.getScreenSizeInfo
import com.alphadle.ui.util.higherColor
import com.alphadle.ui.util.lowerColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun GameScreen(
    navController: NavController,
    gameViewModel: GameViewModel = koinViewModel()
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val (screenHeight, screenWidth) = getScreenSizeInfo()

    val gameData: GameData by gameViewModel.savedGameData.collectAsStateWithLifecycle()

    var currentGuess: String by remember { mutableStateOf("") }
    var animateCurrentGuess: Boolean by remember { mutableStateOf(false) }

    val showError: (String?) -> Unit = {
        it?.let { message ->
            scope.launch { snackbarHostState.showSnackbar(message) }
        }
    }

    LaunchedEffect(gameData.completed) {
        if (gameData.completed && animateCurrentGuess) {
            delay(500)
            navController.navigate("/endScreen")
            animateCurrentGuess = false
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(LocalDimensions.current.largeSpacing),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(LocalDimensions.current.appPadding)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(LocalDimensions.current.largeSpacing),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)
            ) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ExitToApp,
                        contentDescription = null
                    )
                }
                if (gameData.completed && !animateCurrentGuess) {
                    IconButton(onClick = { navController.navigate("/endScreen") }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.List,
                            contentDescription = null
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight(0.3f)
                            .aspectRatio(1f)
                            .background(correctColor)
                    )
                    Text(" - correct")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight(0.3f)
                            .aspectRatio(1f)
                            .background(higherColor)
                    )
                    Text(" - higher")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight(0.3f)
                            .aspectRatio(1f)
                            .background(lowerColor)
                    )
                    Text(" - lower")
                }
            }
            Row {
                Text(text = "Remaining Guesses: ")
                AnimatedContent(
                    targetState = gameData.remainingAttempts,
                    transitionSpec = {
                        if (targetState > initialState) {
                            (slideInVertically { height -> height } + fadeIn()).togetherWith(
                                slideOutVertically { height -> -height } + fadeOut())
                        } else {
                            (slideInVertically { height -> -height } + fadeIn()).togetherWith(
                                slideOutVertically { height -> height } + fadeOut())
                        }.using(
                            SizeTransform(clip = false)
                        )
                    }
                ) {
                    Text(text = "${gameData.remainingAttempts}")
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(LocalDimensions.current.spacing)) {
                gameData.guesses.forEach { guess ->
                    LetterBoxes(
                        guess = guess,
                        wordLength = gameData.difficulty.wordLength,
                        modifier = { i ->
                            Modifier.background(
                                if (i >= guess.length) MaterialTheme.colorScheme.surfaceContainer
                                else if (gameData.answer[i] > guess[i]) higherColor
                                else if (gameData.answer[i] < guess[i]) lowerColor
                                else correctColor
                            )
                        }
                    )
                }
                if (!gameData.completed) {
                    LetterBoxes(
                        guess = currentGuess,
                        wordLength = gameData.difficulty.wordLength,
                        modifier = { i ->
                            val animationDuration = 500L

                            var shrinkAnimationState by remember { mutableStateOf(false) }
                            var showAnswerColor by remember { mutableStateOf(false) }

                            LaunchedEffect(animateCurrentGuess) {
                                if (animateCurrentGuess) {
                                    delay(i * animationDuration)
                                    shrinkAnimationState = true
                                    delay(animationDuration / 2)
                                    showAnswerColor = true
                                    shrinkAnimationState = false

                                    if (i == gameData.answer.lastIndex) {
                                        delay(animationDuration / 2)
                                        gameViewModel.submitGuess(
                                            guess = currentGuess,
                                            onError = showError
                                        )
                                        if (
                                            currentGuess != gameData.answer &&
                                            gameData.remainingAttempts != 1
                                        ) {
                                            animateCurrentGuess = false
                                        }
                                        currentGuess = ""
                                    }
                                } else {
                                    showAnswerColor = false
                                }
                            }

                            val animatedHeight: Dp by animateDpAsState(
                                targetValue = if (shrinkAnimationState) 0.dp else 60.dp,
                                animationSpec = tween(
                                    durationMillis = animationDuration.toInt() / 2
                                )
                            )

                            Modifier
                                .height(animatedHeight)
                                .background(
                                    if (i >= currentGuess.length || !showAnswerColor)
                                        MaterialTheme.colorScheme.surfaceContainer
                                    else if (gameData.answer[i] > currentGuess[i]) higherColor
                                    else if (gameData.answer[i] < currentGuess[i]) lowerColor
                                    else correctColor
                                )
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Keyboard(
                onKeyPress = { key ->
                    when (key) {
                        "ENTER" -> gameViewModel.validateGuess(
                            guess = currentGuess,
                            onSuccess = { animateCurrentGuess = true },
                            onError = showError
                        )

                        "DEL" -> if (currentGuess.isNotEmpty()) {
                            currentGuess = currentGuess.substring(0, currentGuess.length - 1)
                        }

                        else -> if (currentGuess.length < gameData.difficulty.wordLength) {
                            currentGuess += key
                        }
                    }
                },
                keyWidth = min(
                    (screenWidth - LocalDimensions.current.appPadding.times(2)).div(11),
                    48.dp
                ),
                enabled = !animateCurrentGuess && !gameData.completed
            )
        }
    }
}

@Composable
private fun LetterBoxes(
    guess: String,
    wordLength: Int,
    modifier: @Composable (Int) -> Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(LocalDimensions.current.spacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(wordLength) { i ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier(i).size(60.dp)
            ) {
                if (i < guess.length) {
                    Text(guess[i].uppercase())
                }
            }
        }
    }
}

@Composable
private fun Keyboard(
    onKeyPress: (String) -> Unit,
    enabled: Boolean = true,
    keyWidth: Dp = 36.dp,
    modifier: Modifier = Modifier
) {
    val keys = listOf(
        listOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
        listOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
        listOf("ENTER", "Z", "X", "C", "V", "B", "N", "M", "DEL")
    )

    val keySpacing = keyWidth.div(9)

    Column(
        verticalArrangement = Arrangement.spacedBy(keySpacing),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        keys.forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(keySpacing)) {
                row.forEach { key ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .height(keyWidth.times(1.33f))
                            .run {
                                if (key == "ENTER" || key == "DEL") weight(1f)
                                else width(keyWidth)
                            }
                            .clip(MaterialTheme.shapes.small)
                            .background(MaterialTheme.colorScheme.surfaceContainer)
                            .run {
                                if (enabled) this.clickable { onKeyPress(key) }
                                else this
                            }
                    ) {
                        Text(text = key)
                    }
                }
            }
        }
    }
}
