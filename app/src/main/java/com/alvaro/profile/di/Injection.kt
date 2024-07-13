package com.alvaro.profile.di

import android.content.Context
import com.alvaro.profile.data.local.room.FavoriteDatabase
import com.alvaro.profile.data.remote.api.ApiConfig
import com.alvaro.profile.data.repository.DetailRepository
import com.alvaro.profile.data.repository.FavoriteRepository
import com.alvaro.profile.data.repository.ProfileRepository
import com.alvaro.profile.data.repository.UserRepository

object Injection {
    fun getProfileRepository(): ProfileRepository {
        return ProfileRepository.getInstance()
    }

    fun getUserRepository(): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService)
    }

    fun getDetailRepository(): DetailRepository {
        val apiService = ApiConfig.getApiService()
        return DetailRepository.getInstance(apiService)
    }

    fun getFavoriteRepository(context: Context): FavoriteRepository {
        val favoriteDatabase = FavoriteDatabase.getInstance(context)
        val favoriteDao = favoriteDatabase.favoriteDao()

        return FavoriteRepository.getInstance(favoriteDao)
    }
}