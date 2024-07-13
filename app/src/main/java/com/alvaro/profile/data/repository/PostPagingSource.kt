package com.alvaro.profile.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alvaro.profile.data.remote.api.ApiService
import com.alvaro.profile.data.remote.response.Posts
import retrofit2.HttpException
import java.io.IOException

class PostPagingSource(private val apiService: ApiService, private val userId: String) : PagingSource<Int, Posts>() {
    override fun getRefreshKey(state: PagingState<Int, Posts>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Posts> {
        return try {
            val currentPage = params.key ?: 0
            val response = apiService.getUserPosts(
                userId = userId,
                page = currentPage,
                limit = params.loadSize
            )
            
            LoadResult.Page(
                data = response.data,
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = if (response.data.isEmpty()) null else response.page + 1
            )
            
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}