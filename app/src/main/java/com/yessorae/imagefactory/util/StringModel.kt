package com.yessorae.imagefactory.util

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

sealed class StringModel {
    fun get(): String {
        return when (this) {
            is TextString -> text.format(args)
            else -> ""
        }
    }

    fun get(context: Context): String {
        try {
            return when (this) {
                is TextString ->
                    String.format(
                        text,
                        *args
                    )

                is ResString -> {
                    String.format(
                        context.getString(resId),
                        *args
                    )
                }
            }
        } catch (e: Throwable) {
            return ""
        }
    }

    @Composable
    fun getValue(): String {
        return get(LocalContext.current)
    }
}

class ResString(
    val resId: Int,
    vararg val args: Any?
) : StringModel()

class TextString(
    val text: String,
    vararg val args: Any?
) : StringModel()
