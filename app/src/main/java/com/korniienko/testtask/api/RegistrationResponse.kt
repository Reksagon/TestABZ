package com.korniienko.testtask.api

import com.google.gson.annotations.SerializedName

data class RegistrationResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("user_id") val userId: Int?,
    @SerializedName("message") val message: String
)