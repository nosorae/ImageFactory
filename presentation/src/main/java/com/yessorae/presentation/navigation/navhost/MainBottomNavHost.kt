package com.yessorae.presentation.navigation.navhost

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yessorae.presentation.navigation.destination.HistoryBottomTabDestination
import com.yessorae.presentation.navigation.destination.InpaintingBottomTabDestination
import com.yessorae.presentation.navigation.destination.MainBottomTabDestination
import com.yessorae.presentation.navigation.destination.TxtToImgBottomTabDestination
import com.yessorae.presentation.ui.screen.main.history.TxtToImgHistoryScreen
import com.yessorae.presentation.ui.screen.main.tti.TxtToImgScreen
import com.yessorae.presentation.util.compose.ReadyScreen

@Composable
fun MainBottomNavHost(
    navController: NavHostController,
    startDestination: MainBottomTabDestination = TxtToImgBottomTabDestination,
    modifier: Modifier,
    onNavOutEvent: (route: String) -> Unit
) {
    NavHost(
        navController = navController,
        modifier = modifier.fillMaxSize(),
        startDestination = startDestination.route
    ) {
        composable(
            route = TxtToImgBottomTabDestination.route
        ) {
            TxtToImgScreen(
                onNavOutEvent = { route ->
                    onNavOutEvent(route)
                }
            )
        }

        composable(
            route = InpaintingBottomTabDestination.route
        ) {
            ReadyScreen(InpaintingBottomTabDestination.route)
        }

        composable(
            route = HistoryBottomTabDestination.route
        ) {
            TxtToImgHistoryScreen(
                onNavOutEvent = onNavOutEvent
            )
        }
    }
}
