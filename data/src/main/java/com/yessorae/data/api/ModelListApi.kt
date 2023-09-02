package com.yessorae.data.api

import com.yessorae.data.BuildConfig
import com.yessorae.data.model.request.KeyRequest
import com.yessorae.data.model.response.PublicModelDto
import com.yessorae.data.util.DataConstants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ModelListApi {
    @POST(DataConstants.GET_PUBLIC_MODEL_LIST_URL)
    suspend fun getPublicModels(
        @Body key: KeyRequest = KeyRequest(BuildConfig.STABLE_DIFFUSION_API_API_KEY)
    ): Response<PublicModelDto>
}
