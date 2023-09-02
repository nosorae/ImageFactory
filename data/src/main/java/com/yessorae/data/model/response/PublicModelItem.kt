package com.yessorae.data.model.response

import com.google.gson.annotations.SerializedName

data class PublicModelItem(
    @SerializedName("model_id")
    val modelId: String, // midjourney
    @SerializedName("status")
    val status: String, // model_ready
    @SerializedName("created_at")
    val createdAt: Any?, // null
    @SerializedName("instance_prompt")
    val instancePrompt: String?, // mdjrny-v4 style
    @SerializedName("model_name")
    val modelName: String, // MidJourney V4
    @SerializedName("description")
    val description: String, // Openjourney is an open source Stable Diffusion fine tuned model on Midjourney images, by PromptHero
    @SerializedName("screenshots")
    val screenshots: String // https://d1okzptojspljx.cloudfront.net/generations/14853540911669470514.png
)
