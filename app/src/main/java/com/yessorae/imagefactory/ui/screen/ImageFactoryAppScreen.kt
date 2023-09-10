package com.yessorae.imagefactory.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.yessorae.imagefactory.ui.navigation.navhost.ImageFactoryNavHost
import com.yessorae.imagefactory.ui.theme.ImageFactoryTheme

@Composable
fun ImageFactoryAppScreen() {
    ImageFactoryTheme {
        val navController = rememberNavController()

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ImageFactoryNavHost(
                navController = navController
            )
        }
    }
}
