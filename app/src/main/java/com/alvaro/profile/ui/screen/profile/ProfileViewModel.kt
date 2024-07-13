package com.alvaro.profile.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvaro.profile.data.local.entity.ProfileEntity
import com.alvaro.profile.data.repository.ProfileRepository
import com.alvaro.profile.data.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel (
    private val profileRepository: ProfileRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState
        get() = _uiState
    
    private val _profileData = MutableStateFlow(ProfileEntity(
        name = "",
        email = "",
        profileImage = 0,
        university = "",
        description = "",
        contacts = emptyList()
    ))
    val profileData
        get() = _profileData
    
    init {
        getProfile()
    }
    
    private fun getProfile() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val data = profileRepository.getProfile()
            _profileData.value = data
            _uiState.value = UiState.Success
        }
    }
}