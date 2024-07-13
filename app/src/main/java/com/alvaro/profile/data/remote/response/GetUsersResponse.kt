package com.alvaro.profile.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetUsersResponse(

    @field:SerializedName("total")
    val total: Int,

    @field:SerializedName("data")
    val data: List<UserPreview>,

    @field:SerializedName("limit")
    val limit: Int,

    @field:SerializedName("page")
    val page: Int
)

data class UserPreview(

    @field:SerializedName("firstName")
    val firstName: String,

    @field:SerializedName("lastName")
    val lastName: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("picture")
    val picture: String
)
