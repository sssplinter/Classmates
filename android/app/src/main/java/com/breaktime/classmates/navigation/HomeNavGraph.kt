package com.breaktime.classmates.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.breaktime.classmates.presentation.screens.friends.FriendsScreen
import com.breaktime.classmates.presentation.screens.message.MessagesScreen
import com.breaktime.classmates.presentation.screens.people.PeopleScreen
import com.breaktime.classmates.presentation.screens.settings.SettingsScreen

@Composable
fun HomeNavGraph(parentNavController: NavHostController, navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Messages.route) {
        composable(Screen.Messages.route) {
            MessagesScreen(parentNavController, navController)
        }
        composable(Screen.Friends.route) {
            FriendsScreen(navController)
        }
        composable(Screen.People.route) {
            PeopleScreen(navController)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController)
        }
    }
}
