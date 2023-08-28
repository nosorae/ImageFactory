package com.yessorae.imagefactory.model.type

import com.yessorae.imagefactory.ui.util.StringModel
import com.yessorae.imagefactory.ui.util.TextString

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
