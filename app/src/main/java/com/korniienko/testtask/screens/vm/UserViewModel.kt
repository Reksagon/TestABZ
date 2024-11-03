package com.korniienko.testtask.screens.vm

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.korniienko.testtask.api.RetrofitInstance
import com.korniienko.testtask.api.User
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class UserViewModel : ViewModel() {
    val users = mutableStateListOf<User>()
    val errorMessage = mutableStateOf("")
    val isLoading = mutableStateOf(false)
    val isLoadingMore = mutableStateOf(false)
    var currentPage = 1
    var totalPages = 1

    fun fetchUsers(page: Int) {
        if (users.isNotEmpty() && page == 1) return // Якщо дані вже завантажені, не завантажуємо знову

        isLoading.value = true
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getUsers(currentPage, 6)
                users.clear()
                users.addAll(response.users)
                totalPages = response.totalPages
                currentPage = page
                isLoading.value = false
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Unknown Error"
                isLoading.value = false
            }
        }
    }

    fun fetchNextPage() {
        if (currentPage >= totalPages) return
        isLoadingMore.value = true
        viewModelScope.launch {
            try {
                val nextPage = currentPage + 1
                val response = RetrofitInstance.api.getUsers(nextPage, 6)
                users.addAll(response.users)
                currentPage = nextPage
                isLoadingMore.value = false
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Unknown Error"
                isLoadingMore.value = false
            }
        }
    }
}

