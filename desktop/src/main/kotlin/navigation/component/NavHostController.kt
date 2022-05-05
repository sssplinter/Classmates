package navigation.component

import java.util.*

class NavHostController {
    val graphBuilder = NavGraphBuilder()
    private var stack: Deque<Screen> = ArrayDeque()
    private var onNavigationChange: ((String) -> Unit)? = null

    val currentRoute: String
        get() = stack.peekFirst()?.route ?: ""

    val arguments: Map<String, java.io.Serializable>
        get() = stack.peekFirst()?.arguments ?: emptyMap()

    fun setOnNavigationChange(onNavigationChange: (String) -> Unit) {
        this.onNavigationChange = onNavigationChange
    }

    fun navigate(route: String) {
        stack.push(Screen(route = route, arguments = null))
        onNavigationChange?.invoke(route)
    }

    fun navigate(route: String, arguments: Map<String, java.io.Serializable>? = null) {
        stack.push(Screen(route = route, arguments = arguments))
        onNavigationChange?.invoke(route)
    }

    fun popBackStack() {
        stack.pollFirst()
        stack.peekFirst()?.route?.let { route ->
            onNavigationChange?.invoke(route)
        }
    }

    fun popBackStack(destinationRoute: String, inclusive: Boolean) {
        stack.firstOrNull { screen -> screen.route == destinationRoute } ?: throw Exception("There is no such route")
        var current = stack.peekFirst()
        while (current != null && current.route != destinationRoute) {
            stack.removeFirst()
            current = stack.peekFirst()
        }
        current?.let {
            if (inclusive) stack.removeFirst()
        }
        onNavigationChange?.invoke(current?.route ?: "")
    }

    fun clearBackStack() {
        stack.clear()
    }

    data class Screen(val route: String, val arguments: Map<String, java.io.Serializable>? = null)
}
