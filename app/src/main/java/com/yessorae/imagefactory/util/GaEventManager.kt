package com.yessorae.imagefactory.util

import android.os.Bundle
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.yessorae.common.Logger.recordException

object GaEventManager {
    fun event(
        event: String,
        vararg params: Pair<String, Any?>
    ) {
        try {
            Bundle().apply {
                params.forEach { pair ->
                    when (val value = pair.second) {
                        is Int -> {
                            putInt(pair.first, value)
                        }

                        is Boolean -> {
                            putBoolean(pair.first, value)
                        }

                        else -> {
                            (value as? String)?.let {
                                putString(pair.first, value)
                            } ?: run {
                                putString(
                                    pair.first,
                                    value.toString()
                                )
                            }
                        }
                    }
                }
            }.also { bundle ->
                Firebase.analytics.logEvent(event, bundle)
            }
        } catch (e: Exception) {
            recordException(e)
        }
    }
}
