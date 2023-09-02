package com.yessorae.data.remote.api

import com.yessorae.data.remote.model.request.FetchQueuedImageRequest
import com.yessorae.data.remote.model.request.TxtToImgRequest
import com.yessorae.data.remote.model.response.FetchQueuedImageDto
import com.yessorae.data.remote.model.response.TxtToImgDto
import com.yessorae.data.util.RemoteConstants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TxtToImgApi {
    @POST(RemoteConstants.COMMUNITY_TEXT_TO_IMAGE_URL)
    suspend fun generateImage(@Body request: TxtToImgRequest): Response<TxtToImgDto>

    @POST(RemoteConstants.COMMUNITY_FETCH_QUEUED_IMAGE_URL)
    suspend fun fetchQueuedImage(@Body request: FetchQueuedImageRequest): Response<FetchQueuedImageDto>
}
