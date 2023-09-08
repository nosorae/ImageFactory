package com.yessorae.imagefactory.ui.components.item.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.yessorae.imagefactory.R
import com.yessorae.imagefactory.ui.theme.Dimen
import com.yessorae.imagefactory.ui.theme.Gray400

@Composable
fun ImageLoadError(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(color = Gray400, shape = MaterialTheme.shapes.medium),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.common_error_not_watch_image_your_country),
            textAlign = TextAlign.Center
        )
    }
}

