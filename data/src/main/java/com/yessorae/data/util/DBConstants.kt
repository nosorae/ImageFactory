package com.yessorae.data.util

object DBConstants {
    const val DATABASE_NAME = "image_factory_database"

    // prompt
    const val TABLE_PROMPT = "prompt_table"
    const val COL_PROMPT_ID = "prompt_id"
    const val COL_PROMPT = "prompt"
    const val COLPOSITIVE = "positive"
    const val COL_SELECT_COUNT = "select_count"
    const val COL_UPDATED_AT = "updated_at"
    const val COL_NSFW = "nsfw"

    // public model
    const val TABLE_PUBLIC_MODEL = "public_model_table"
    const val COL_INSTANCE_PROMPT = "instance_prompt"
    const val COL_API_CALLS = "api_calls"
    const val COL_MODEL_CATEGORY = "model_category"
    const val COL_IS_NSFW = "is_nsfw"
    const val COL_FEATURED = "featured"
    const val COL_MODEL_NAME = "model_name"
    const val COL_DESCRIPTION = "description"
    const val COL_SCREENSHOTS = "screenshots"

    // models
    const val TABLE_SD_MODEL = "sd_model"
    const val TABLE_LORA_MODEL = "lora_model"
    const val TABLE_EMBEDDINGS_MODEL = "embeddings_model"
    const val COL_IMG_URL = "img_url"
    const val COL_DISPLAY_NAME = "display_name"
    const val COL_USED_AT = "used_at"

    // Request/Response History
    const val TABLE_TXT_TO_IMG_HISTORY = "txt_to_img_history"
    const val TABLE_IN_PAINTING_HISTORY = "in_painting_history"
    const val PREFIX_REQUEST = "request_"
    const val PREFIX_RESULT = "result_"
    const val PREFIX_META = "meta_"

    // Txt to Img Upscale History Model
    const val TABLE_UPSCALE_HISTORY = "upscale_history"
    const val COL_HISTORY_ID = "history_id"

    // common
    const val COL_ID = "id"
    const val COL_CREATED_AT = "created_at"
    const val COL_MODEL_ID = "model_id"
    const val COL_STATUS = "status"
    const val COL_FILE_PATH = "file_path"
    const val COL_SERVER_SYNC = "server_sync"
}
