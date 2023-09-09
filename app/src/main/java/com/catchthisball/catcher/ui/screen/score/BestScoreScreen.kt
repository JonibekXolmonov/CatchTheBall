package com.catchthisball.catcher.ui.screen.score

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.catchthisball.catcher.ui.screen.common.IconButton
import com.catchthisball.catcher.ui.theme.Black2
import com.catchthisball.catcher.ui.theme.MainBackground
import com.catchthisball.catcher.ui.theme.White
import com.frt.cardsuits.R

@Composable
fun BestScoreScreen(
    modifier: Modifier = Modifier,
    viewModel: ScoreViewModel = hiltViewModel(),
    onMenuBack: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.getScores()
    }

    val scores by remember { viewModel.top5Score }

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

        Ball()

        ScoreList(scores = scores)

    }
}

@Composable
fun ScoreList(modifier: Modifier = Modifier, scores: List<Int>) {

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
                .height(282.dp)
                .background(White)
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 36.dp)
                .constrainAs(box) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                },
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.background(White).width(200.dp),
            ) {
                items(scores) {
                    Text(
                        text = it.toString(),
                        color = Black2,
                        fontWeight = FontWeight(900),
                        fontSize = 36.sp
                    )
                }
            }
        }
    }

}

@Composable
fun Ball(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.medal_with_star),
        contentDescription = null,
        modifier = modifier
            .size(54.dp)
    )
}