package com.yessorae.presentation.navigation.navhost

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yessorae.presentation.navigation.destination.Destination
import com.yessorae.presentation.navigation.destination.InPaintingResultDestination
import com.yessorae.presentation.navigation.destination.MainDestination
import com.yessorae.presentation.navigation.destination.TxtToImgResultDestination
import com.yessorae.presentation.ui.screen.main.MainScreen
import com.yessorae.presentation.ui.screen.main.inpainting.InPaintingScreen
import com.yessorae.presentation.ui.screen.result.inpainting.InPaintingResultScreen
import com.yessorae.presentation.ui.screen.result.tti.TxtToImgResultScreen
import com.yessorae.presentation.util.navigateSingleTopTo

@Composable
fun ImageFactoryNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: Destination = MainDestination
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination.route
    ) {
        composable(
            route = MainDestination.route
        ) {
            MainScreen(
                onNavOutEvent = { route ->
                    navController.navigateSingleTopTo(route)
                }
            )
        }

        composable(
            route = TxtToImgResultDestination.routeWithArgs,
            arguments = TxtToImgResultDestination.arguments
        ) {
            TxtToImgResultScreen(
                onNavEvent = { route ->
                    navController.navigate(
                        route,
                        navOptions = NavOptions
                            .Builder()
                            .setLaunchSingleTop(singleTop = false)
                            .build()
                    )
                },
                onBackEvent = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = InPaintingResultDestination.routeWithArgs,
            arguments = InPaintingResultDestination.arguments
        ) {
            InPaintingResultScreen(
                onNavEvent = { route ->
                    navController.navigate(
                        route,
                        navOptions = NavOptions
                            .Builder()
                            .setLaunchSingleTop(singleTop = false)
                            .build()
                    )
                },
                onBackEvent = {
                    navController.popBackStack()
                }
            )
        }
    }
}
