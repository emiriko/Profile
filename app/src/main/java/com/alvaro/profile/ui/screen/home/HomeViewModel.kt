package com.alvaro.profile.ui.screen.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.alvaro.profile.data.remote.response.GetUsersResponse
import com.alvaro.profile.data.remote.response.UserPreview
import com.alvaro.profile.data.repository.UserRepository
import com.alvaro.profile.data.state.ResultState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepository: UserRepository
): ViewModel() {
    private val _users = MutableStateFlow<PagingData<UserPreview>>(PagingData.empty())

    private val _searchQuery = MutableStateFlow(TextFieldValue("")) 
    val searchQuery
        get() = _searchQuery
    
    @OptIn(ExperimentalCoroutinesApi::class)
    val filteredUser = _searchQuery.flatMapLatest {query -> 
        _users.map { pagingData ->
            pagingData.filter { user ->
                user.firstName.contains(query.text, ignoreCase = true) ||
                user.lastName.contains(query.text, ignoreCase = true)  || 
                (user.firstName + " " + user.lastName).contains(query.text, ignoreCase = true)    
            }
        }
    }
    
    init {
        getUsers()
    }

    fun getUsers() {
        viewModelScope.launch { 
           userRepository
               .getUsers()
               .distinctUntilChanged()
               .cachedIn(viewModelScope)
               .collect { _users.value = it }
        }
    }
    
    fun updateSearchQuery(query: TextFieldValue) {
        Log.d("HomeViewModel", "updateSearchQuery: $query")
        _searchQuery.value = query
    }
}