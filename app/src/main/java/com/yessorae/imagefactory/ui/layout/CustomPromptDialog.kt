package com.yessorae.imagefactory.ui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.yessorae.imagefactory.R
import com.yessorae.imagefactory.ui.component.ActionButton
import com.yessorae.imagefactory.ui.component.CustomPrompt
import com.yessorae.imagefactory.ui.component.common.BaseDialog
import com.yessorae.imagefactory.ui.component.common.BaseDialogScreen
import com.yessorae.imagefactory.ui.theme.Dimen
import com.yessorae.imagefactory.ui.util.ResString
import com.yessorae.imagefactory.ui.util.compose.Margin

@Composable
fun CustomPromptDialog(
    onDismissRequest: () -> Unit,
    onClickAddButton: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    BaseDialog(onDismissRequest = onDismissRequest) {
        BaseDialogScreen {
            CustomPrompt(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = { text = it },
                placeholderText = ResString(R.string.common_input_prompt)
            )

            Margin(margin =Dimen.dialog_content_button_padding)

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
    CustomPromptDialog(
        onDismissRequest = {},
        onClickAddButton = {}
    )
}