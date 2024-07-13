package com.alvaro.profile.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.alvaro.profile.di.Injection
import com.alvaro.profile.ui.screen.detail.DetailViewModel
import com.alvaro.profile.ui.screen.favorite.FavoriteViewModel
import com.alvaro.profile.ui.screen.home.HomeViewModel
import com.alvaro.profile.ui.screen.profile.ProfileViewModel

class ViewModelFactory(private val context: Context): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras,
    ): T {
        val savedStateHandle = extras.createSavedStateHandle()
        
        return when (modelClass) {
            ProfileViewModel::class.java -> ProfileViewModel(Injection.getProfileRepository()) as T
            HomeViewModel::class.java -> HomeViewModel(Injection.getUserRepository()) as T
            DetailViewModel::class.java -> DetailViewModel(savedStateHandle, Injection.getDetailRepository()) as T
            FavoriteViewModel::class.java -> FavoriteViewModel(Injection.getFavoriteRepository(context)) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}