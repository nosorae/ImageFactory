package com.yessorae.imagefactory.ui.components.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yessorae.imagefactory.ui.theme.Dimen
import com.yessorae.imagefactory.ui.util.compose.ColumnPreview
import com.yessorae.imagefactory.ui.util.compose.ThemePreview
import kotlin.math.roundToInt

@Composable
fun NaturalNumberSliderOptionLayout(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    valueRange: IntRange,
) {
    require(valueRange.first >= 0) { "first should be >= 0" }
    require(valueRange.last >= 0) { "last should be >= 0" }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = Dimen.space_4, bottom = Dimen.space_12)
    ) {
        Text(
            text = "$value",
            modifier = Modifier.padding(start = Dimen.space_16)
        )

        Slider(
            value = value.toFloat(),
            onValueChange = {
                onValueChange(it.roundToInt())
            },
            valueRange = valueRange.first.toFloat()..valueRange.last.toFloat(),
            steps = valueRange.last - 1,
            modifier = Modifier.padding(horizontal = Dimen.space_8)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimen.space_16),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "${valueRange.first}", style = MaterialTheme.typography.labelSmall)
            Text(text = "${valueRange.last}", style = MaterialTheme.typography.labelSmall)
        }
    }

}

@Composable
fun ZeroToOneSliderOptionLayout(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    require(value in 0f..1f)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = Dimen.space_4, bottom = Dimen.space_12)
    ) {
        Text(
            text = ((value * 10).roundToInt() / 10f).toString(),
            modifier = Modifier.padding(start = Dimen.space_16)
        )
        Slider(
            value = value,
            onValueChange = { onValueChange(it) },
            valueRange = 0f..1.0f,
            steps = 9,
            modifier = Modifier.padding(horizontal = Dimen.space_8)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimen.space_16),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "0.0", style = MaterialTheme.typography.labelSmall)
            Text(text = "0.5", style = MaterialTheme.typography.labelSmall)
            Text(text = "1.0", style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Preview
@Composable
fun ZeroToOneSliderOptionPreview() {
    var value by remember {
        mutableStateOf(0.5f)
    }
    ThemePreview {
        ZeroToOneSliderOptionLayout(
            value = value,
            onValueChange = {
                value = it
            }
        )
    }
}

@Preview
@Composable
fun IntegerSliderOptionPreview() {
    var value1 by remember {
        mutableStateOf(5)
    }
    var value2 by remember {
        mutableStateOf(5)
    }
    ColumnPreview {
        NaturalNumberSliderOptionLayout(
            value = value1,
            onValueChange = {
                value1 = it
            },
            valueRange = 0..10
        )
        Divider()
        NaturalNumberSliderOptionLayout(
            value = value2,
            onValueChange = {
                value2 = it
            },
            valueRange = 100..1000
        )
    }
}