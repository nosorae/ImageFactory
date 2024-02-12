package com.yessorae.data.remote.firebase.config.model

import com.yessorae.domain.model.parameter.EmbeddingsModel
import com.yessorae.domain.model.parameter.LoRaModel
import com.yessorae.domain.model.parameter.SDModel

data class Model(
    val id: String,
    val imgUrl: String,
    val displayName: String
)

fun Model.asSDModel(): SDModel {
    return SDModel(
        id = id,
        imgUrl = imgUrl,
        displayName = displayName
    )
}

fun Model.asLoRaModel(): LoRaModel {
    return LoRaModel(
        id = id,
        imgUrl = imgUrl,
        displayName = displayName
    )
}

fun Model.asEmbeddingsModel(): EmbeddingsModel {
    return EmbeddingsModel(
        id = id,
        imgUrl = imgUrl,
        displayName = displayName
    )
}
