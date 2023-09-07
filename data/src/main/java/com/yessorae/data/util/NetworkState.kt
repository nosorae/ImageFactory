package com.yessorae.data.util


sealed class NetworkResult<T : Any> {
    object Loading: NetworkResult<Nothing>()
    class Success<T: Any>(val data: T) : NetworkResult<T>()
    class Error<T: Any>(val code: Int, val message: String?) : NetworkResult<T>()
    class Exception<T: Any>(val e: Throwable) : NetworkResult<T>()
}