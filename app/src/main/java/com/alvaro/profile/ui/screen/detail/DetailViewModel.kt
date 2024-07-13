package com.alvaro.profile.ui.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alvaro.profile.data.remote.response.Posts
import com.alvaro.profile.data.remote.response.UserDetailResponse
import com.alvaro.profile.data.repository.DetailRepository
import com.alvaro.profile.data.state.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class DetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val detailRepository: DetailRepository
) : ViewModel() {

    private val userId: String? = savedStateHandle["userId"]

    private val _user = MutableStateFlow<ResultState<UserDetailResponse>>(ResultState.Loading)
    val user
        get() = _user

    private val _posts = MutableStateFlow<PagingData<Posts>>(PagingData.empty())
    val posts
        get() = _posts

    init {
        if (userId != null) {
            getUserDetail()
            getUserPosts()
        } else {
            _user.value = ResultState.Error("User not found.")
        }
    }

    private fun getUserDetail() {
        viewModelScope.launch {
            _user.value = ResultState.Loading
            val userResponse = detailRepository.getUserDetail(userId as String)
            _user.value = userResponse
        }
    }

    private fun getUserPosts() {
        viewModelScope.launch {
            detailRepository.getUserPosts(userId as String)
                .cachedIn(viewModelScope)
                .distinctUntilChanged()
                .collect { data ->
                    _posts.value = data
                }
        }
    }
}