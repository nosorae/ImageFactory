package com.yessorae.data.remote.stablediffusion.api

import com.yessorae.data.remote.stablediffusion.model.request.UpscaleRequestBody
import com.yessorae.data.remote.stablediffusion.model.response.UpscaleDto
import com.yessorae.data.util.StableDiffusionConstants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ImageEditingApi {
    @POST(StableDiffusionConstants.IMAGE_EDITING_UPSCALE_URL)
    suspend fun upscaleImage(
        @Body request: UpscaleRequestBody
    ): Response<UpscaleDto>
}
