package com.breaktime.classmates.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.breaktime.classmates.domain.entities.data.ChatInfo
import com.breaktime.classmates.presentation.screens.login.LoginScreen
import com.breaktime.classmates.presentation.screens.main.MainScreen
import com.breaktime.classmates.presentation.screens.chat.ChatScreen
import com.breaktime.classmates.presentation.screens.profile.ProfileScreen
import com.breaktime.classmates.presentation.screens.splash.SplashScreen

@Composable
fun GlobalNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.Main.route) {
            MainScreen(navController = navController)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController)
        }
        composable(Screen.Chat.route) {
            val chatInfo =
                navController.previousBackStackEntry?.savedStateHandle?.get<ChatInfo>("chatInfo")
            chatInfo?.let {
                ChatScreen(navController = navController, chatInfo = chatInfo)
            }
        }
    }
}
