package com.catchthisball.catcher.ui.screen.select

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.catchthisball.catcher.ui.screen.common.IconButton
import com.catchthisball.catcher.ui.theme.LightGrey
import com.catchthisball.catcher.ui.theme.MainBackground
import com.catchthisball.catcher.ui.theme.White
import com.catchthisball.catcher.utils.noRippleClickable
import com.frt.cardsuits.R

@Composable
fun BallSelectionScreen(
    modifier: Modifier = Modifier,
    viewModel: BallSelectionViewModel = hiltViewModel(),
    onMenuBack: () -> Unit,
    onVibrate: () -> Unit,
) {

    val selectedBall by remember {
        viewModel.ball
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MainBackground),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(54.dp)
    ) {

        Box(
            modifier = modifier
                .padding(end = 16.dp, top = 12.dp)
                .fillMaxWidth(), contentAlignment = Alignment.CenterEnd
        ) {
            IconButton(id = R.drawable.ic_home, onClick = onMenuBack)
        }

        Ball(selectedBall)

        BallCollection {
            viewModel.saveSelectedBall(it)
            onVibrate()
        }
    }
}

@Composable
fun BallCollection(modifier: Modifier = Modifier, onBallSelected: (Int) -> Unit) {

    ConstraintLayout(
        modifier
            .padding(horizontal = 32.dp)
            .height(286.dp)
            .width(262.dp)
    ) {

        val (box, back) = createRefs()

        Box(
            modifier = modifier
                .padding(end = 8.dp, top = 4.dp)
                .fillMaxHeight()
                .clip(RoundedCornerShape(18.dp))
                .background(Color.Black)
                .fillMaxWidth()
                .constrainAs(back) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
        )

        Box(
            modifier = modifier
                .padding(horizontal = 4.dp)
                .border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(18.dp))
                .clip(RoundedCornerShape(18.dp))
                .background(White)
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 36.dp)
                .constrainAs(box) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        ) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                modifier = modifier.background(White)
            ) {
                items(9) {
                    if (it <= balls.size - 1)
                        Ball(ballId = balls[it]) {
                            onBallSelected(balls[it])
                        }
                    else
                        NoBall()
                }
            }
        }
    }
}

@Composable
fun NoBall(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center) {
        Box(
            modifier = modifier
                .clip(CircleShape)
                .background(LightGrey)
                .size(54.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.question_mark),
                contentDescription = null,
                modifier = modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun Ball(ballId: Int, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Image(
        painter = painterResource(id = ballId),
        contentDescription = null,
        modifier = modifier
            .size(54.dp)
            .noRippleClickable {
                onClick()
            }
    )
}

val balls = listOf(
    R.drawable.soccer_ball,
    R.drawable.soccer_ball_2,
    R.drawable.soccer_ball_3,
    R.drawable.soccer_ball_4,
    R.drawable.soccer_ball_5,
)