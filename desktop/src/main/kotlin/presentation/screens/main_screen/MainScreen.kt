package presentation.screens.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import navigation.HomeNavGraph
import navigation.Screen
import navigation.component.NavHostController
import navigation.component.rememberNavController
import ui.theme.EXTRA_SMALL_PADDING
import ui.theme.MenuPanel
import ui.theme.ProfileOutline
import ui.theme.SMALL_PADDING

typealias ButtonInfo = Pair<String, ImageVector>

@Composable
fun MainScreen(navController: NavHostController) {
    val tabNavController = rememberNavController()
    var currentTabRoute by remember { mutableStateOf(Screen.Messages.route) }
    Box(
        modifier = Modifier
            .padding(SMALL_PADDING)
            .fillMaxSize()
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
                        ButtonInfo(Screen.Home.route, Icons.Default.Home),
                        ButtonInfo(Screen.Messages.route, Icons.Default.Email),
                        ButtonInfo(Screen.Favorite.route, Icons.Default.Star),
                        ButtonInfo(Screen.People.route, Icons.Default.Person),
                        ButtonInfo(Screen.Settings.route, Icons.Default.Settings)
                    )
                    buttonsInfo.forEach { (route, icon) ->
                        val buttonBackground: ButtonColors
                        val tint: Color
                        if (route == currentTabRoute) {
                            buttonBackground = ButtonDefaults.textButtonColors(backgroundColor = Color(0xFF6C85F7))
                            tint = Color.White
                        } else {
                            buttonBackground = ButtonDefaults.textButtonColors()
                            tint = Color(0xFF656F7E)
                        }
                        TextButton(
                            modifier = Modifier.padding(EXTRA_SMALL_PADDING).size(40.dp),
                            colors = buttonBackground,
                            onClick = {
                                currentTabRoute = route
                                tabNavController.navigate(route)
                            }
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = "",
                                tint = tint
                            )
                        }
                    }
                }
                TextButton(
                    modifier = Modifier
                        .padding(EXTRA_SMALL_PADDING)
                        .size(40.dp),
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = MaterialTheme.colors.ProfileOutline
                    ),
                    onClick = {
                        tabNavController.navigate(Screen.Profile.route)
                    }
                ) {
                    Image(
                        painter = painterResource("no_user_photo.svg"),
                        contentDescription = ""
                    )
                }
            }
            Box(modifier = Modifier.padding(start = SMALL_PADDING)) {
                HomeNavGraph(tabNavController)
            }
        }
    }
}