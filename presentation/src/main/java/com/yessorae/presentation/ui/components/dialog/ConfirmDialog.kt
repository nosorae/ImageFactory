package com.yessorae.presentation.ui.components.dialog

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.components.item.ActionButton
import com.yessorae.presentation.ui.components.item.OutlinedActionButton
import com.yessorae.presentation.ui.components.item.common.BaseDialog
import com.yessorae.presentation.ui.components.item.common.BaseDialogScreen
import com.yessorae.presentation.ui.theme.Dimen
import com.yessorae.presentation.util.compose.Padding

@Composable
fun ConfirmDialog(
    title: String,
    body: String = "",
    confirmText: String = stringResource(R.string.common_dialog_button_confirm),
    cancelText: String = stringResource(R.string.common_dialog_button_no),
    onClickConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    BaseDialog(onDismissRequest = onCancel) {
        BaseDialogScreen {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )

            if (body.isNotEmpty()) {
                Padding(margin = Dimen.dialog_content_padding)
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = body,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }

            Padding(margin = Dimen.dialog_bottom_padding)
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedActionButton(
                    modifier = Modifier.weight(1f),
                    text = cancelText,
                    onClick = onCancel
                )
                Padding(margin = Dimen.dialog_content_padding)
                ActionButton(
                    modifier = Modifier.weight(1f),
                    text = confirmText,
                    onClick = onClickConfirm
                )
            }
        }
    }
}
