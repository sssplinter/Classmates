package com.breaktime.classmates.navigation

sealed class Screen(val route: String) {
    object Splash : Screen(route = "splash")
    object Login : Screen(route = "login")
    object Main : Screen(route = "main")
    object Home : Screen(route = "home")
    object Messages : Screen(route = "messages")
    object Chat: Screen(route = "chat")
    object Favorite : Screen(route = "favorite")
    object Friends : Screen(route = "friends")
    object People : Screen(route = "people")
    object Settings : Screen(route = "settings")
    object Profile : Screen(route = "profile")
}