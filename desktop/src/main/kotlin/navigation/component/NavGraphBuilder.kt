package navigation.component

import androidx.compose.runtime.Composable

class NavGraphBuilder {
    private val pages: MutableMap<String, @Composable () -> Unit> = mutableMapOf()

    fun getScreen(route: String) = pages[route]

    fun NavGraphBuilder.composable(
        route: String,
        content: @Composable () -> Unit,
    ) {
        pages[route] = content
    }
}