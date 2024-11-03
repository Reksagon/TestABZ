package com.korniienko.testtask.utils

import android.app.Application

class TestTaskApp : Application() {

    lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate() {
        super.onCreate()
        connectivityObserver = ConnectivityObserver(this)
    }
}