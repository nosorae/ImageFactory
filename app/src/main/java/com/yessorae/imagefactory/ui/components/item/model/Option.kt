package com.yessorae.imagefactory.ui.components.item.model

import com.yessorae.imagefactory.ui.util.StringModel

interface Option {
    val id: String // todo 제거 고민, 화면 그리는 용도는 아니지만 옵션 선택 시 id가 필수인 경우 많음
    val title: StringModel
    val selected: Boolean
}

fun List<Option>.getSelectedOption(): Option? {
    return this.find { it.selected }
}
