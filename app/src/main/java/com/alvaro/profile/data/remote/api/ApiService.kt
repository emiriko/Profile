package com.alvaro.profile.data.remote.api

import com.alvaro.profile.data.remote.response.GetUsersResponse
import com.alvaro.profile.data.remote.response.UserDetailResponse
import com.alvaro.profile.data.remote.response.UserPostsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("user")
    suspend fun getUsers(
        @Query("page") page: Int = 0,
        @Query("limit") limit: Int = 10,
    ): GetUsersResponse

    @GET("user/{userId}")
    suspend fun getUserDetail(
        @Path("userId") userId: String,
    ): UserDetailResponse

    @GET("user/{userId}/post")
    suspend fun getUserPosts(
        @Path("userId") userId: String,
        @Query("page") page: Int = 0,
        @Query("limit") limit: Int = 10,
    ): UserPostsResponse
}