package com.yessorae.imagefactory.model.type

import com.yessorae.imagefactory.ui.components.item.model.Option
import com.yessorae.imagefactory.util.StringModel
import com.yessorae.imagefactory.util.TextString

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
    );

    companion object {
        val defaultOptions: List<Option> = SDSizeType.values().mapIndexed { index, type ->
            object : Option {
                override val id: String = type.name
                override val title: StringModel = type.title
                override val selected: Boolean = index == 0
            }
        }
    }
}

fun Option.toSDSizeType(): SDSizeType {
    return SDSizeType.valueOf(this.id)
}

fun SDSizeType.toOptionList(): List<Option> {
    return SDSizeType.values().mapIndexed { _, type ->
        object : Option {
            override val id: String = type.name
            override val title: StringModel = type.title
            override val selected: Boolean = type.name == this@toOptionList.name
        }
    }
}
