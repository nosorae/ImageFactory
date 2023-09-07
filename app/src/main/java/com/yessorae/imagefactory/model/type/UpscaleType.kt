package com.yessorae.imagefactory.model.type

import com.yessorae.imagefactory.R
import com.yessorae.imagefactory.ui.components.item.model.Option
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