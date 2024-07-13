package com.alvaro.profile.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.alvaro.profile.R

sealed class NavigationItem(val route: String, val icon: ImageVector, val labelResId: Int) {
    object Home : NavigationItem(
        route = Screen.Home.route,
        icon = Icons.Default.Home,
        labelResId = R.string.home
    )

    object Favorite : NavigationItem(
        route = Screen.Favorite.route,
        icon = Icons.Default.Favorite,
        labelResId = R.string.favorite
    )

    object Profile : NavigationItem(
        route = Screen.Profile.route,
        icon = Icons.Default.AccountCircle,
        labelResId = R.string.profile
    )

    companion object {
        val items = listOf(Home, Favorite, Profile)
    }
}