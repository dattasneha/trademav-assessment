package com.snehadatta.signalviewer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.snehadatta.signalviewer.constant.NavItem
import com.snehadatta.signalviewer.ui.presentation.DetailScreen
import com.snehadatta.signalviewer.ui.presentation.MainScreen

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavItem.LIST.route
    ) {

        composable(NavItem.LIST.route) {
            MainScreen(
                navController = navController
            )
        }

        composable(
            route = "${NavItem.DETAIL.route}/{postId}"
        ) { backStackEntry ->

            val postId =
                backStackEntry.arguments?.getString("postId")?.toIntOrNull()

            DetailScreen(
                postId = postId,
                navController = navController
            )
        }
    }
}