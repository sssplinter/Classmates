package com.breaktime.classmates

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.breaktime.classmates.di.kodein
import com.breaktime.classmates.domain.entities.data.ChatInfo
import com.breaktime.classmates.domain.entities.data.Languages
import com.breaktime.classmates.domain.use_cases.settings.GetLanguageUseCase
import com.breaktime.classmates.localization.Language
import com.breaktime.classmates.localization.Localization
import com.breaktime.classmates.localization.languages.LanguageEn
import com.breaktime.classmates.navigation.Screen
import com.breaktime.classmates.presentation.screens.host.HostScreen
import com.breaktime.classmates.presentation.screens.login.LoginScreen
import com.breaktime.classmates.presentation.screens.main.MainScreen
import com.breaktime.classmates.presentation.screens.message.ChatScreen
import com.breaktime.classmates.presentation.screens.message.MessageScreen
import com.breaktime.classmates.presentation.screens.people.PeopleScreen
import com.breaktime.classmates.presentation.screens.settings.SettingsScreen
import com.breaktime.classmates.presentation.screens.splash.SplashScreen
import com.breaktime.classmates.ui.theme.AppTheme
import org.kodein.di.compose.withDI
import org.kodein.di.instance

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContent {
            withDI(kodein) {
                AppTheme {
                    Localization(getLocalization()) {
                        HostScreen()
                    }
                }
            }
        }
    }
}


private fun getLocalization(): Language {
    val getLanguageUseCase: GetLanguageUseCase by kodein.di.instance()
    return getLanguageUseCase()?.let { language -> Languages.valueOf(language).language }
        ?: LanguageEn
}

@Composable
fun ComposeNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.Main.route) {
            MainScreen(navController = navController)
        }
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

@Composable
fun HomeNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Settings.route
    ) {
        composable(Screen.Home.route) {

        }
        composable(Screen.Messages.route) {
            MessageScreen(navController)
        }
        composable(Screen.Chat.route) {
            // todo how to dial with chat id?
            val chatInfo =
                navController.previousBackStackEntry?.savedStateHandle?.get<ChatInfo>("chatInfo")
            chatInfo?.let {
                ChatScreen(navController = navController, chatInfo = chatInfo)
            }

        }
        composable(Screen.Favorite.route) {

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