package com.yessorae.presentation.util.compose

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.semantics.Role
import com.yessorae.domain.util.StableDiffusionConstants

fun Modifier.debouncedClickable(
    interval: Long = StableDiffusionConstants.DEFAULT_SAFE_CLICK_INTERVAL,
    enabled: Boolean = true,
    role: Role? = null,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    indication: Indication? = null,
    onClick: () -> Unit
): Modifier = composed {
    val singleEvent = remember { SingleEvent.get(interval = interval) }
    clickable(
        onClick = {
            singleEvent.processEvent(
                event = onClick
            )
        },
        enabled = enabled,
        role = role,
        interactionSource = interactionSource,
        indication = indication
    )
}

fun Modifier.debouncedClickable(
    enabled: Boolean = true,
    interval: Long = StableDiffusionConstants.DEFAULT_SAFE_CLICK_INTERVAL,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
): Modifier = composed {
    val singleEvent = rememberDebouncedEvent(interval = interval)
    clickable(
        onClick = {
            singleEvent.processEvent(
                event = onClick
            )
        },
        enabled = enabled,
        role = role,
        onClickLabel = onClickLabel
    )
}
