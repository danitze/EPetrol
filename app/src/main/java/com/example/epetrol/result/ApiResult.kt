package com.example.epetrol.result

sealed class ApiResult<T> {
    data class Data<T>(val data: T) : ApiResult<T>()
    data class Error<T>(val msg: String) : ApiResult<T>()

    fun onData(block: (data: T) -> Unit): ApiResult<T> {
        if(this is Data) {
            block.invoke(this.data)
        }
        return this
    }

    fun onError(block: (msg: String) -> Unit): ApiResult<T> {
        if(this is Error) {
            block.invoke(this.msg)
        }
        return this
    }

    suspend fun onAsyncData(block: suspend (data: T) -> Unit): ApiResult<T> {
        if(this is Data) {
            block.invoke(this.data)
        }
        return this
    }

    suspend fun onAsyncError(block: suspend (msg: String) -> Unit): ApiResult<T> {
        if(this is Error) {
            block.invoke(this.msg)
        }
        return this
    }
}
