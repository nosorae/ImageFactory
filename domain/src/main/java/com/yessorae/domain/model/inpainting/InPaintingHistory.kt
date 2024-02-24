package com.yessorae.domain.model.inpainting

import java.time.LocalDateTime

data class InPaintingHistory(
    var id: Int = 0,
    val request: InPaintingRequest,
    val result: InPaintingResult? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
)