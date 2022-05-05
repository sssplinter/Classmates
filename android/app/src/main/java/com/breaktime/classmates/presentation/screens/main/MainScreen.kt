package com.breaktime.classmates.presentation.screens.main

import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.breaktime.classmates.HomeNavigation
import com.breaktime.classmates.presentation.screens.menu.Drawer
import com.breaktime.classmates.presentation.screens.menu.TopBar


@Composable
fun MainScreen(navController: NavHostController) {

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val tabNavController = rememberNavController()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar(scope = scope, scaffoldState = scaffoldState) },
        drawerBackgroundColor = Color.Red,
        // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer
        drawerContent = {
            Drawer(scope = scope, scaffoldState = scaffoldState, navController = tabNavController)
        },
    ) {
        HomeNavigation(tabNavController)        /* Add code later */
    }
}