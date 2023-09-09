package com.catchthisball.catcher.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.unit.dp

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

fun LayoutCoordinates.calculateTopLeft(): Offset {
    val globalPosition = this.localToWindow(Offset.Zero)
    val parentPosition = parentCoordinates?.calculateTopLeft() ?: Offset.Zero
    return Offset(
        x = globalPosition.x - parentPosition.x,
        y = globalPosition.y - parentPosition.y
    )
}