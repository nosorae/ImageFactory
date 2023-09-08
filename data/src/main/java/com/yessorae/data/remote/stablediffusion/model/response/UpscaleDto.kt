package com.yessorae.data.remote.stablediffusion.model.response


import com.google.gson.annotations.SerializedName

data class UpscaleDto(
    @SerializedName("status")
    val status: String, // success
    @SerializedName("generationTime")
    val generationTime: Double, // 12.782328367233276
    @SerializedName("id")
    val id: Int, // 2912600
    @SerializedName("output")
    val output: String // https://pub-8b49af329fa499aa563997f5d4068a4.r2.dev/generations/1675767485_out.png
)