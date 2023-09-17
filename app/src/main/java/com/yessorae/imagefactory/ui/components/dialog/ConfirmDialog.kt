package com.yessorae.imagefactory.ui.components.dialog

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.yessorae.imagefactory.R
import com.yessorae.imagefactory.ui.components.item.ActionButton
import com.yessorae.imagefactory.ui.components.item.OutlinedActionButton
import com.yessorae.imagefactory.ui.components.item.common.BaseDialog
import com.yessorae.imagefactory.ui.components.item.common.BaseDialogScreen
import com.yessorae.imagefactory.ui.theme.Dimen
import com.yessorae.imagefactory.util.ResString
import com.yessorae.imagefactory.util.StringModel
import com.yessorae.imagefactory.util.compose.Margin

@Composable
fun ConfirmDialog(
    title: StringModel,
    body: StringModel,
    confirmText: StringModel = ResString(R.string.common_dialog_button_confirm),
    cancelText: StringModel = ResString(R.string.common_dialog_button_no),
    onClickConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    BaseDialog(onDismissRequest = onCancel) {
        BaseDialogScreen {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title.getValue(),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            Margin(margin = Dimen.dialog_content_padding)
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = body.getValue(),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            Margin(margin = Dimen.dialog_bottom_padding)
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedActionButton(
                    modifier = Modifier.weight(1f),
                    text = cancelText.getValue(),
                    onClick = onCancel
                )
                Margin(margin = Dimen.dialog_content_padding)
                ActionButton(
                    modifier = Modifier.weight(1f),
                    text = confirmText.getValue(),
                    onClick = onClickConfirm
                )
            }
        }
    }
}