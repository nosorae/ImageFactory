package com.yessorae.imagefactory.ui.components.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.yessorae.imagefactory.R
import com.yessorae.imagefactory.ui.components.item.ActionButton
import com.yessorae.imagefactory.ui.components.item.ImageFactoryTextField
import com.yessorae.imagefactory.ui.components.item.common.BaseDialog
import com.yessorae.imagefactory.ui.components.item.common.BaseDialogScreen
import com.yessorae.imagefactory.ui.theme.Dimen
import com.yessorae.imagefactory.ui.util.ResString
import com.yessorae.imagefactory.ui.util.StringModel
import com.yessorae.imagefactory.ui.util.compose.Margin

@Composable
fun InputDialog(
    onDismissRequest: () -> Unit,
    onClickAddButton: (String) -> Unit,
    placeholderText: StringModel = ResString(R.string.common_input_prompt),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    var text by remember { mutableStateOf("") }
    BaseDialog(onDismissRequest = onDismissRequest) {
        BaseDialogScreen {
            ImageFactoryTextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = { text = it },
                placeholderText = placeholderText,
                keyboardOptions = keyboardOptions
            )

            Margin(margin = Dimen.dialog_content_button_padding)

            ActionButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.common_add),
                onClick = {
                    onClickAddButton(text)
                }
            )
        }
    }
}

@Preview
@Composable
fun CustomDialogPromptPreview() {
    InputDialog(
        onDismissRequest = {},
        onClickAddButton = {}
    )
}
