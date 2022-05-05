package com.breaktime.classmates.presentation.screens.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.breaktime.classmates.R
import com.breaktime.classmates.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

sealed class DrawerScreens(val route: String, val title: String, val icon: ImageVector) {
    object Messages : DrawerScreens(Screen.Messages.route, "Messages", Icons.Default.Email)
    object Friends : DrawerScreens(Screen.Friends.route, "Friends", Icons.Default.Person)
    object People : DrawerScreens(Screen.People.route, "People", Icons.Default.Search)
    object Settings : DrawerScreens(Screen.Settings.route, "Settings", Icons.Default.Settings)
    object Profile : DrawerScreens(Screen.Profile.route, "Profile", Icons.Default.Settings)
}

private val screens = listOf(
    DrawerScreens.Messages,
    DrawerScreens.Friends,
    DrawerScreens.People,
    DrawerScreens.Settings
)

@Composable
fun Drawer(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    parentController: NavHostController,
    tabController: NavHostController,
    onScreenChange: (DrawerScreens) -> Unit
) {
    val navBackStackEntry by tabController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Column {
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
        screens.forEach { item ->
            DrawerItem(
                item = item,
                selected = (currentRoute == item.route),
                onItemClick = {
                    onScreenChange(item)
                    tabController.popBackStack()
                    tabController.navigate(item.route)
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                }
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        DrawerItem(
            item = DrawerScreens.Profile,
            selected = (currentRoute == DrawerScreens.Profile.route),
            onItemClick = {
                onScreenChange(DrawerScreens.Profile)
                parentController.navigate(DrawerScreens.Profile.route)
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            })
        Text(
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.CenterHorizontally),
            text = "Classmates",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DrawerItem(item: DrawerScreens, selected: Boolean, onItemClick: (DrawerScreens) -> Unit) {
    val background = if (selected) Color(0xAA999999) else Color.Transparent
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(item) })
            .height(45.dp)
            .background(background)
            .padding(start = 10.dp)
    ) {
        Icon(
            item.icon, item.title,
            Modifier
                .height(35.dp)
                .width(35.dp)
        )
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = item.title,
            fontSize = 18.sp,
            color = Color.DarkGray
        )
    }
}
