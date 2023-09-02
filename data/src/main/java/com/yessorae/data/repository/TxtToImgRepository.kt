package com.yessorae.data.repository

import com.yessorae.data.remote.api.ModelListApi
import com.yessorae.data.remote.api.TxtToImgApi
import com.yessorae.data.remote.model.request.TxtToImgRequest
import com.yessorae.data.remote.model.response.PublicModelDto
import com.yessorae.data.remote.model.response.TxtToImgDto
import com.yessorae.data.util.handleResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TxtToImgRepository @Inject constructor(
    private val txtToImgApi: TxtToImgApi,
    private val modelListApi: ModelListApi
) {
    suspend fun generateImage(
        request: TxtToImgRequest
    ): TxtToImgDto {
        return txtToImgApi.generateImage(request).handleResponse()
    }

    suspend fun getPublicModels(usingCache: Boolean = true): PublicModelDto {
        return modelListApi.getPublicModels().handleResponse()
    }
}
