package com.yessorae.data.remote.stablediffusion.api

import com.yessorae.data.remote.stablediffusion.model.request.KeyRequestDto
import com.yessorae.data.remote.stablediffusion.model.response.PublicModelDto
import com.yessorae.domain.util.StableDiffusionApiConstants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ModelListApi {
    @POST(StableDiffusionApiConstants.GET_PUBLIC_MODEL_LIST_URL)
    suspend fun getPublicModels(
        @Body key: KeyRequestDto = KeyRequestDto()
    ): Response<PublicModelDto>
}
