package com.yessorae.data.api

import com.yessorae.data.util.DataConstants
import com.yessorae.data.model.request.TxtToImgRequest
import com.yessorae.data.model.response.TxtToImgResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TxtToImgApi {
    @POST(DataConstants.COMMUNITY_TEXT_TO_IMAGE_URL)
    suspend fun generateImage(@Body request: TxtToImgRequest): Response<TxtToImgResponse>
}