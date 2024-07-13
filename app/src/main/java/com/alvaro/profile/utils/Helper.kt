package com.alvaro.profile.utils

import com.alvaro.profile.data.local.entity.FavoriteEntity
import com.alvaro.profile.data.remote.response.GetUsersResponse
import com.alvaro.profile.data.remote.response.Location
import com.alvaro.profile.data.remote.response.Owner
import com.alvaro.profile.data.remote.response.Posts
import com.alvaro.profile.data.remote.response.UserDetailResponse
import com.alvaro.profile.data.remote.response.UserPreview

object Helper {
    fun getDummyUsers(): GetUsersResponse {
        return GetUsersResponse(
            total = 5,
            data = listOf(
                UserPreview(
                    firstName = "Jessica",
                    lastName = "Cabrera",
                    id = "1",
                    title = "miss",
                    picture = "https://randomuser.me/api/portraits/women/58.jpg"
                ),
                UserPreview(
                    firstName = "Alvaro",
                    lastName = "Austin",
                    id = "2",
                    title = "mr",
                    picture = "https://randomuser.me/api/portraits/med/men/80.jpg"
                ),
                UserPreview(
                    firstName = "Bryant",
                    lastName = "Kobe",
                    id = "3",
                    title = "mr",
                    picture = "https://randomuser.me/api/portraits/med/men/80.jpg"
                ),
            ),
            limit = 5,
            page = 0,
        )
    }


    fun getDummyUserDetail(): UserDetailResponse {
        return UserDetailResponse(
                id = "60d0fe4f5311236168a109ca",
                title = "ms",
                firstName = "Sara",
                lastName = "Andersen",
                picture = "https://randomuser.me/api/portraits/women/58.jpg",
                gender = "female",
                email = "sara.andersen@example.com",
                dateOfBirth = "1996-04-30T19:26:49.610Z",
                phone = "92694011",
                location = Location(
                    street = "9614, SÃ¸ndermarksvej",
                    city = "Kongsvinger",
                    state = "Nordjylland",
                    country = "Denmark",
                    timezone = "-9:00"
                ),
                registerDate= "2021-06-21T21:02:07.374Z",
                updatedDate= "2021-06-21T21:02:07.374Z"
        )
    }
    
    fun getDummyUserPosts(): List<Posts> {
        return listOf(
            Posts(
                id = "60d0fe4f5311236168a109ca",
                image = "https://img.dummyapi.io/photo-1555897209-208b67f652c5.jpg",
                likes = 52,
                tags = listOf(
                    "dog",
                    "animal",
                    "canine"
                ),
                text = "two brown and black dogs sitting on green grass fi...",
                publishDate =  "2020-02-27T16:08:29.562Z",
                owner = Owner(
                    id = "60d0fe4f5311236168a109ca",
                    title = "ms",
                    firstName = "Sara",
                    lastName = "Andersen",
                    picture = "https://randomuser.me/api/portraits/women/58.jpg"
                )
            ),
            Posts(
                id = "60d21bda67d0d8992e610e39",
                image = "https://img.dummyapi.io/photo-1551585895-81567c4fd16c.jpg",
                likes = 0,
                tags = listOf(
                    "animal",
                    "pet",
                    "mammal"
                ),
                text = "long-coat brown and white dog in close-up photogra...",
                publishDate =  "2020-02-10T21:04:06.213Z",
                owner = Owner(
                    id = "60d0fe4f5311236168a109ca",
                    title = "ms",
                    firstName = "Sara",
                    lastName = "Andersen",
                    picture = "https://randomuser.me/api/portraits/women/58.jpg"
                )
            ),
        )
    }
    
    fun getDummyFavorites(): List<FavoriteEntity> {
        return listOf(
            FavoriteEntity(
                fullName = "Jessica Cabrera",
                id = "1",
                title = "Software Engineer",
                picture = "https://randomuser.me/api/portraits/women/58.jpg"
            ),
            FavoriteEntity(
                fullName = "Alvaro Austin",
                id = "2",
                title = "Software Engineer",
                picture = "https://randomuser.me/api/portraits/med/men/80.jpg"
            ),
            FavoriteEntity(
                fullName = "Bryant Kobe",
                id = "3",
                title = "Basketball Player",
                picture = "https://randomuser.me/api/portraits/med/men/80.jpg"
            )
        )
    }
}