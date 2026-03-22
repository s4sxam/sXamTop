package com.sxam.sxamtop.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.sxam.sxamtop.ui.bookmarks.BookmarksScreen
import com.sxam.sxamtop.ui.detail.DetailScreen
import com.sxam.sxamtop.ui.home.HomeScreen
import com.sxam.sxamtop.ui.post.PostNewsScreen
import com.sxam.sxamtop.ui.search.SearchScreen
import com.sxam.sxamtop.ui.settings.SettingsScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search")
    object Bookmarks : Screen("bookmarks")
    object Post : Screen("post")
    object Settings : Screen("settings")
    object Detail : Screen("detail/{newsId}") {
        fun createRoute(newsId: String) = "detail/$newsId"
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(onNavigateToDetail = { navController.navigate(Screen.Detail.createRoute(it)) })
            }
            composable(Screen.Search.route) {
                SearchScreen(onNavigateToDetail = { navController.navigate(Screen.Detail.createRoute(it)) })
            }
            composable(Screen.Bookmarks.route) {
                BookmarksScreen(onNavigateToDetail = { navController.navigate(Screen.Detail.createRoute(it)) })
            }
            composable(Screen.Post.route) { PostNewsScreen() }
            composable(Screen.Settings.route) { SettingsScreen() }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("newsId") { type = NavType.StringType })
            ) { 
                // #4 FIX: Parameter mismatch resolved (ViewModel uses SavedStateHandle to grab ID securely)
                DetailScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}