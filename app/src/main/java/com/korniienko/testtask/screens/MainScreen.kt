package com.korniienko.testtask.screens

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.*
import androidx.compose.material.Scaffold
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.korniienko.testtask.R
import com.korniienko.testtask.navigation.BottomNavItem
import com.korniienko.testtask.navigation.CustomBottomNavigation
import com.korniienko.testtask.screens.vm.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel(), navController: NavController) {
    val isConnected by viewModel.isConnected.collectAsState(initial = true)

    if (isConnected) {
        ContentScreen(navController)
    } else {
        NoInternetScreen(onRetry = {
            viewModel.retryConnection()
        })
    }
}

@Composable
fun ContentScreen(navController: NavController) {
    var selectedScreen by remember { mutableStateOf("users") }
    val showBottomBar = selectedScreen == "users" || selectedScreen == "signup"

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                CustomBottomNavigation(
                    items = listOf(
                        BottomNavItem("users", R.drawable.ic_people, "Users"),
                        BottomNavItem("signup", R.drawable.ic_sign_up, "Sign up")
                    ),
                    selectedRoute = selectedScreen,
                    onItemSelected = { item ->
                        selectedScreen = item.route // Змінюємо поточний екран
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when (selectedScreen) {
                "users" -> UsersScreen()
                "signup" -> SignUpScreen(navController = navController, onRegisterSuccess = {
                    selectedScreen = "register_success"
                }, onRegisterError = {
                    selectedScreen = "register_error"
                })
                "register_success" -> RegisterStatusScreen(
                    onRetry = { selectedScreen = "users" },
                    success = true
                )
                "register_error" -> RegisterStatusScreen(
                    onRetry = { selectedScreen = "signup" },
                    success = false
                )
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun MainScreenPreview() {
//    MainScreen()
//}