package com.catchthisball.catcher.ui.screen.common

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.catchthisball.catcher.ui.theme.DarkerGrey
import com.catchthisball.catcher.ui.theme.White
import com.catchthisball.catcher.utils.noRippleClickable
import com.frt.cardsuits.R
import kotlin.math.roundToInt

@Composable
fun IconButton(modifier: Modifier = Modifier, @DrawableRes id: Int, onClick: () -> Unit) {

    ConstraintLayout(
        modifier = modifier
            .padding(top = 24.dp, end = 8.dp)
            .size(64.dp)
    ) {
        val (box1, box2) = createRefs()

        Box(
            modifier = modifier
                .size(60.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Black)
                .constrainAs(box1) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
        )
        Box(
            contentAlignment = Alignment.Center, modifier = modifier
                .size(60.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(White)
                .border(
                    BorderStroke(width = 2.dp, color = Color.Black),
                    RoundedCornerShape(16.dp)
                )
                .clickable {
                    onClick()
                }
                .constrainAs(box2) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        ) {
            Image(
                painter = painterResource(id = id),
                contentDescription = null,
                modifier = modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun CatchBall() {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = (-48).dp)
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.catch_text
            ), contentDescription = null,
            modifier = Modifier
                .size(width = 260.dp, height = 260.dp)
        )

        Image(
            painter = painterResource(
                id = R.drawable.ball_text
            ), contentDescription = null,
            modifier = Modifier
                .size(width = 240.dp, height = 130.dp)
                .offset(y = 8.dp)
        )
    }
}


@Composable
fun Gate(click: () -> Unit) {
    Image(
        painter = painterResource(
            id = R.drawable.football_gates
        ), contentDescription = null,
        modifier = Modifier
            .size(width = 210.dp, height = 180.dp)
            .offset(y = (-100).dp)
            .noRippleClickable { click() }
    )
}

@Composable
fun FingerPath(
    modifier: Modifier = Modifier,
    backColor: Color = DarkerGrey,
    cornerRadius: Dp = 8.dp,
    onFingerMove: (horizontalMove: Float) -> Unit = {}
) {

    Box(contentAlignment = Alignment.Center) {

        var size = IntSize(0, 0)
        Box(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(cornerRadius))
                .fillMaxWidth()
                .height(18.dp)
                .background(backColor)
                .onSizeChanged {
                    size = it
                },
        )

        var offsetX by remember { mutableStateOf(0f) }

        Box(
            modifier = modifier
                .padding(horizontal = 32.dp)
                .offset { IntOffset(offsetX.roundToInt(), 30) }
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        if (delta + offsetX in -size.width / 2f + 40..size.width / 2f - 40) {
                            offsetX += delta
                            onFingerMove(offsetX)
                        }
                    }
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.finger),
                contentDescription = null,
                modifier = modifier
                    .size(50.dp)
            )
        }
    }
}