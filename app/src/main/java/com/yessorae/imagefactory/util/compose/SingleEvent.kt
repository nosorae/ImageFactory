package com.yessorae.imagefactory.util.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.yessorae.common.Constants

interface SingleEvent {
    fun processEvent(event: () -> Unit)

    companion object
}

internal fun SingleEvent.Companion.get(interval: Long = Constants.DEFAULT_SAFE_CLICK_INTERVAL): SingleEvent =
    SingleEventImpl(interval = interval)

class SingleEventImpl(private val interval: Long = Constants.DEFAULT_SAFE_CLICK_INTERVAL) :
    SingleEvent {
    private val now: Long
        get() = System.currentTimeMillis()

    private var lastEventTimeMs: Long = 0

    override fun processEvent(event: () -> Unit) {
        if (now - lastEventTimeMs >= interval) {
            event.invoke()
        }
        lastEventTimeMs = now
    }
}

@Composable
fun rememberDebouncedEvent(interval: Long = Constants.DEFAULT_SAFE_CLICK_INTERVAL): SingleEvent {
    return remember {
        SingleEvent.get(interval = interval)
    }
}
