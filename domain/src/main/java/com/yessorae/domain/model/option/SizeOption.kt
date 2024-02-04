package com.yessorae.domain.model.option

import com.yessorae.domain.model.type.SDSizeType
import com.yessorae.imagefactory.ui.components.item.model.Option
import com.yessorae.imagefactory.util.StringModel

data class SizeOption(
    val sizeType: SDSizeType,
    override val selected: Boolean
) : Option {
    override val title: StringModel = sizeType.title
    override val id: String = sizeType.name
    companion object
}

fun SizeOption.Companion.mock(): List<Option> {
    return SDSizeType.values().mapIndexed { index, sdSizeType ->
        SizeOption(
            sizeType = sdSizeType,
            selected = index == 0
        )
    }
}
