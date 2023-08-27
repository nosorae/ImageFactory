package com.yessorae.imagefactory.model

import com.yessorae.imagefactory.ui.components.item.model.Option
import com.yessorae.imagefactory.ui.util.StringModel
import com.yessorae.imagefactory.ui.util.TextString

data class SizeOption(
    val sizeType: SDSizeType,
    override val text: StringModel,
    override val selected: Boolean
) : Option {
    companion object
}

enum class SDSizeType(val title: StringModel, val width: Int, val height: Int) {
    Square(
        title = TextString("1:1"),
        width = 512,
        height = 512
    ),
    Portrait(
        title = TextString("2:3"),
        width = 512,
        height = 768
    ),
    Landscape(
        title = TextString("3:2"),
        width = 768,
        height = 512
    ),
}

fun SizeOption.Companion.mock(): List<Option> {
    return SDSizeType.values().mapIndexed { index, sdSizeType ->
        SizeOption(
            sizeType = sdSizeType,
            text = sdSizeType.title,
            selected = index == 0
        )
    }
}