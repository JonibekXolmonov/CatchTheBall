package com.catchthisball.catcher.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.catchthisball.catcher.ui.screen.game.GameScreen2
import com.catchthisball.catcher.ui.screen.menu.MenuScreen
import com.catchthisball.catcher.ui.screen.score.BestScoreScreen
import com.catchthisball.catcher.ui.screen.select.BallSelectionScreen
import com.catchthisball.catcher.utils.Screen

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Menu.route,
    onVibrate: () -> Unit,
    onSound: (Boolean) -> Unit
) {
    NavHost(
        navController,
        startDestination = startDestination,
    ) {
        composable(route = Screen.Menu.route) {
            MenuScreen(
                onBallSelectionClick = {
                    navController.navigate(Screen.BallSelection.route)
                    onVibrate()
                },
                onGameStartClick = {
                    navController.navigate(Screen.Game.route)
                    onVibrate()
                },
                onBestScoreClick = {
                    navController.navigate(Screen.BestScore.route)
                    onVibrate()
                },
                onVibrate = onVibrate,
                onSound = onSound
            )
        }

        composable(route = Screen.Game.route) {
            GameScreen2(onHomeClick = {
                navController.popBackStack()
            }, onVibrate = onVibrate)
        }

        composable(route = Screen.BallSelection.route) {
            BallSelectionScreen(
                onMenuBack = {
                    navController.popBackStack()
                    onVibrate()
                },
                onVibrate = onVibrate
            )
        }

        composable(route = Screen.BestScore.route) {
            BestScoreScreen {
                navController.popBackStack()
            }
        }
    }
}