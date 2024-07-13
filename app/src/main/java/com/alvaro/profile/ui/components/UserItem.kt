package com.alvaro.profile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.SubcomposeAsyncImage
import com.alvaro.profile.R
import com.alvaro.profile.ui.theme.LightPink
import com.alvaro.profile.ui.theme.ProfileTheme
import com.alvaro.profile.utils.Helper
import com.alvaro.profile.utils.getGender
import com.wiryadev.bootstrapiconscompose.BootstrapIcons
import com.wiryadev.bootstrapiconscompose.BsIcon
import com.wiryadev.bootstrapiconscompose.bootstrapicons.Filled
import com.wiryadev.bootstrapiconscompose.bootstrapicons.Normal
import com.wiryadev.bootstrapiconscompose.bootstrapicons.normal.ChevronBarRight
import com.wiryadev.bootstrapiconscompose.bootstrapicons.normal.ChevronRight

@Composable
fun UserItem(
    picture: String,
    fullName: String,
    title: String,
    id: String,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick(id)
            }
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            SubcomposeAsyncImage(
                model = picture,
                loading = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(), // Fill the parent Box
                        contentAlignment = Alignment.Center // Center the loading indicator
                    ) {
                        LoadingIndicator(
                            diameter = 30.dp
                        )
                    }
                },
                contentDescription = stringResource(id = R.string.profile),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(60.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = fullName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = title.getGender(),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        BsIcon(
            bsIcon = BootstrapIcons.Normal.ChevronRight, 
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            size = 25.dp,
        )
    }
}

@Preview
@Composable
fun UserItemPreview() {
    GradientBackground {
        ProfileTheme {
            val user = Helper.getDummyUsers().data.first()
            UserItem(
                picture = user.picture,
                fullName = "${user.firstName} ${user.lastName}",
                title = user.title,
                id = user.id,
                onClick = {}
            )
        }
    }
}