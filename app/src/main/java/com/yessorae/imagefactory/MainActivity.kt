package com.yessorae.imagefactory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.yessorae.imagefactory.ui.screen.ImageFactoryAppScreen
import com.yessorae.imagefactory.ui.screen.main.tti.TxtToImgScreen
import com.yessorae.imagefactory.ui.theme.ImageFactoryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageFactoryAppScreen()
        }
    }
}
