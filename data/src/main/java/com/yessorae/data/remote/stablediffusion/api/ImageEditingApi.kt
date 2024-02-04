package com.yessorae.data.remote.stablediffusion.api

import com.yessorae.data.remote.stablediffusion.model.request.UpscaleRequestDto
import com.yessorae.data.remote.stablediffusion.model.response.UpscaleResultDto
import com.yessorae.domain.util.StableDiffusionApiConstants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ImageEditingApi {
    @POST(StableDiffusionApiConstants.IMAGE_EDITING_UPSCALE_URL)
    suspend fun upscaleImage(
        @Body request: UpscaleRequestDto
    ): Response<UpscaleResultDto>
}
