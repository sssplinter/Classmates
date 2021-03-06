package presentation.screens.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import navigation.HomeNavGraph
import navigation.Screen
import navigation.component.NavHostController
import navigation.component.rememberNavController
import org.kodein.di.compose.rememberInstance
import presentation.screens.main_screen.elements.MenuButton
import presentation.screens.main_screen.elements.ProfileButton
import presentation.screens.profile_dialog.ProfileScreen
import ui.theme.EXTRA_SMALL_PADDING
import ui.theme.MenuPanel
import ui.theme.SMALL_PADDING

@Composable
fun MainScreen(navController: NavHostController) {
    val viewModel: MainScreenViewModel by rememberInstance()

    val tabNavController = rememberNavController()
    val isProfileDialog = remember { mutableStateOf(false) }
    val currentTabRoute = remember { mutableStateOf(Screen.Messages.route) }
    val isMessagesNotified = remember { mutableStateOf(false) }
    val isConnectionsNotified = remember { mutableStateOf(false) }

    initMainObservable(
        scope = rememberCoroutineScope(),
        viewModel = viewModel,
        tabNavController = tabNavController,
        isProfileDialog = isProfileDialog,
        currentTabRoute = currentTabRoute,
        isMessagesNotified = isMessagesNotified,
        isConnectionsNotified = isConnectionsNotified
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .padding(SMALL_PADDING)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clip(MaterialTheme.shapes.large)
                        .background(MaterialTheme.colors.MenuPanel)
                        .padding(
                            vertical = SMALL_PADDING,
                            horizontal = EXTRA_SMALL_PADDING
                        ),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        val buttonsInfo = listOf(
                            Triple(Screen.Messages.route, Icons.Default.Email, isMessagesNotified),
                            Triple(Screen.Connections.route, Icons.Default.Person, isConnectionsNotified),
                            Triple(Screen.People.route, Icons.Default.Search, null),
                            Triple(Screen.Settings.route, Icons.Default.Settings, null)
                        )
                        buttonsInfo.forEach { (route, icon, isNotified) ->
                            if (route == currentTabRoute.value) {
                                isNotified?.value = false
                            }
                            MenuButton(
                                icon = icon,
                                isActive = route == currentTabRoute.value,
                                isNotified = isNotified?.value == true,
                                onClick = {
                                    isNotified?.value = false
                                    viewModel.setEvent(MainScreenContract.Event.OnMenuButtonClick(route))
                                    tabNavController.navigate(route)
                                }
                            )
                        }
                    }
                    ProfileButton(
                        imageUrl = "",
                        onClick = {
                            viewModel.setEvent(MainScreenContract.Event.OnProfileButtonClick)
                        }
                    )
                }
                Box(modifier = Modifier.padding(start = SMALL_PADDING)) {
                    HomeNavGraph(tabNavController)
                }
            }
        }
        if (isProfileDialog.value) {
            ProfileScreen(
                navController = navController,
                onOutBoxClick = {
                    viewModel.setEvent(MainScreenContract.Event.OnCloseProfileClick)
                }
            )
        }
    }
}