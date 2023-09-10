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

    // public model
    const val TABLE_PUBLIC_MODEL = "public_model_table"
    const val COL_MODEL_ID = "model_id"
    const val COL_STATUS = "status"
    const val COL_INSTANCE_PROMPT = "instance_prompt"
    const val COL_API_CALLS = "api_calls"
    const val COL_MODEL_CATEGORY  = "model_category"
    const val COL_IS_NSFW = "is_nsfw"
    const val COL_FEATURED = "featured"
    const val COL_MODEL_NAME = "model_name"
    const val COL_DESCRIPTION = "description"
    const val COL_SCREENSHOTS = "screenshots"

    // common
    const val COL_CREATED_AT = "created_at"
}
