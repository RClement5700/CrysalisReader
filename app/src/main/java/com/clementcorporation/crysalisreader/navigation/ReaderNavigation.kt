package com.clementcorporation.crysalisreader.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.clementcorporation.crysalisreader.screens.ReaderSplashScreen
import com.clementcorporation.crysalisreader.screens.details.BookDetailsScreen
import com.clementcorporation.crysalisreader.screens.stats.ReaderBookStatsScreen
import com.clementcorporation.crysalisreader.screens.update.ReaderBookUpdateScreen
import com.clementcorporation.crysalisreader.screens.login.ReaderLoginScreen
import com.clementcorporation.crysalisreader.screens.home.ReaderHomeScreen
import com.clementcorporation.crysalisreader.screens.search.ReaderBookSearchScreen


@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name) {
        composable(ReaderScreens.SplashScreen.name){
            ReaderSplashScreen(navController)
        }
        composable(ReaderScreens.HomeScreen.name){
            ReaderHomeScreen(navController)
        }
        composable(ReaderScreens.LoginScreen.name){
            ReaderLoginScreen(navController)
        }
        composable(ReaderScreens.DetailScreen.name){
            BookDetailsScreen(navController)
        }
        composable(ReaderScreens.StatsScreen.name){
            ReaderBookStatsScreen(navController)
        }
        composable(ReaderScreens.UpdateScreen.name){
            ReaderBookUpdateScreen(navController)
        }
        composable(ReaderScreens.SearchScreen.name){
            ReaderBookSearchScreen(navController)
        }
    }
}