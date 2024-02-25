package com.yessorae.data.remote.stablediffusion.api

import com.yessorae.data.remote.stablediffusion.model.request.FetchQueuedImageRequestDto
import com.yessorae.data.remote.stablediffusion.model.request.InPaintingRequestDto
import com.yessorae.data.remote.stablediffusion.model.request.TxtToImgRequestDto
import com.yessorae.data.remote.stablediffusion.model.response.FetchQueuedImgResponseDto
import com.yessorae.data.remote.stablediffusion.model.response.InPaintingResponseDto
import com.yessorae.data.remote.stablediffusion.model.response.TxtToImgResponseDto
import com.yessorae.domain.util.StableDiffusionConstants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface StableDiffusionApi {
    @POST(StableDiffusionConstants.COMMUNITY_TEXT_TO_IMAGE_URL)
    suspend fun generateImage(@Body request: TxtToImgRequestDto): Response<TxtToImgResponseDto>

    @POST(StableDiffusionConstants.COMMUNITY_IN_PAINTING_IMAGE_URL)
    suspend fun generateImage(@Body request: InPaintingRequestDto): Response<InPaintingResponseDto>

    @POST(StableDiffusionConstants.COMMUNITY_FETCH_QUEUED_IMAGE_URL)
    suspend fun fetchQueuedImage(@Body request: FetchQueuedImageRequestDto): Response<FetchQueuedImgResponseDto>
}
