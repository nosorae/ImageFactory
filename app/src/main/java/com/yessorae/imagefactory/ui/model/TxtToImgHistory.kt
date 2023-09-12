package com.yessorae.imagefactory.ui.model

import com.yessorae.imagefactory.ui.model.TxtToImgRequest
import com.yessorae.imagefactory.ui.model.TxtToImgResult
import com.yessorae.imagefactory.ui.model.TxtToImgResultMetaData
import java.time.LocalDateTime

data class TxtToImgHistory(
    var id: Int = 0,
    val createdAt: LocalDateTime,
    val request: TxtToImgRequest,
    val meta: TxtToImgResultMetaData? = null,
    val result: TxtToImgResult? = null
)