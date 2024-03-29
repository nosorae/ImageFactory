package com.yessorae.presentation.ui.components.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.theme.Dimen
import com.yessorae.presentation.util.compose.ColumnPreview
import com.yessorae.presentation.util.compose.rememberDebouncedEvent

@Composable
fun ActionButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    val singleEvent = rememberDebouncedEvent()
    Button(
        onClick = {
            singleEvent.processEvent(onClick)
        },
        colors = ButtonDefaults.buttonColors(),
        shape = MaterialTheme.shapes.medium,
        contentPadding = PaddingValues(horizontal = Dimen.space_16, vertical = Dimen.space_18),
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = Color.White
        )
    }
}

@Composable
fun ActionButtonWithAd(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    val singleEvent = rememberDebouncedEvent()
    Button(
        onClick = {
            singleEvent.processEvent {
                onClick()
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(horizontal = Dimen.space_16, vertical = Dimen.space_18),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = stringResource(id = R.string.common_watch_ad),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun OutlinedActionButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    val singleEvent = rememberDebouncedEvent()
    OutlinedButton(
        onClick = {
            singleEvent.processEvent {
                onClick()
            }
        },
        colors = ButtonDefaults.outlinedButtonColors(),
        contentPadding = PaddingValues(horizontal = Dimen.space_16, vertical = Dimen.space_18),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview
@Composable
fun ButtonPreviews() {
    var maxHeight by remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current
    ColumnPreview(spacedBy = Dimen.space_8) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimen.space_12)
        ) {
            OutlinedActionButton(
                text = "사진 선택 하기",
                onClick = {},
                modifier = Modifier
                    .weight(1f)
                    .height(maxHeight)
            )
            ActionButtonWithAd(
                text = "화질 개선 하기",
                onClick = {},
                modifier = Modifier
                    .weight(1f)
                    .onGloballyPositioned {
                        maxHeight = with(density) {
                            it.size.height.toDp()
                        }
                    }
            )
        }
    }
}
