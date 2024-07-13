package com.alvaro.profile.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvaro.profile.data.local.entity.FavoriteEntity
import com.alvaro.profile.data.repository.FavoriteRepository
import com.alvaro.profile.data.state.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {
    val _uiState = MutableStateFlow<ResultState<List<FavoriteEntity>>>(ResultState.Loading)
    val uiState
        get() = _uiState

    init {
        getFavorites()
    }

    fun getFavorites() {
        viewModelScope.launch {
            _uiState.value = ResultState.Loading
            val response = favoriteRepository.getFavorites()
            _uiState.value = ResultState.Success(response)
        }
    }

    fun insertFavorite(favoriteEntity: FavoriteEntity) {
        viewModelScope.launch {
            _uiState.value = ResultState.Loading
            favoriteRepository.insertFavorite(favoriteEntity)
            _uiState.value = ResultState.Success(favoriteRepository.getFavorites())
        }
    }

    fun deleteFromFavorites(userId: String) {
        viewModelScope.launch {
            _uiState.value = ResultState.Loading
            favoriteRepository.deleteFromFavorites(userId)
            _uiState.value = ResultState.Success(favoriteRepository.getFavorites())
        }
    }
}