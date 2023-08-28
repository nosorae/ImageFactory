package com.yessorae.imagefactory.model

import com.yessorae.imagefactory.model.type.SDSizeType
import com.yessorae.imagefactory.ui.components.item.model.Option
import com.yessorae.imagefactory.ui.util.StringModel

data class SizeOption(
    val sizeType: SDSizeType,
    override val title: StringModel,
    override val selected: Boolean
) : Option {
    companion object
}

fun SizeOption.Companion.mock(): List<Option> {
    return SDSizeType.values().mapIndexed { index, sdSizeType ->
        SizeOption(
            sizeType = sdSizeType,
            title = sdSizeType.title,
            selected = index == 0
        )
    }
}