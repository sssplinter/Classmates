package navigation

import androidx.compose.runtime.Composable
import navigation.component.NavHost
import navigation.component.NavHostController
import presentation.screens.chats_screen.ChatsScreen
import presentation.screens.friends_screen.FriendsScreen
import presentation.screens.people_screen.PeopleScreen
import presentation.screens.settings_screen.SettingsScreen

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Messages.route) {
        composable(Screen.Home.route) {

        }
        composable(Screen.Messages.route) {
            ChatsScreen(navController)
        }
        composable(Screen.Connections.route) {
            FriendsScreen()
        }
        composable(Screen.People.route) {
            PeopleScreen(navController)
        }
        composable(Screen.Settings.route) {
            SettingsScreen()
        }
        composable(Screen.Profile.route) {

        }
    }
}
