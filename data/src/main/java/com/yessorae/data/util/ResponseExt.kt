package com.yessorae.data.util

import com.google.gson.Gson
import retrofit2.Response

fun <T> Response<T>.handleResponse(): T {
    errorBody()?.let {
        val json = it.string()
        val errorDto = Gson().fromJson(json, ErrorDto::class.java)
        throw ServerException(errorDto.toString())
    } ?: run {
        return body()!!
    }
}

class ServerException(message: String) : Exception(message)
data class ErrorDto(
    val code: String,
    val message: String
)