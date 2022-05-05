package com.breaktime.classmates.presentation.screens.main

import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.breaktime.classmates.navigation.HomeNavGraph
import com.breaktime.classmates.presentation.screens.menu.Drawer
import com.breaktime.classmates.presentation.screens.menu.DrawerScreens
import com.breaktime.classmates.presentation.screens.menu.TopBar

@Composable
fun MainScreen(navController: NavHostController) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val tabNavController = rememberNavController()
    val currentTab = remember { mutableStateOf<DrawerScreens>(DrawerScreens.Messages) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                title = currentTab.value.title,
                scope = scope,
                scaffoldState = scaffoldState
            )
        },
        drawerBackgroundColor = Color.White,
        drawerContent = {
            Drawer(
                scope = scope,
                scaffoldState = scaffoldState,
                parentController = navController,
                tabController = tabNavController,
                onScreenChange = {
                    currentTab.value = it
                }
            )
        },
    ) {
        HomeNavGraph(navController, tabNavController)
    }
}