package com.catchthisball.catcher.ui.screen.menu

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.catchthisball.catcher.ui.screen.common.*
import com.catchthisball.catcher.ui.theme.MainBackground
import com.catchthisball.catcher.ui.theme.White
import com.frt.cardsuits.R

@Composable
fun MenuScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
    onBestScoreClick: () -> Unit,
    onGameStartClick: () -> Unit,
    onBallSelectionClick: () -> Unit,
    onVibrate: () -> Unit,
    onSound: (Boolean) -> Unit
) {

    var showSettings by remember {
        mutableStateOf(false)
    }

    val vibrateEnabled by remember {
        viewModel.vibrationEnabled
    }

    val soundEnabled by remember {
        viewModel.soundEnabled
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MainBackground)
            .padding(bottom = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        TopPart(
            showSettings = showSettings,
            vibrateEnabled = vibrateEnabled,
            soundEnabled = soundEnabled,
            onSettingsClick = {
                showSettings = !showSettings
                onVibrate()
            },
            onVibrationChange = {
                viewModel.controlVibration(it)
                onVibrate()
            },
            onSoundChange = {
                viewModel.controlSound(it)
                onVibrate()
                onSound(soundEnabled)
            })

        CatchBall()

        Gate {
            onGameStartClick()
        }

        MedalAndBall(
            onBestScoreClick = onBestScoreClick,
            onBallSelectionClick = onBallSelectionClick
        )

        Box(modifier = modifier.padding(bottom = 18.dp)) {
            FingerPath()
        }
    }
}

@Composable
fun MedalAndBall(
    modifier: Modifier = Modifier,
    onBestScoreClick: () -> Unit,
    onBallSelectionClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .offset(y = (-50).dp)
    ) {
        ConstraintLayout(
            modifier = modifier
                .size(100.dp, height = 90.dp)
        ) {

            val (box, back) = createRefs()

            Box(
                modifier = modifier
                    .clip(RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp))
                    .background(Color.Black)
                    .size(96.dp, height = 86.dp)
                    .constrainAs(back) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    })

            Box(
                modifier = modifier
                    .clip(RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp))
                    .border(
                        BorderStroke(width = 2.dp, color = Color.Black),
                        RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
                    )
                    .background(
                        White
                    )
                    .size(100.dp, height = 86.dp)
                    .constrainAs(box) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    }
                    .clickable {
                        onBestScoreClick()
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.medal_with_star),
                    contentDescription = null,
                    modifier = modifier.size(32.dp, 54.dp)
                )
            }
        }

        ConstraintLayout(
            modifier = modifier
                .size(104.dp, height = 90.dp)
        ) {

            val (box, back) = createRefs()

            Box(
                modifier = modifier
                    .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
                    .background(Color.Black)
                    .size(100.dp, height = 86.dp)
                    .constrainAs(back) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    })

            Box(
                modifier = modifier
                    .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
                    .border(
                        BorderStroke(width = 2.dp, color = Color.Black),
                        RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
                    )
                    .background(
                        White
                    )
                    .size(100.dp, height = 86.dp)
                    .constrainAs(box) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                    .clickable {
                        onBallSelectionClick()
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.soccer_ball),
                    contentDescription = null,
                    modifier = modifier.size(54.dp, 54.dp)
                )
            }

        }
    }
}

@Composable
fun TopPart(
    modifier: Modifier = Modifier,
    vibrateEnabled: Boolean,
    soundEnabled: Boolean,
    showSettings: Boolean,
    onSettingsClick: () -> Unit,
    onVibrationChange: (Boolean) -> Unit,
    onSoundChange: (Boolean) -> Unit,
) {

    Row(
        modifier = modifier
            .padding(end = 16.dp, top = 12.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        if (showSettings) {
            IconButton(id = if (vibrateEnabled) R.drawable.ic_vibration_on else R.drawable.ic_vibration_off) {
                onVibrationChange(!vibrateEnabled)
            }


            IconButton(id = if (soundEnabled) R.drawable.ic_sound_on else R.drawable.ic_sound_off) {
                onSoundChange(!soundEnabled)
            }
        }

        IconButton(id = R.drawable.ic_settings, onClick = onSettingsClick)
    }
}
