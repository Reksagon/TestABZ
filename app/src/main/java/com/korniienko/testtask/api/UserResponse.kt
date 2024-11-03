package com.korniienko.testtask.api

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_users") val totalUsers: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("users") val users: List<User>
)