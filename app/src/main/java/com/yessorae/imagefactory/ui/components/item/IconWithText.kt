package com.yessorae.imagefactory.ui.components.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import com.yessorae.imagefactory.ui.theme.Dimen
import com.yessorae.imagefactory.util.compose.Margin

@Composable
fun IconWithText(
    imageVector: ImageVector,
    text: String,
    onClick: () -> Unit = { }
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(Dimen.space_16)
            .clickable(
                onClick = onClick,
                role = Role.Button
            )
    ) {
        Icon(imageVector = imageVector, contentDescription = null)
        Margin(margin = Dimen.space_2)
        Text(text = text)
    }
}