package com.yessorae.domain.model

data class PublicModel(
    val modelId: String, // midjourney
    val status: String, // model_ready
    val createdAt: String?, // null
    val instancePrompt: String?, // mdjrny-v4 style
    val apiCalls: Long?, // "1690106"
    val modelCategory: String, // stable_diffusion
    val isNsfw: Boolean?, // yes || no
    val featured: Boolean?, // yes || no
    val modelName: String, // MidJourney V4
    val description: String, // Openjourney is an open source Stable Diffusion fine tuned model on Midjourney images, by PromptHero
    val screenshots: String, // https://d1okzptojspljx.cloudfront.net/generations/14853540911669470514.png
    val serverSync: Boolean = false
)