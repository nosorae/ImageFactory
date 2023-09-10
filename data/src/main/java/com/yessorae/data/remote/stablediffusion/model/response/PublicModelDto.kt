package com.yessorae.data.remote.stablediffusion.model.response

import com.google.gson.annotations.SerializedName

class PublicModelDto : ArrayList<PublicModelItem>()

data class PublicModelItem(
    @SerializedName("model_id")
    val modelId: String, // midjourney
    @SerializedName("status")
    val status: String, // model_ready
    @SerializedName("created_at")
    val createdAt: Any?, // null
    @SerializedName("instance_prompt")
    val instancePrompt: String?, // mdjrny-v4 style
    @SerializedName("api_calls")
    val apiCalls: String?, // "1690106"
    @SerializedName("model_category")
    val modelCategory: String, // stable_diffusion
    @SerializedName("is_nsfw")
    val isNsfw: String, // yes || no
    @SerializedName("featured")
    val featured: String, // yes || no
    @SerializedName("model_name")
    val modelName: String, // MidJourney V4
    @SerializedName("description")
    val description: String, // Openjourney is an open source Stable Diffusion fine tuned model on Midjourney images, by PromptHero
    @SerializedName("screenshots")
    val screenshots: String // https://d1okzptojspljx.cloudfront.net/generations/14853540911669470514.png
)
