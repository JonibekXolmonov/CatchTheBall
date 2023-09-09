package com.catchthisball.catcher.ui.screen.game

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.catchthisball.catcher.ui.screen.common.FingerPath
import com.catchthisball.catcher.ui.screen.common.IconButton
import com.catchthisball.catcher.ui.screen.finish_dialog.GameFinishDialog
import com.catchthisball.catcher.ui.screen.pause_dialog.GamePauseDialog
import com.catchthisball.catcher.ui.theme.MainBackground
import com.catchthisball.catcher.utils.MyToOffsetAnim
import com.frt.cardsuits.R
import kotlin.math.roundToInt

@Composable
fun GameScreen2(
    modifier: Modifier = Modifier,
    viewModel: GameViewModel2 = hiltViewModel(),
    onHomeClick: () -> Unit,
    onVibrate: () -> Unit
) {

    val ball by remember { viewModel.ball }
    val ballOffset by remember { viewModel.ballOffset }
    val isPaused by remember { viewModel.isPaused }
    val isFinished by remember { viewModel.isFinished }
    val score by remember { viewModel.score }
    val rank by remember { viewModel.rank }
    val ballFailCatchOffset by remember { viewModel.ballFailCatchOffset }

    var gateXOffset by remember { mutableStateOf(0f) }
    var gateWidth by remember { mutableStateOf(0) }
    var gateOffset by remember { mutableStateOf(Offset.Zero) }
    var gateSize by remember { mutableStateOf(IntSize.Zero) }
    var lastBallOffset by remember { mutableStateOf(IntOffset.Zero) }

    if (isFinished) {
        LaunchedEffect(Unit) {
            viewModel.saveAndGetRank(score)
        }
    }

    if (rank != 0) {
        GameFinishDialog(score = score, ranking = rank, onHomeClick = {
            onHomeClick()
            onVibrate()
        }, onRestart = {
            viewModel.startGame()
            onVibrate()
        })
    }

    if (isPaused) {
        GamePauseDialog(onResumeClick = {
            viewModel.resumeGame()
            onVibrate()
        }, onHomeClick = {
            onHomeClick()
            onVibrate()
        })
    }

    LaunchedEffect(Unit) {
        viewModel.startGame()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MainBackground)
            .padding(bottom = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = modifier
                .fillMaxWidth()
                .padding(end = 16.dp, top = 12.dp)
        ) {
            IconButton(id = R.drawable.ic_pause, onClick = {
                viewModel.pauseGame()
                onVibrate()
            })
        }

        Text(
            text = score.toString(),
            color = Color.Black,
            fontWeight = FontWeight(900),
            fontSize = 42.sp,
        )

        ConstraintLayout(
            modifier = modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {

            val (gate, bal, fakeBall) = createRefs()

            Image(contentScale = ContentScale.Crop,
                painter = painterResource(
                    id = R.drawable.football_gates
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 147.dp, height = 85.dp)
                    .onSizeChanged {
                        gateSize = it
                    }
                    .offset { IntOffset(x = (gateXOffset * 1.2f).roundToInt(), y = (-100)) }
                    .onSizeChanged {
                        gateWidth = it.width
                    }
                    .onGloballyPositioned { coordinates ->
                        val offset = coordinates.positionInWindow()
                        gateOffset = offset
                    }
                    .constrainAs(gate) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })

            if (isPaused || isFinished)
                Image(
                    painter = painterResource(id = ball),
                    contentDescription = null,
                    modifier = modifier
                        .size(42.dp)
                        .constrainAs(fakeBall) {
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .offset(
                            x = if (isPaused) lastBallOffset.x.dp else ballFailCatchOffset.x.dp,
                            y = if (isPaused) lastBallOffset.y.dp else ballFailCatchOffset.y.dp
                        )
                )
            else
                MovingBall2(
                    myToOffsetAnim = ballOffset,
                    ball = ball,
                    onTopReached = { ballOffset, ballSize ->
                        viewModel.checkIfBallCatch(
                            ballOffset,
                            gateOffset,
                            gateSize,
                            ballSize
                        )
                    },
                    modifier = modifier.constrainAs(bal) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    onOffsetChange = {
                        lastBallOffset = it
                    })
        }

        Box(modifier = Modifier.padding(bottom = 22.dp)) {
            if (isFinished) Image(
                painter = painterResource(id = R.drawable.close),
                contentDescription = null,
                modifier = modifier.size(64.dp)
            )
            else FingerPath {
                gateXOffset = it
            }
        }
    }
}

@Composable
fun MovingBall2(
    modifier: Modifier,
    myToOffsetAnim: MyToOffsetAnim,
    ball: Int,
    onTopReached: (Offset, IntSize) -> Unit,
    onOffsetChange: (IntOffset) -> Unit,
) {

    val infiniteTransition = rememberInfiniteTransition()
    var ancientOffset by remember { mutableStateOf(Offset.Zero) }
    var lastOffset by remember { mutableStateOf(Offset.Zero) }
    var currentOffset by remember { mutableStateOf(Offset.Zero) }

    val xof = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = myToOffsetAnim.x,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = myToOffsetAnim.time,
                easing = LinearEasing
            )
        )
    )

    val yof = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = myToOffsetAnim.y,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = myToOffsetAnim.time,
                easing = LinearEasing
            )
        )
    )

    BallImage2(
        ball = ball,
        x = xof.value,
        y = yof.value,
        modifier = modifier,
        onTargetReached = { offset, imageSize ->
            ancientOffset = lastOffset
            lastOffset = currentOffset
            currentOffset = offset
            if (currentOffset.y > lastOffset.y) {
                onTopReached(
                    lastOffset,
                    imageSize
                )
            }
            onOffsetChange(IntOffset(x = xof.value.toInt(), y = yof.value.toInt()))
        })
}

@Composable
fun BallImage2(
    modifier: Modifier,
    ball: Int,
    x: Float,
    y: Float,
    onTargetReached: (Offset, IntSize) -> Unit
) {
    var size = IntSize.Zero
    Image(painter = painterResource(id = ball),
        contentDescription = null,
        modifier = modifier
            .size(42.dp)
            .offset(x = x.dp, y = y.dp)
            .onSizeChanged {
                size = it
            }
            .onGloballyPositioned { coordinates ->
                val offset: Offset = coordinates.positionInWindow()
                onTargetReached(offset, size)
            })
}