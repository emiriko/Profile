package com.alvaro.profile.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alvaro.profile.ui.theme.LightPink
import com.alvaro.profile.ui.theme.ProfileTheme

@Composable
fun BottomNavigationBar(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    NavigationBar(
        containerColor = LightPink,
        modifier = modifier,
        
    ) {
        NavigationItem.items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route, 
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = stringResource(id = item.labelResId)
                    )
                },
                label = { 
                    Text(
                        text = stringResource(id = item.labelResId) 
                    ) 
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun BottomNavigationBarPreview() {
    ProfileTheme {
        BottomNavigationBar()
    }
}