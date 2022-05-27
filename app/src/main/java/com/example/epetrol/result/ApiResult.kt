package com.example.epetrol.result

sealed class ApiResult<T> {
    data class Data<T>(val data: T) : ApiResult<T>()
    data class Error<T>(val msg: String) : ApiResult<T>()
}
