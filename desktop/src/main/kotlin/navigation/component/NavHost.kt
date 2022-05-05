package navigation.component

import androidx.compose.runtime.*

@Composable
fun NavHost(
    navController: NavHostController,
    startDestination: String,
    builder: NavGraphBuilder.() -> Unit,
) {
    navController.graphBuilder.builder()
    navController.navigate(startDestination)
    NavHost(navController)
}

@Composable
private fun NavHost(navController: NavHostController) {
    var currentScreen by remember { mutableStateOf(navController.currentRoute) }
    navController.setOnNavigationChange { route ->
        currentScreen = route
    }
    val screen = navController.graphBuilder.getScreen(currentScreen)
    screen?.let { content ->
        content()
    }
}