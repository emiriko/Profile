package com.alvaro.profile.ui.screen.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.alvaro.profile.R
import com.alvaro.profile.data.local.entity.FavoriteEntity
import com.alvaro.profile.data.state.ResultState
import com.alvaro.profile.ui.components.ErrorIndicator
import com.alvaro.profile.ui.components.FavoriteItem
import com.alvaro.profile.ui.components.GradientBackground
import com.alvaro.profile.ui.components.LoadingIndicator
import com.alvaro.profile.ui.components.NotFound
import com.alvaro.profile.ui.theme.LightPinkPurple
import com.alvaro.profile.ui.theme.ProfileTheme
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun FavoriteScreen(
    uiState: ResultState<List<FavoriteEntity>>,
    onSurfaceClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        is ResultState.Success -> {
            FavoriteContent(
                favorites = uiState.data,
                onSurfaceClicked = onSurfaceClicked,
                modifier = modifier.fillMaxSize()
            )
        }

        is ResultState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoadingIndicator()
            }
        }

        is ResultState.Error -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                ErrorIndicator(error = uiState.error)
            }
        }
    }
}

@Composable
fun FavoriteContent(
    favorites: List<FavoriteEntity>,
    onSurfaceClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        if (favorites.isEmpty()) {
            NotFound(
                notFoundText = stringResource(id = R.string.no_favorite),
                modifier = modifier
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(160.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier
            ) {
                items(favorites) { favorite ->
                    FavoriteItem(
                        favorite = favorite,
                        onSurfaceClicked = onSurfaceClicked
                    )
                }
            }
        }
    }
}

@Composable
@Preview(name = "Success", showBackground = true)
fun FavoriteScreenSuccessPreview() {
    GradientBackground {
        ProfileTheme {
            val result = ResultState.Success(emptyList<FavoriteEntity>())
            val favoritesState = MutableStateFlow<ResultState<List<FavoriteEntity>>>(result)
            val favorites by favoritesState.collectAsState()
            
            FavoriteScreen(
                uiState = favorites,
                onSurfaceClicked = {}
            )
        }
    }
}

@Composable
@Preview(name = "Loading", showBackground = true)
fun FavoriteScreenLoadingPreview() {
    GradientBackground {
        ProfileTheme {
            val result = ResultState.Loading
            val favoritesState = MutableStateFlow<ResultState<List<FavoriteEntity>>>(result)
            val favorites by favoritesState.collectAsState()

            FavoriteScreen(
                uiState = favorites,
                onSurfaceClicked = {}
            )
        }
    }
}

@Composable
@Preview(name = "Error", showBackground = true)
fun FavoriteScreenErrorPreview() {
    GradientBackground {
        ProfileTheme {
            val result = ResultState.Error("An Error Occured")
            val favoritesState = MutableStateFlow<ResultState<List<FavoriteEntity>>>(result)
            val favorites by favoritesState.collectAsState()

            FavoriteScreen(
                uiState = favorites,
                onSurfaceClicked = {}
            )
        }
    }
}