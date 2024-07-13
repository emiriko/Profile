package com.alvaro.profile.data.local.datasource

import com.alvaro.profile.R
import com.alvaro.profile.data.local.entity.ContactInformation
import com.alvaro.profile.data.local.entity.ProfileEntity
import com.wiryadev.bootstrapiconscompose.BootstrapIcons
import com.wiryadev.bootstrapiconscompose.bootstrapicons.Normal
import com.wiryadev.bootstrapiconscompose.bootstrapicons.normal.Envelope
import com.wiryadev.bootstrapiconscompose.bootstrapicons.normal.Github
import com.wiryadev.bootstrapiconscompose.bootstrapicons.normal.Instagram
import com.wiryadev.bootstrapiconscompose.bootstrapicons.normal.Linkedin

object ProfileDataSource {
    val profile = ProfileEntity(
        name = "Alvaro Austin",
        profileImage = R.drawable.picture_no_bg,
        email = "alvaro.austin@ui.ac.id",
        university = "Universitas Indonesia",
        description = "Inquisitive, energetic, skilled and knowledgeable computer science student " +
                "with extensive knowledge of programming. Seeking to leverage solid skills in collaboration " +
                "and communication. A committed programmer with the intention to contribute " +
                "my expertise to be a dependable team member. Excited to find " +
                "opportunities to improve myself.",
        contacts = listOf(
            ContactInformation(
                icon = BootstrapIcons.Normal.Instagram,
                value = "@notalvaroaustin",
                url = "https://www.instagram.com/notalvaroaustin"
            ),
            ContactInformation(
                icon = BootstrapIcons.Normal.Linkedin,
                value = "@alvaro.austin",
                url = "https://www.linkedin.com/in/alvaro-austin"
            ),
            ContactInformation(
                icon = BootstrapIcons.Normal.Github,
                value = "emiriko",
                url = "https://github.com/emiriko"
            ),
            ContactInformation(
                icon = BootstrapIcons.Normal.Envelope,
                value = "itsrealalvaro",
                url = "mailto:itsrealalvaro@gmail.com"
            )
        )
    )
}