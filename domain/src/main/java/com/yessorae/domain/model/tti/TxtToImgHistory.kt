package com.yessorae.domain.model.tti

import java.time.LocalDateTime

data class TxtToImgHistory(
    var id: Int = 0,
    val request: TxtToImgRequest,
    val result: TxtToImgResult? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
)

