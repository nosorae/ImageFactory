package com.yessorae.imagefactory.ui.components.item

import androidx.compose.foundation.border
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yessorae.imagefactory.ui.theme.Gray300
import com.yessorae.imagefactory.ui.theme.Transparent
import com.yessorae.imagefactory.util.StringModel
import com.yessorae.imagefactory.util.TextString
import com.yessorae.imagefactory.util.compose.ColumnPreview

@Composable
fun ImageFactoryTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: StringModel,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.border(
            width = 1.dp,
            shape = MaterialTheme.shapes.medium,
            color = Gray300
        ),
        placeholder = {
            Text(
                text = placeholderText.getValue(),
                color = Gray300
            )
        },
        singleLine = true,
        trailingIcon = trailingIcon,
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Transparent,
            unfocusedIndicatorColor = Transparent
        ),
        keyboardOptions = keyboardOptions
    )
}

@Preview
@Composable
fun CustomPromptPreview() {
    var value by remember {
        mutableStateOf("")
    }
    ColumnPreview {
        ImageFactoryTextField(
            value = value,
            onValueChange = { value = it },
            placeholderText = TextString("Placeholder"),
            trailingIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }
        )
    }
}
