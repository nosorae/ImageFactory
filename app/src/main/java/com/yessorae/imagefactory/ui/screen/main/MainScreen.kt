package com.yessorae.imagefactory.ui.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yessorae.imagefactory.ui.navigation.destination.MainDestination
import com.yessorae.imagefactory.ui.navigation.navhost.MainBottomNavHost
import com.yessorae.imagefactory.ui.theme.Gray200
import com.yessorae.imagefactory.util.navigateSingleTopTo

@Composable
fun MainScreen(
    onNavOutEvent: (route: String) -> Unit
) {
    val bottomNavController = rememberNavController()
    val currentBackstack by bottomNavController.currentBackStackEntryAsState()
    val currentDestination by remember {
        derivedStateOf {
            currentBackstack?.destination
        }
    }

    Scaffold(
        bottomBar = {
            MainScreenBottomNavigation(
                currentDestination = currentDestination,
                onNavInEvent = { route ->
                    bottomNavController.navigateSingleTopTo(route)
                }
            )
        }
    ) { innerPadding ->
        MainBottomNavHost(
            navController = bottomNavController,
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
            onNavOutEvent = onNavOutEvent
        )
    }
}

@Composable
private fun MainScreenBottomNavigation(
    currentDestination: NavDestination?,
    onNavInEvent: (route: String) -> Unit
) {
    Column {
        Divider(modifier = Modifier.fillMaxWidth(), color = Gray200, thickness = 0.5.dp)
        BottomNavigation(
            backgroundColor = MaterialTheme.colorScheme.background
        ) {
            MainDestination.screens.forEach { screen ->
                val isSelected = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true

                val color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onBackground
                }

                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = screen.icon),
                            contentDescription = screen.display.getValue(),
                            tint = color
                        )
                    },
                    selected = isSelected,
                    onClick = {
                        onNavInEvent(screen.route)
                    },
                    label = {
                        Text(
                            text = screen.display.getValue(),
                            style = MaterialTheme.typography.labelSmall,
                            color = color
                        )
                    }
                )
            }
        }
    }
}
