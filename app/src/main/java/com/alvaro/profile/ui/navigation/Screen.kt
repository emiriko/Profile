package com.alvaro.profile.ui.navigation


sealed class Screen(val route: String) {
    object Home : Screen(Route.Home.name)
    object Detail : Screen("${Route.Detail.name}/{userId}") {
        fun createRoute(userId: String) = "${Route.Detail.name}/$userId"
    }
    object Favorite : Screen(Route.Favorite.name)
    object Profile : Screen(Route.Profile.name)
}