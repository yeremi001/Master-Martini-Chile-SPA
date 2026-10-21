package com.example.mastermartini.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mastermartini.ui.screens.DetailScreen
import com.example.mastermartini.ui.screens.RegisterScreen
import com.example.mastermartini.ui.viewmodels.AuthViewModel

sealed class Screen(val route: String) {
    object Register : Screen("register")
    object Detail : Screen("detail")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Register.route
    ) {
        composable(Screen.Register.route) {
            RegisterScreen(
                viewModel = authViewModel,
                onRegisterSuccess = {
                    navController.navigate(Screen.Detail.route)
                }
            )
        }

        composable(Screen.Detail.route) {
            DetailScreen()
        }
    }
}