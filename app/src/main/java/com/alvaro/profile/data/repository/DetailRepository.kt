package com.alvaro.profile.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alvaro.profile.data.remote.api.ApiService
import com.alvaro.profile.data.remote.response.Posts
import com.alvaro.profile.data.remote.response.UserDetailResponse
import com.alvaro.profile.data.state.ResultState
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException

class DetailRepository(
    private val apiService: ApiService
) {
    suspend fun getUserDetail(userId: String): ResultState<UserDetailResponse> {
        return try {
            ResultState.Success(apiService.getUserDetail(userId))
        } catch (e: HttpException) {
            ResultState.Error(e.message ?: "An error occurred.")
        } catch (e: IOException) {
            ResultState.Error(e.message ?: "An error occurred.")
        }
    }

    fun getUserPosts(userId: String): Flow<PagingData<Posts>> {
        return Pager(
            config = PagingConfig(pageSize = 5, enablePlaceholders = false),
            pagingSourceFactory = { PostPagingSource(apiService, userId) }
        ).flow
    }

    companion object {
        @Volatile
        private var instance: DetailRepository? = null

        fun getInstance(apiService: ApiService): DetailRepository =
            instance ?: synchronized(this) {
                DetailRepository(apiService).also {
                    instance = it
                }
            }
    }
}