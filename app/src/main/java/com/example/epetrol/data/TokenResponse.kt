package com.example.epetrol.data

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("token")
    val token: String
)
