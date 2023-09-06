package com.yessorae.common

import android.os.Bundle

object GaEventManager {
    fun event(
        event: String,
        vararg params: Pair<String, Any?>
    ) {
//        debugEvent(event, *params)
//        try {
//            Bundle().apply {
//                params.forEach { pair ->
//                    when (val value = pair.second) {
//                        is Int -> {
//                            putInt(pair.first, value)
//                        }
//
//                        is Boolean -> {
//                            putBoolean(pair.first, value)
//                        }
//
//                        else -> {
//                            (value as? String)?.let {
//                                putString(pair.first, value)
//                            } ?: run {
//                                putString(
//                                    pair.first,
//                                    value.toString()
//                                )
//                            }
//                        }
//                    }
//                }
//            }.also { bundle ->
//                Firebase.analytics.logEvent(event, bundle)
//            }
//        } catch (e: Exception) {
//            recordException(e)
//        }
    }
}