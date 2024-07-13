package com.alvaro.profile.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alvaro.profile.data.remote.api.ApiService
import com.alvaro.profile.data.remote.response.UserPreview
import retrofit2.HttpException
import java.io.IOException

class UserPagingSource(private val apiService: ApiService) : PagingSource<Int, UserPreview>() {
    override fun getRefreshKey(state: PagingState<Int, UserPreview>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserPreview> {
        return try {
            val currentPage = params.key ?: 0
            val response = apiService.getUsers(
                page = currentPage, 
                limit = params.loadSize
            )
            
            LoadResult.Page(
                data = response.data,
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = if (response.data.isEmpty()) null else response.page + 1
            )
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}