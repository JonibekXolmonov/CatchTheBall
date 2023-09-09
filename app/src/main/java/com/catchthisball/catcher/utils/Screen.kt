package com.catchthisball.catcher.utils

sealed class Screen(
    val route: String
) {
    object Menu : Screen("menu")
    object Game : Screen("game")
    object BallSelection : Screen("ballSelection")
    object BestScore : Screen("bestScore")
}