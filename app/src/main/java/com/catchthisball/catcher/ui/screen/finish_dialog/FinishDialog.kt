package com.catchthisball.catcher.ui.screen.finish_dialog

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.catchthisball.catcher.ui.screen.common.IconButton
import com.catchthisball.catcher.ui.theme.Black2
import com.catchthisball.catcher.ui.theme.DarkerGrey
import com.catchthisball.catcher.ui.theme.MainBackground90
import com.frt.cardsuits.R
import kotlinx.coroutines.delay

@Composable
fun GameFinishDialog(
    modifier: Modifier = Modifier,
    score: Int,
    ranking: Int,
    onHomeClick: () -> Unit,
    onRestart: () -> Unit
) {

    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        val context = LocalContext.current
        val windowManager =
            remember { context.getSystemService(Context.WINDOW_SERVICE) as WindowManager }

        val metrics = DisplayMetrics().apply {
            windowManager.defaultDisplay.getRealMetrics(this)
        }
        val (width, height) = with(LocalDensity.current) {
            Pair(metrics.widthPixels.toDp(), (metrics.heightPixels).toDp())
        }

        DialogUI(
            modifier,
            score,
            ranking,
            width,
            height,
            onRestart = onRestart,
            onHomeClick = onHomeClick
        )
    }
}

@Composable
fun DialogUI(
    modifier: Modifier,
    score: Int,
    ranking: Int,
    width: Dp,
    height: Dp,
    onHomeClick: () -> Unit,
    onRestart: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .requiredSize(width, height)
            .background(MainBackground90),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = score.toString(),
            color = Black2,
            fontWeight = FontWeight(900),
            fontSize = 42.sp,
            modifier = modifier.padding(bottom = 14.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.medal_with_star),
            contentDescription = null,
            modifier
                .padding(bottom = 14.dp)
                .size(54.dp)
        )

        Text(
            text = ranking.toString(),
            color = DarkerGrey,
            fontWeight = FontWeight(900),
            fontSize = 32.sp,
            modifier = modifier.padding(bottom = 24.dp)
        )

        IconButton(id = R.drawable.ic_restart, onClick = onRestart)


        IconButton(id = R.drawable.ic_home, onClick = onHomeClick)
    }
}
