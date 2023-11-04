package com.yessorae.data.util

object StableDiffusionApiConstants {
    const val BASE_URL = "https://stablediffusionapi.com/"
    const val COMMUNITY_TEXT_TO_IMAGE_URL = "api/v4/dreambooth"
    const val COMMUNITY_FETCH_QUEUED_IMAGE_URL = "api/v4/dreambooth/fetch"
    const val GET_PUBLIC_MODEL_LIST_URL = "api/v4/dreambooth/model_list"
    const val IMAGE_EDITING_UPSCALE_URL = "api/v3/super_resolution"

    const val RESPONSE_SUCCESS = "success"
    const val RESPONSE_PROCESSING = "processing"
    const val RESPONSE_ERROR = "error"
    const val SD_API_CALL_STATE_SUCCESS = "success"
}
