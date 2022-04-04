package presentation.screens.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import navigation.component.NavHostController
import presentation.screens.message_screen.MessageScreen
import ui.theme.EXTRA_SMALL_PADDING
import ui.theme.MenuPanel
import ui.theme.ProfileOutline
import ui.theme.SMALL_PADDING

@Composable
fun HomeScreen(navController: NavHostController) {
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
                    TextButton(
                        modifier = Modifier.padding(EXTRA_SMALL_PADDING).size(40.dp),
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "",
                            tint = Color(0xFF656F7E)
                        )
                    }
                    TextButton(
                        modifier = Modifier.padding(EXTRA_SMALL_PADDING).size(40.dp),
                        colors = ButtonDefaults.textButtonColors(backgroundColor = Color(0xFF6C85F7)),
                        shape = MaterialTheme.shapes.medium,
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                    TextButton(
                        modifier = Modifier.padding(EXTRA_SMALL_PADDING).size(40.dp),
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "",
                            tint = Color(0xFF656F7E)
                        )
                    }
                    TextButton(
                        modifier = Modifier.padding(EXTRA_SMALL_PADDING).size(40.dp),
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "",
                            tint = Color(0xFF656F7E)
                        )
                    }
                    TextButton(
                        modifier = Modifier.padding(EXTRA_SMALL_PADDING).size(40.dp),
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "",
                            tint = Color(0xFF656F7E)
                        )
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
                    onClick = {}
                ) {
                    Image(
                        painter = painterResource("no_user_photo.svg"),
                        contentDescription = ""
                    )
                }
            }
            Box(modifier = Modifier.padding(start = SMALL_PADDING)) {
                MessageScreen(navController)
            }
        }
    }
}