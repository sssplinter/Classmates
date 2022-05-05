package com.breaktime.classmates.presentation.screens.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.breaktime.classmates.R
import com.breaktime.classmates.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// TODO reorganize and remove useless
sealed class DrawerScreens(val route: String, val title: String, val icon: ImageVector) {
    object Home : DrawerScreens(Screen.Home.route, "Home", Icons.Default.Home)
    object Messages : DrawerScreens(Screen.Messages.route, "Messages", Icons.Default.Email)
    object Favorite : DrawerScreens(Screen.Favorite.route, "Favorite", Icons.Default.Star)
    object People : DrawerScreens(Screen.People.route, "People", Icons.Default.Person)
    object Settings : DrawerScreens(Screen.Settings.route, "Settings", Icons.Default.Settings)
}

private val screens = listOf(
    DrawerScreens.Home,
    DrawerScreens.Messages,
    DrawerScreens.Favorite,
    DrawerScreens.People,
    DrawerScreens.Settings
)

@Composable
fun Drawer(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .background(Color.LightGray)
    ) {
        Image(
            painter = painterResource(id = R.drawable.owl),
            contentDescription = "logo",
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .padding(10.dp)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        )
        // List of navigation items
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { item ->
            DrawerItem(item = item, selected = (currentRoute == item.route), onItemClick = {
                navController.navigate(item.route)
                {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
//                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            })
        }

        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Classmates",
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun DrawerItem(item: DrawerScreens, selected: Boolean, onItemClick: (DrawerScreens) -> Unit) {
    val background = if (selected) Color.Black else android.R.color.transparent
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(item) })
            .height(45.dp)
            .background(Color.White)
            .padding(start = 10.dp)
    ) {
        Icon(item.icon, item.title,
            Modifier
                .height(35.dp)
                .width(35.dp))
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = item.title,
            fontSize = 18.sp,
            color = Color.DarkGray
        )
    }
}

@Preview(showBackground = false)
@Composable
fun DrawerItemPreview() {
    DrawerItem(item = DrawerScreens.Home, false, onItemClick = {})
}

@Preview(showBackground = true)
@Composable
fun MyDrawerPreview() {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val navController = rememberNavController()
    Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController)
}


