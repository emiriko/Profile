package com.alvaro.profile

import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.alvaro.profile.data.local.entity.FavoriteEntity
import com.alvaro.profile.data.state.ResultState
import com.alvaro.profile.data.state.UiState
import com.alvaro.profile.ui.navigation.BottomNavigationBar
import com.alvaro.profile.ui.navigation.Screen
import com.alvaro.profile.ui.ViewModelFactory
import com.alvaro.profile.ui.screen.detail.DetailScreen
import com.alvaro.profile.ui.screen.detail.DetailViewModel
import com.alvaro.profile.ui.screen.favorite.FavoriteScreen
import com.alvaro.profile.ui.screen.favorite.FavoriteViewModel
import com.alvaro.profile.ui.screen.home.HomeScreen
import com.alvaro.profile.ui.screen.home.HomeViewModel
import com.alvaro.profile.ui.screen.profile.ProfileScreen
import com.alvaro.profile.ui.screen.profile.ProfileViewModel
import com.alvaro.profile.ui.theme.ProfileTheme
import com.alvaro.profile.ui.theme.SetSystemBarColor
import com.alvaro.profile.ui.theme.Gradient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val context = LocalContext.current
    
    var currentTitle by remember { mutableStateOf(currentRoute) }
    
    LaunchedEffect(currentRoute) {
        currentTitle = if (currentRoute == Screen.Detail.route) {
            "Detail"
        } else {
            currentRoute
        }
    }
    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = currentTitle ?: "Profile",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                    )
                },
                navigationIcon = {
                    if (
                        navController.previousBackStackEntry != null 
                        && 
                        (currentRoute == Screen.Profile.route || currentRoute == Screen.Detail.route)
                    ) {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
        },
        bottomBar = {
            if (currentRoute != Screen.Detail.route && currentRoute != Screen.Profile.route ) {
                SetSystemBarColor()
                BottomNavigationBar(
                    navController = navController,
                )
            } else {
                if (currentRoute == Screen.Profile.route) {
                    SetSystemBarColor(
                        navigationBarColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    SetSystemBarColor()
                }
            }
        },
        modifier = modifier
            .safeDrawingPadding(),
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier
                .background(Brush.verticalGradient(Gradient))
                .fillMaxSize()
                .padding(innerPadding),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700)
                )
            },
        ) {
            composable(Screen.Home.route) {
                val homeViewModel: HomeViewModel = viewModel<HomeViewModel> (
                    factory = ViewModelFactory(context)
                )
                
                val users = homeViewModel.filteredUser.collectAsLazyPagingItems()
                val searchQuery by homeViewModel.searchQuery.collectAsState()

                LaunchedEffect(Unit) {
                    homeViewModel.getUsers()
                }
                
                HomeScreen(
                    users = users,
                    searchQuery = searchQuery,
                    onSearchQueryChanged = { query ->
                        homeViewModel.updateSearchQuery(query)
                    },
                    onClick = { userId ->
                        navController.navigate(Screen.Detail.createRoute(userId))
                    }
                )
            }
            composable(Screen.Profile.route) {
                val profileViewModel: ProfileViewModel = viewModel<ProfileViewModel> (
                    factory = ViewModelFactory(context)
                )
                
                val uiState by profileViewModel.uiState.collectAsState(initial = UiState.Loading)
                val profile by profileViewModel.profileData.collectAsState()
                
                ProfileScreen(
                    uiState = uiState,
                    profile = profile
                )
            }
            composable(Screen.Favorite.route) {
                val favoriteViewModel: FavoriteViewModel = viewModel<FavoriteViewModel> (
                    factory = ViewModelFactory(context)
                )
                
                LaunchedEffect(Unit) {
                    favoriteViewModel.getFavorites()
                }
                
                val uiState by favoriteViewModel.uiState.collectAsState()
                
                FavoriteScreen(
                    uiState = uiState,
                    onSurfaceClicked = { userId ->
                        navController.navigate(Screen.Detail.createRoute(userId))
                    }
                )
            }
            composable(
                route = Screen.Detail.createRoute("{userId}"),
                arguments = listOf(
                    navArgument("userId") { type = NavType.StringType } 
                )
            ) {
                val detailViewModel: DetailViewModel = viewModel(
                    factory = ViewModelFactory(context),
                )

                val favoriteViewModel: FavoriteViewModel = viewModel<FavoriteViewModel> (
                    factory = ViewModelFactory(context)
                )

                val user by detailViewModel.user.collectAsState()
                val posts = detailViewModel.posts.collectAsLazyPagingItems()
                
                var isOnFavorite by remember { mutableStateOf(false) }
                val favorites by favoriteViewModel.uiState.collectAsState()
                
                when (val userState = user) {
                    is ResultState.Success -> {
                        val data = userState.data
                        LaunchedEffect(user) {
                            currentTitle = "${data.firstName} ${data.lastName}"
                        }
                        
                        isOnFavorite = favorites is ResultState.Success && 
                                (favorites as ResultState.Success<List<FavoriteEntity>>).data.any { it.id == data.id }
                    }
                    else -> Unit
                }
                
                DetailScreen(
                    user = user,
                    posts = posts,
                    favoriteState = favorites,
                    isOnFavorite = isOnFavorite,
                    onFavoriteClicked = { data ->
                        if (isOnFavorite) {
                            favoriteViewModel.deleteFromFavorites(data.id)
                        } else {
                            favoriteViewModel.insertFavorite(data)
                        }
                    }
                )
            }
        }
        
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileAppPreview() {
    ProfileTheme {
        ProfileApp()
    }
}