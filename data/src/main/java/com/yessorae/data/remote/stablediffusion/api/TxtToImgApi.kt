package com.yessorae.data.remote.stablediffusion.api

import com.yessorae.data.remote.stablediffusion.model.request.FetchQueuedImageRequest
import com.yessorae.data.remote.stablediffusion.model.request.TxtToImgRequest
import com.yessorae.data.remote.stablediffusion.model.response.FetchQueuedImageDto
import com.yessorae.data.remote.stablediffusion.model.response.TxtToImgDto
import com.yessorae.data.util.StableDiffusionConstants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TxtToImgApi {
    @POST(StableDiffusionConstants.COMMUNITY_TEXT_TO_IMAGE_URL)
    suspend fun generateImage(@Body request: TxtToImgRequest): Response<TxtToImgDto>

    @POST(StableDiffusionConstants.COMMUNITY_FETCH_QUEUED_IMAGE_URL)
    suspend fun fetchQueuedImage(@Body request: FetchQueuedImageRequest): Response<FetchQueuedImageDto>
}
