package com.korniienko.testtask.api

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("success") val success: Boolean,
    @SerializedName("token") val token: String
)