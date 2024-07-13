package com.alvaro.profile.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserPostsResponse(

	@field:SerializedName("total")
	val total: Int,

	@field:SerializedName("data")
	val data: List<Posts>,

	@field:SerializedName("limit")
	val limit: Int,

	@field:SerializedName("page")
	val page: Int
)

data class Owner(

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

data class Posts(

	@field:SerializedName("owner")
	val owner: Owner,

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("publishDate")
	val publishDate: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("text")
	val text: String,

	@field:SerializedName("likes")
	val likes: Int,

	@field:SerializedName("tags")
	val tags: List<String>
)
