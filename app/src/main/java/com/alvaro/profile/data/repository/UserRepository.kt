package com.alvaro.profile.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alvaro.profile.data.remote.api.ApiService
import com.alvaro.profile.data.remote.response.UserPreview
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val apiService: ApiService
) {
    fun getUsers(): Flow<PagingData<UserPreview>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { UserPagingSource(apiService) })
            .flow
    }
    
    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(apiService: ApiService): UserRepository =
            instance ?: synchronized(this) {
                UserRepository(apiService).also {
                    instance = it
                }
            }
    }
}