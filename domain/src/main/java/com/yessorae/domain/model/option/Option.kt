package com.yessorae.domain.model.option

interface Option {
    val id: String // todo 제거 고민, 화면 그리는 용도는 아니지만 옵션 선택 시 id가 필수인 경우 많음
    val title: String
    val selected: Boolean
}

fun List<Option>.getSelectedOption(): Option? {
    return this.find { it.selected }
}
