package com.yessorae.data.remote.stablediffusion.api

import com.yessorae.data.BuildConfig
import com.yessorae.data.remote.stablediffusion.model.request.KeyRequest
import com.yessorae.data.remote.stablediffusion.model.response.PublicModelDto
import com.yessorae.data.util.StableDiffusionConstants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ModelListApi {
    @POST(StableDiffusionConstants.GET_PUBLIC_MODEL_LIST_URL)
    suspend fun getPublicModels(
        @Body key: KeyRequest = KeyRequest(BuildConfig.STABLE_DIFFUSION_API_API_KEY)
    ): Response<PublicModelDto>
}

