package com.sxam.sxamtop.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.sxam.sxamtop.datastore.SettingsDataStore
import com.sxam.sxamtop.ui.bookmarks.BookmarksScreen
import com.sxam.sxamtop.ui.detail.DetailScreen
import com.sxam.sxamtop.ui.home.HomeScreen
import com.sxam.sxamtop.ui.post.PostNewsScreen
import com.sxam.sxamtop.ui.search.SearchScreen
import com.sxam.sxamtop.ui.settings.SettingsScreen
import com.sxam.sxamtop.ui.shared.SharedNewsViewModel

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
fun AppNavigation(settingsDataStore: SettingsDataStore) {
    val navController = rememberNavController()
    // Shared ViewModel tied to the Navigation graph's lifecycle to hold the selected article
    val sharedNewsViewModel: SharedNewsViewModel = viewModel()

    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    settingsDataStore = settingsDataStore,
                    sharedViewModel = sharedNewsViewModel,
                    onNavigateToDetail = { newsId ->
                        navController.navigate(Screen.Detail.createRoute(newsId))
                    }
                )
            }
            composable(Screen.Search.route) {
                SearchScreen(
                    settingsDataStore = settingsDataStore,
                    sharedViewModel = sharedNewsViewModel,
                    onNavigateToDetail = { newsId ->
                        navController.navigate(Screen.Detail.createRoute(newsId))
                    }
                )
            }
            composable(Screen.Bookmarks.route) {
                BookmarksScreen(
                    sharedViewModel = sharedNewsViewModel,
                    onNavigateToDetail = { newsId ->
                        navController.navigate(Screen.Detail.createRoute(newsId))
                    }
                )
            }
            composable(Screen.Post.route) { PostNewsScreen() }
            composable(Screen.Settings.route) { SettingsScreen(settingsDataStore = settingsDataStore) }
            
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("newsId") { type = NavType.StringType })
            ) { backStackEntry ->
                val newsId = backStackEntry.arguments?.getString("newsId") ?: ""
                DetailScreen(
                    newsId = newsId,
                    sharedViewModel = sharedNewsViewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}