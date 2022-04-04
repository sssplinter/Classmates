package navigation.component

import androidx.compose.runtime.Composable

private var navController: NavHostController? = null

@Composable
fun rememberNavController(): NavHostController {
    if (navController == null) {
        navController = NavHostController()
    }
    return navController!!
}