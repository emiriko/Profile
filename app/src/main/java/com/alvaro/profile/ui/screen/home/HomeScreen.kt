package com.alvaro.profile.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.alvaro.profile.R
import com.alvaro.profile.data.remote.response.UserPreview
import com.alvaro.profile.data.state.ResultState
import com.alvaro.profile.ui.components.ErrorIndicator
import com.alvaro.profile.ui.components.ErrorMessage
import com.alvaro.profile.ui.components.GradientBackground
import com.alvaro.profile.ui.components.LoadingIndicator
import com.alvaro.profile.ui.components.NotFound
import com.alvaro.profile.ui.components.SearchBar
import com.alvaro.profile.ui.components.UserItem
import com.alvaro.profile.ui.theme.ProfileTheme
import com.alvaro.profile.utils.Helper
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreen(
    users: LazyPagingItems<UserPreview>,
    searchQuery: TextFieldValue,
    onSearchQueryChanged: (TextFieldValue) -> Unit,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
    ) {
        this.constraints

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SearchBar(
                searchText = searchQuery,
                onSearchTextChanged = {
                    onSearchQueryChanged(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
            )

            if (users.itemCount > 0 ||
                !users.loadState.isIdle ||
                users.loadState.hasError
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = false),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    items(users.itemCount) { index ->
                        val item = users[index]
                        if (item != null) {
                            UserItem(
                                picture = item.picture,
                                fullName = "${item.firstName} ${item.lastName}",
                                title = item.title,
                                id = item.id,
                                onClick = onClick,
                            )
                        }
                    }

                    users.apply {
                        when {
                            loadState.refresh is LoadState.Loading -> {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillParentMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        LoadingIndicator(
                                            modifier = Modifier
                                                .wrapContentWidth(Alignment.CenterHorizontally)
                                        )
                                    }
                                }
                            }

                            loadState.refresh is LoadState.Error -> {
                                val error = users.loadState.refresh as LoadState.Error
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillParentMaxSize(),
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
                                val error = users.loadState.append as LoadState.Error
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillParentMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        ErrorMessage(
                                            modifier = Modifier
                                                .padding(horizontal = 14.dp),
                                            error = error.error.localizedMessage!!,
                                            onClick = { retry() }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    NotFound(
                        notFoundText = stringResource(id = R.string.no_match),
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    GradientBackground {
        ProfileTheme {
            val result = ResultState.Success(Helper.getDummyUsers())
            val users = flowOf(PagingData.from(result.data.data)).collectAsLazyPagingItems()

            HomeScreen(
                users = users,
                searchQuery = TextFieldValue(""),
                onSearchQueryChanged = {},
                onClick = {}
            )
        }
    }
}