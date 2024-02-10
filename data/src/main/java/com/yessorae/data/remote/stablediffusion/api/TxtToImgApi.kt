package com.yessorae.data.remote.stablediffusion.api

import com.yessorae.data.remote.stablediffusion.model.request.FetchQueuedImageRequestDto
import com.yessorae.data.remote.stablediffusion.model.request.TxtToImgRequestDto
import com.yessorae.data.remote.stablediffusion.model.response.FetchQueuedImgResponseDto
import com.yessorae.data.remote.stablediffusion.model.response.TxtToImgDto
import com.yessorae.domain.util.StableDiffusionApiConstants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TxtToImgApi {
    @POST(StableDiffusionApiConstants.COMMUNITY_TEXT_TO_IMAGE_URL)
    suspend fun generateImage(@Body request: TxtToImgRequestDto): Response<TxtToImgDto>

    @POST(StableDiffusionApiConstants.COMMUNITY_FETCH_QUEUED_IMAGE_URL)
    suspend fun fetchQueuedImage(@Body request: FetchQueuedImageRequestDto): Response<FetchQueuedImgResponseDto>
}
