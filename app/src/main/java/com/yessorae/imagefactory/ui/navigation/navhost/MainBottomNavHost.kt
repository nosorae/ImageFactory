package com.yessorae.imagefactory.ui.navigation.navhost

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yessorae.imagefactory.ui.navigation.destination.InpaintingBottomTabDestination
import com.yessorae.imagefactory.ui.navigation.destination.MainBottomTabDestination
import com.yessorae.imagefactory.ui.navigation.destination.HistoryBottomTabDestination
import com.yessorae.imagefactory.ui.navigation.destination.TxtToImgBottomTabDestination
import com.yessorae.imagefactory.ui.screen.main.history.TxtToImgHistoryScreen
import com.yessorae.imagefactory.ui.screen.main.tti.TxtToImgScreen
import com.yessorae.imagefactory.util.compose.ReadyScreen

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
