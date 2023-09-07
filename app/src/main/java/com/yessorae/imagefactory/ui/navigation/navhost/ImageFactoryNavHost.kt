package com.yessorae.imagefactory.ui.navigation.navhost

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yessorae.imagefactory.ui.navigation.destination.Destination
import com.yessorae.imagefactory.ui.navigation.destination.MainDestination
import com.yessorae.imagefactory.ui.screen.main.MainScreen
import com.yessorae.imagefactory.util.navigateSingleTopTo

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
    }

}