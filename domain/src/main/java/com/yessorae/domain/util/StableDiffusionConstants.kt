package com.yessorae.domain.util

object StableDiffusionConstants {
    // api url
    const val BASE_URL = "https://stablediffusionapi.com/"
    const val COMMUNITY_TEXT_TO_IMAGE_URL = "api/v4/dreambooth"
    const val COMMUNITY_FETCH_QUEUED_IMAGE_URL = "api/v4/dreambooth/fetch"
    const val GET_PUBLIC_MODEL_LIST_URL = "api/v4/dreambooth/model_list"
    const val IMAGE_EDITING_UPSCALE_URL = "api/v3/super_resolution"

    // response > status
    const val RESPONSE_SUCCESS = "success"
    const val RESPONSE_PROCESSING = "processing"
    const val RESPONSE_ERROR = "error"

    // argument's default/max/min ...
    const val INITIAL_GUIDANCE_SCALE = 10
    const val MAX_GUIDANCE_SCALE = 1
    const val MIN_GUIDANCE_SCALE = 20
    const val DEFAULT_SAMPLES = 1
    const val DEFAULT_LORA_STRENGTH = 1.0f
    const val INITIAL_STEP_COUNT = 20
    const val MIN_STEP_COUNT = 1
    const val MAX_STEP_COUNT = 50
    const val DEFAULT_WIDTH = 512
    const val DEFAULT_HEIGHT = 512
    const val DEFAULT_SAMPLE_COUNT = 1

    // argument's value
    const val ARG_YES = "yes"
    const val ARG_NO = "no"
    const val ARG_MODEL_TYPE_STABLE_DIFFUSION = "stable_diffusion"
    const val ARG_MODEL_TYPE_STABLE_DIFFUSION_XL = "stable_diffusion_xl"
    const val ARG_MODEL_TYPE_LORA = "lora"
    const val ARG_MODEL_TYPE_EMBEDDINGS = "embeddings"
    const val ARG_MODEL_TYPE_CONTROL_NET = "controlnet"
    const val PROMPT_SEPARATOR = ","

    // config
    const val MIN_FETCH_INTERVAL = 2000L
    const val DEFAULT_SAFE_CLICK_INTERVAL = 300L
    const val LIMIT_FEATURED_MODEL_LIST_COUNT = 12
    const val NONE_ID = 0
    const val DEFAULT_SAVED_IMAGE_NAME = "ImageFactory"
}

val stepCountRange = StableDiffusionConstants.MIN_STEP_COUNT..StableDiffusionConstants.MAX_STEP_COUNT