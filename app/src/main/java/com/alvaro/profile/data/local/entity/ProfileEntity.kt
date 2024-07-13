package com.alvaro.profile.data.local.entity

import androidx.compose.ui.graphics.vector.ImageVector

data class ProfileEntity(
    val name: String,
    val profileImage: Int,
    val email: String,
    val university: String,
    val description: String,
    val contacts: List<ContactInformation>
)

data class ContactInformation (
    val icon: ImageVector,
    val value: String,
    val url: String,
)