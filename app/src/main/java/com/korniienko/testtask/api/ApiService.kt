package com.korniienko.testtask.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("count") count: Int
    ): UserResponse

    @GET("positions")
    suspend fun getPositions(): PositionsResponse

    @GET("token")
    suspend fun getToken(): Token

    @Multipart
    @POST("users")
    suspend fun registerUser(
        @Header("Token") token: String,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("position_id") positionId: RequestBody,
        @Part photo: MultipartBody.Part
    ): Response<RegistrationResponse>
}