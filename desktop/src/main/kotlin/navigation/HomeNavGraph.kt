package navigation

import androidx.compose.runtime.Composable
import navigation.component.NavHost
import navigation.component.NavHostController
import presentation.screens.message_screen.MessageScreen
import presentation.screens.people_screen.PeopleScreen

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Messages.route) {
        composable(Screen.Home.route) {

        }
        composable(Screen.Messages.route) {
            MessageScreen(navController)
        }
        composable(Screen.Favorite.route) {

        }
        composable(Screen.People.route) {
            PeopleScreen(navController)
        }
        composable(Screen.Settings.route) {

        }
        composable(Screen.Profile.route) {

        }
    }
}
