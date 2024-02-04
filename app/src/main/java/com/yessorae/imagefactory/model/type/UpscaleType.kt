package com.yessorae.imagefactory.model.type

import com.yessorae.imagefactory.R
import com.yessorae.domain.model.option.Option
import com.yessorae.imagefactory.util.ResString
import com.yessorae.imagefactory.util.StringModel

enum class UpscaleType(val value: String) {
    None(value = "1"),
    Twice(value = "2"),
    Triple(value = "3");

    companion object {
        val defaultOptions: List<Option> = UpscaleType.values().mapIndexed { index, type ->
            object : Option {
                override val id: String = type.name
                override val title: StringModel =
                    ResString(R.string.common_n_value_upscale, type.value)
                override val selected: Boolean = index == 0
            }
        }

        fun createOptions(
            lastUpscaleNumber: Int? // API의 응답에 의해서 1, 2, 3
        ): List<Option> {
            return if (lastUpscaleNumber != null) {
                val lastUpscaleType = when (lastUpscaleNumber) {
                    1 -> None
                    2 -> Twice
                    else -> Triple
                }
                UpscaleType.values().map { type ->
                    object : Option {
                        override val id: String = type.name
                        override val title: StringModel =
                            ResString(R.string.common_n_value_upscale, type.value)
                        override val selected: Boolean = lastUpscaleType == type
                    }
                }
            } else {
                defaultOptions
            }
        }
    }
}

fun Option.toUpscaleType(): UpscaleType {
    return UpscaleType.valueOf(this.id)
}

fun UpscaleType.toOptionList(): List<Option> {
    return UpscaleType.values().mapIndexed { _, type ->
        object : Option {
            override val id: String = type.name
            override val title: StringModel =
                ResString(R.string.common_n_value_upscale, type.value)
            override val selected: Boolean = type.name == this@toOptionList.name
        }
    }
}
