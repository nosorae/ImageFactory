package com.yessorae.presentation.ui.components.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.components.item.ImageFactoryTextField
import com.yessorae.presentation.ui.components.layout.option.FullModelsLayout
import com.yessorae.presentation.ui.screen.main.tti.model.ModelOption
import com.yessorae.presentation.ui.theme.Dimen
import com.yessorae.presentation.util.compose.Padding
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullModelOptionBottomSheet(
    title: String,
    options: List<ModelOption>,
    onCancelDialog: () -> Unit,
    onSelectModelOption: (ModelOption) -> Unit
) {
    val windowInsets = BottomSheetDefaults.windowInsets
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val focusManager = LocalFocusManager.current

    var value by remember { mutableStateOf("") }

    fun cancel() {
        scope.launch {
            bottomSheetState.hide()
            focusManager.clearFocus()
        }.invokeOnCompletion {
            onCancelDialog()
        }
    }

    ModalBottomSheet(
        onDismissRequest = {
            cancel()
        },
        sheetState = bottomSheetState,
        windowInsets = windowInsets,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = Dimen.bottom_sheet_top_padding)
    ) {
//        Column(
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(
//                        start = Dimen.side_padding,
//                        end = Dimen.space_8,
//                        bottom = Dimen.space_8
//                    ),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                IconButton(onClick = { cancel() }) {
//                    Icon(
//                        imageVector = Icons.Default.Close,
//                        contentDescription = null
//                    )
//                }
//
//                Padding(margin = Dimen.space_8)
//
//                ImageFactoryTextField(
//                    value = value,
//                    onValueChange = {
//                        value = it
//                    },
//                    placeholderText = stringResource(R.string.common_placeholder_search),
//                    modifier = Modifier.weight(1f)
//                )
//            }
//        }

        FullModelsLayout(
            modifier = Modifier.fillMaxWidth(),
            models = options,
            onClick = onSelectModelOption
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageAddOptionBottomSheet(
    onCancelDialog: () -> Unit,
    onSelectTakePicture: () -> Unit,
    onSelectPickImage: () -> Unit
) {
    val windowInsets = BottomSheetDefaults.windowInsets
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    val focusManager = LocalFocusManager.current

    fun cancel() {
        scope.launch {
            bottomSheetState.hide()
            focusManager.clearFocus()
        }.invokeOnCompletion {
            onCancelDialog()
        }
    }

    ModalBottomSheet(
        onDismissRequest = {
            cancel()
        },
        sheetState = bottomSheetState,
        windowInsets = windowInsets,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = Dimen.bottom_sheet_top_padding)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TextButton(onClick = onSelectTakePicture, modifier = Modifier.fillMaxWidth()) {
                Text(text = stringResource(id = R.string.common_add_image_from_camera))
            }

            TextButton(onClick = onSelectPickImage, modifier = Modifier.fillMaxWidth()) {
                Text(text = stringResource(id = R.string.common_add_image_from_album))
            }
        }
    }
}