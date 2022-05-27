package com.example.epetrol.result

sealed class AdminAreaResult {
    data class Success(val adminArea: String) : AdminAreaResult()
    object Null : AdminAreaResult()
    data class Error(val msg: String) : AdminAreaResult()
}
