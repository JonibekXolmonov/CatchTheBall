package com.catchthisball.catcher.ui.screen.pause_dialog

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.catchthisball.catcher.ui.screen.common.IconButton
import com.catchthisball.catcher.ui.theme.MainBackground90
import com.frt.cardsuits.R

@Composable
fun GamePauseDialog(
    modifier: Modifier = Modifier,
    onResumeClick: () -> Unit,
    onHomeClick: () -> Unit
) {

    Dialog(
        onDismissRequest = { /*TODO*/ },
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

        DialogUI(modifier, width, height, onResumeClick = onResumeClick, onHomeClick = onHomeClick)
    }

}

@Composable
fun DialogUI(
    modifier: Modifier,
    width: Dp,
    height: Dp,
    onResumeClick: () -> Unit,
    onHomeClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .requiredSize(width, height)
            .background(MainBackground90),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        IconButton(id = R.drawable.ic_play, onClick = onResumeClick)


        IconButton(id = R.drawable.ic_home, onClick = onHomeClick)
    }
}
