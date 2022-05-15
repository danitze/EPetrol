package com.example.epetrol.data

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String
)
