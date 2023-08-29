package com.yessorae.data.repository

import com.yessorae.data.api.TxtToImgApi
import com.yessorae.data.model.request.TxtToImgRequest
import com.yessorae.data.model.response.TxtToImgDto
import com.yessorae.data.util.handleResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TxtToImgRepository @Inject constructor(
    private val txtToImgApi: TxtToImgApi
) {
    suspend fun generateImage(
        request: TxtToImgRequest
    ): TxtToImgDto {
        return txtToImgApi.generateImage(request).handleResponse()
    }
}
