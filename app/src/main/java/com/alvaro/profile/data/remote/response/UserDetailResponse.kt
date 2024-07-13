package com.alvaro.profile.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(

    @field:SerializedName("firstName")
    val firstName: String,

    @field:SerializedName("lastName")
    val lastName: String,

    @field:SerializedName("gender")
    val gender: String,

    @field:SerializedName("phone")
    val phone: String,

    @field:SerializedName("dateOfBirth")
    val dateOfBirth: String,

    @field:SerializedName("location")
    val location: Location,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("updatedDate")
    val updatedDate: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("picture")
    val picture: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("registerDate")
    val registerDate: String
)

data class Location(

    @field:SerializedName("country")
    val country: String,

    @field:SerializedName("city")
    val city: String,

    @field:SerializedName("street")
    val street: String,

    @field:SerializedName("timezone")
    val timezone: String,

    @field:SerializedName("state")
    val state: String
)
