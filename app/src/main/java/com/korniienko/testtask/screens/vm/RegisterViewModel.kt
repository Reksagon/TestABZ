package com.korniienko.testtask.screens.vm

import android.util.Base64
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.korniienko.testtask.api.Position
import com.korniienko.testtask.api.RegistrationResponse
import com.korniienko.testtask.api.RetrofitInstance
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.File
import java.io.IOException

class RegisterViewModel : ViewModel() {
    private val apiService = RetrofitInstance.api
    var positions by mutableStateOf<List<Position>>(emptyList())
    var selectedPosition by mutableStateOf<Position?>(null)
    var isLoading by mutableStateOf(true)
    var errorMessage by mutableStateOf("")

    init {
        fetchPositions()
    }

    private fun fetchPositions() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = ""
            try {
                val response = RetrofitInstance.api.getPositions()
                if (response.success) {
                    positions = response.positions
                } else {
                    errorMessage = "Failed to load positions"
                }
            } catch (e: IOException) {
                errorMessage = "Network error. Please check your connection."
            } catch (e: HttpException) {
                errorMessage = "Server error. Please try again later."
            } finally {
                isLoading = false
            }
        }
    }

    fun onPositionSelected(position: Position) {
        selectedPosition = position
    }

    suspend fun registerUser(
        name: String,
        email: String,
        phone: String,
        positionId: Int,
        base64Image: String
    ): Response<RegistrationResponse>? {
        try {
            // Отримання токену
            val tokenResponse = apiService.getToken()
            if (!tokenResponse.success) {
                throw Exception("Token request failed")
            }
            // Створення частин запиту
            val namePart = name.toRequestBody("text/plain".toMediaTypeOrNull())
            val emailPart = email.toRequestBody("text/plain".toMediaTypeOrNull())
            val phonePart = phone.toRequestBody("text/plain".toMediaTypeOrNull())
            val positionIdPart = positionId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            // Перетворення Base64 в Multipart файл
            val imageBytes = Base64.decode(base64Image, Base64.DEFAULT)
            val requestFile = imageBytes.toRequestBody("image/jpeg".toMediaTypeOrNull())
            val photoPart = MultipartBody.Part.createFormData("photo", "photo.jpg", requestFile)
            // Виконання запиту
            return apiService.registerUser(
                token = tokenResponse.token,
                name = namePart,
                email = emailPart,
                phone = phonePart,
                positionId = positionIdPart,
                photo = photoPart
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}
