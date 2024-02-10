package com.yessorae.domain.model

import java.time.LocalDateTime

data class TxtToImgHistory(
    var id: Int = 0,
    val createdAt: LocalDateTime,
    val request: TxtToImgRequest,
    val result: TxtToImgResult? = null
)
