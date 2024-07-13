package com.alvaro.profile.data.repository

import com.alvaro.profile.data.local.entity.FavoriteEntity
import com.alvaro.profile.data.local.room.FavoriteDao
import com.alvaro.profile.data.remote.api.ApiService

class FavoriteRepository(
    private val favoriteDao: FavoriteDao
) {
    suspend fun getFavorites() = favoriteDao.getFavorites()
    
    suspend fun insertFavorite(favorite: FavoriteEntity) = favoriteDao.insertFavorite(favorite)
    
    suspend fun deleteFromFavorites(userId: String) = favoriteDao.deleteFromFavorites(userId)

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null

        fun getInstance(favoriteDao: FavoriteDao): FavoriteRepository =
            instance ?: synchronized(this) {
                FavoriteRepository(favoriteDao).also {
                    instance = it
                }
            }
    }
}