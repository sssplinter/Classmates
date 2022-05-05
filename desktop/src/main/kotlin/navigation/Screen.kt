package navigation

sealed class Screen(val route: String) {
    object Splash : Screen(route = "splash_screen")
    object Login : Screen(route = "login_screen")
    object Main : Screen(route = "main_screen")
    object Home : Screen(route = "home_screen")
    object Messages : Screen(route = "messages_screen")
    object Connections : Screen(route = "favorite_screen")
    object People : Screen(route = "people_screen")
    object Settings : Screen(route = "settings_screen")
    object Profile : Screen(route = "profile_screen")
}