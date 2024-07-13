package com.alvaro.profile.ui.screen.detail

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.alvaro.profile.R
import com.alvaro.profile.data.local.entity.FavoriteEntity
import com.alvaro.profile.data.remote.response.Posts
import com.alvaro.profile.data.remote.response.UserDetailResponse
import com.alvaro.profile.data.state.ResultState
import com.alvaro.profile.ui.components.ContactItem
import com.alvaro.profile.ui.components.ErrorIndicator
import com.alvaro.profile.ui.components.ErrorMessage
import com.alvaro.profile.ui.components.GradientBackground
import com.alvaro.profile.ui.components.PostCard
import com.alvaro.profile.ui.components.LoadingIndicator
import com.alvaro.profile.ui.components.Orientation
import com.alvaro.profile.ui.theme.LightPinkPurple
import com.alvaro.profile.ui.theme.ProfileTheme
import com.alvaro.profile.utils.Helper
import com.wiryadev.bootstrapiconscompose.BootstrapIcons
import com.wiryadev.bootstrapiconscompose.bootstrapicons.Filled
import com.wiryadev.bootstrapiconscompose.bootstrapicons.filled.Envelope
import com.wiryadev.bootstrapiconscompose.bootstrapicons.filled.Geo
import com.wiryadev.bootstrapiconscompose.bootstrapicons.filled.Telephone
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

@Composable
fun DetailScreen(
    user: ResultState<UserDetailResponse>,
    favoriteState: ResultState<List<FavoriteEntity>>,
    isOnFavorite: Boolean,
    onFavoriteClicked: (FavoriteEntity) -> Unit,
    posts: LazyPagingItems<Posts>,
    modifier: Modifier = Modifier
) {
    when (user) {
        is ResultState.Success -> {
            UserDetailContent(
                user = user.data,
                posts = posts,
                favoriteState = favoriteState,
                isOnFavorite = isOnFavorite,
                onFavoriteClicked = onFavoriteClicked,
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
                ErrorIndicator(error = user.error)
            }
        }
    }
}

@Composable
fun UserDetailContent(
    user: UserDetailResponse,
    posts: LazyPagingItems<Posts>,
    favoriteState: ResultState<List<FavoriteEntity>>,
    isOnFavorite: Boolean,
    onFavoriteClicked: (FavoriteEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SubcomposeAsyncImage(
                model = user.picture,
                loading = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center 
                    ) {
                        LoadingIndicator(
                            diameter = 30.dp
                        )
                    }
                },
                contentDescription = stringResource(id = R.string.profile),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(180.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "${user.firstName} ${user.lastName}",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        UserContactItem(user)
        
        UserPostsContent(posts)
        
        Column (
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ){
            Button(
                onClick = {
                    onFavoriteClicked(
                        FavoriteEntity(
                            id = user.id,
                            picture = user.picture,
                            fullName = "${user.firstName} ${user.lastName}",
                            title = user.title
                        )
                    )
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightPinkPurple,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                ),
                enabled = favoriteState !is ResultState.Loading,
            ) {
                Text(
                    text = if(isOnFavorite) stringResource(id = R.string.remove_favorite) else stringResource(id = R.string.add_favorite),
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UserContactItem(
    user: UserDetailResponse,
    modifier: Modifier = Modifier
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        ContactItem(
            icon = BootstrapIcons.Filled.Envelope,
            value = user.email,
            url = "mailto:${user.email}",
            iconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            textColor = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .weight(1.7f)
                .align(Alignment.CenterVertically)
        )
        ContactItem(
            icon = BootstrapIcons.Filled.Telephone,
            value = user.phone,
            url = user.phone,
            clickable = false,
            iconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            textColor = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        ContactItem(
            icon = BootstrapIcons.Filled.Geo,
            value = "${user.location.street} - ${user.location.city} ${user.location.state} / ${user.location.country}",
            url = user.location.street,
            clickable = false,
            iconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            textColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
@Composable
fun UserPostsContent(
    posts: LazyPagingItems<Posts>,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .padding(top = 8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.posts),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(posts.itemCount) { index ->
                    val post = posts[index]
                    if (post != null) {
                        PostCard(
                            picture = post.image,
                            likes = post.likes,
                            tags = post.tags,
                            text = post.text,
                            publishDate = post.publishDate
                        )
                    }
                }
    
                posts.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item {
                                LoadingIndicator(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                            }
                        }

                        loadState.refresh is LoadState.Error -> {
                            val error = posts.loadState.refresh as LoadState.Error
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    ErrorIndicator(
                                        modifier = Modifier,
                                        error = error.error.localizedMessage!!,
                                        onClick = { retry() }
                                    )
                                }
                            }
                        }

                        loadState.append is LoadState.Loading -> {
                            item {
                                LoadingIndicator(
                                    diameter = 25.dp,
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                            }
                        }

                        loadState.append is LoadState.Error -> {
                            val error = posts.loadState.append as LoadState.Error
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    ErrorMessage(
                                        modifier = Modifier
                                            .width(300.dp),
                                        orientation = Orientation.Vertical,
                                        error = error.error.localizedMessage!!,
                                        onClick = { retry() }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(name = "Success", showBackground = true)
fun DetailSuccessScreenPreview() {
    GradientBackground {
        ProfileTheme {
            val result = ResultState.Success(Helper.getDummyUserDetail())
            val userState = MutableStateFlow<ResultState<UserDetailResponse>>(result)
            val user by userState.collectAsState()
            val postData = Helper.getDummyUserPosts()

            val posts = flowOf(PagingData.from(postData)).collectAsLazyPagingItems()
            
            DetailScreen(
                user = user,
                posts = posts,
                isOnFavorite = true,
                onFavoriteClicked = {},
                favoriteState = ResultState.Success(emptyList())
            )
        }
    }
}

@Composable
@Preview(name = "Loading", showBackground = true)
fun DetailScreenLoadingPreview() {
    GradientBackground {
        ProfileTheme {
            val result = ResultState.Loading
            val userState = MutableStateFlow<ResultState<UserDetailResponse>>(result)
            val user by userState.collectAsState()
            val postData = Helper.getDummyUserPosts()

            val posts = flowOf(PagingData.from(postData)).collectAsLazyPagingItems()


            DetailScreen(
                user = user,
                posts = posts,
                isOnFavorite = false,
                onFavoriteClicked = {},
                favoriteState = ResultState.Success(emptyList())
            )
        }
    }
}

@Composable
@Preview(name = "Error", showBackground = true)
fun DetailScreenErrorPreview() {
    GradientBackground {
        ProfileTheme {
            val result = ResultState.Error("An Error Occured")
            val userState = MutableStateFlow<ResultState<UserDetailResponse>>(result)
            val user by userState.collectAsState()
            val postData = Helper.getDummyUserPosts()

            val posts = flowOf(PagingData.from(postData)).collectAsLazyPagingItems()


            DetailScreen(
                user = user,
                posts = posts,
                isOnFavorite = false,
                onFavoriteClicked = {},
                favoriteState = ResultState.Success(emptyList())
            )
        }
    }
}