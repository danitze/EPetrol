package com.example.epetrol.result

sealed class NullableResult<T> {
    data class Data<T>(val data: T) : NullableResult<T>()
    data class Error<T>(val msg: String) : NullableResult<T>()
    class Null<T> : NullableResult<T>()

    inline fun onData(block: (data: T) -> Unit): NullableResult<T> {
        if(this is Data) {
            block.invoke(this.data)
        }
        return this
    }

    inline fun onError(block: (msg: String) -> Unit): NullableResult<T> {
        if(this is Error) {
            block.invoke(this.msg)
        }
        return this
    }

    inline fun onNull(block: () -> Unit): NullableResult<T> {
        if(this is Null) {
            block.invoke()
        }
        return this
    }

    suspend inline fun onAsyncData(block: suspend (data: T) -> Unit): NullableResult<T> {
        if(this is Data) {
            block.invoke(this.data)
        }
        return this
    }

    suspend inline fun onAsyncError(block: suspend (msg: String) -> Unit): NullableResult<T> {
        if(this is Error) {
            block.invoke(this.msg)
        }
        return this
    }

    suspend inline fun onAsyncNull(block: suspend () -> Unit): NullableResult<T> {
        if(this is Null) {
            block.invoke()
        }
        return this
    }
}
