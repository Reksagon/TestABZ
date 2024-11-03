package com.korniienko.testtask.screens.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.korniienko.testtask.utils.TestTaskApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val connectivityObserver = (application as TestTaskApp).connectivityObserver

    private val _isConnected = MutableStateFlow(true)
    val isConnected: StateFlow<Boolean> = _isConnected

    init {
        viewModelScope.launch {
            connectivityObserver.connectivityStatus.collectLatest { status ->
                _isConnected.value = status
            }
        }
    }

    fun retryConnection() {
        // Повторна перевірка або спроба відновлення підключення
    }
}
