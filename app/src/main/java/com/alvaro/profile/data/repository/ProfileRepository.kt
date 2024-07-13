package com.alvaro.profile.data.repository

import com.alvaro.profile.data.local.datasource.ProfileDataSource
import com.alvaro.profile.data.local.entity.ProfileEntity

class ProfileRepository {
    fun getProfile(): ProfileEntity {
        return ProfileDataSource.profile
    }

    companion object {
        @Volatile
        private var instance: ProfileRepository? = null

        fun getInstance(): ProfileRepository =
            instance ?: synchronized(this) {
                ProfileRepository().also {
                    instance = it
                }
            }
    }
}