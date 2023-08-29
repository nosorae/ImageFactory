package com.yessorae.imagefactory.ui.components.item.model

import com.yessorae.imagefactory.ui.util.StringModel

interface Option {
    val id: String
    val title: StringModel
    val selected: Boolean
}

fun List<Option>.getSelectedOption(): Option? {
    return this.find { it.selected }
}