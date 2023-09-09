package com.catchthisball.catcher.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val White = Color(0xFFFFFFFF)
val GREY = Color(0xFFCFCFCF)
val White90 = Color(0xFFFFFFFF).copy(0.9f)
val GREY90 = Color(0xFFCFCFCF).copy(0.9f)
val DarkerGrey = Color(0xFF646464)
val LightGrey = Color(0xFFD9D9D9)
val Black2 = Color(0xFF1D1D1D)

val MainBackground = Brush.radialGradient(
    colors = listOf(White, GREY),
    radius = 380f,
)

val MainBackground90 = Brush.radialGradient(
    colors = listOf(White90, GREY90),
    radius = 380f,
)