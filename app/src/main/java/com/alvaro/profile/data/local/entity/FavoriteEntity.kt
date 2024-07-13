package com.alvaro.profile.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity (
    @PrimaryKey
    val id: String,
    
    @ColumnInfo(name = "picture")
    val picture: String,

    @ColumnInfo(name = "full_name")
    val fullName: String,

    @ColumnInfo(name = "title")
    val title: String,
)