package com.alvaro.profile.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.SubcomposeAsyncImage
import com.alvaro.profile.R
import com.alvaro.profile.data.local.datasource.ProfileDataSource
import com.alvaro.profile.data.local.entity.ContactInformation
import com.alvaro.profile.data.local.entity.ProfileEntity
import com.alvaro.profile.data.state.UiState
import com.alvaro.profile.ui.components.ContactItem
import com.alvaro.profile.ui.components.GradientBackground
import com.alvaro.profile.ui.components.LoadingIndicator
import com.alvaro.profile.ui.theme.LightPink
import com.alvaro.profile.ui.theme.ProfileTheme
import com.alvaro.profile.ui.theme.PurpleGrey40

@Composable
fun ProfileScreen(
    uiState: UiState,
    profile: ProfileEntity,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        is UiState.Loading -> {
            LoadingIndicator()
        }

        is UiState.Success -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxSize()
            ) {
                SubcomposeAsyncImage(
                    model = profile.profileImage,
                    loading = {
                        LoadingIndicator(
                            diameter = 25.dp
                        )
                    },
                    contentDescription = stringResource(id = R.string.profile),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .offset(y = 60.dp)
                        .zIndex(1f)
                        .clip(CircleShape)
                        .size(160.dp)
                        .background(MaterialTheme.colorScheme.onSecondaryContainer)
                        .wrapContentHeight(align = Alignment.CenterVertically)
                        .wrapContentWidth(align = Alignment.CenterHorizontally),
                )
                ProfileContent(
                    name = profile.name,
                    email = profile.email,
                    university = profile.university,
                    description = profile.description,
                    contacts = profile.contacts,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }

        else -> Unit
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileContent(
    name: String,
    email: String,
    university: String,
    description: String,
    contacts: List<ContactInformation>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .clip(RoundedCornerShape(topStart = 120.dp, topEnd = 120.dp))
            .background(MaterialTheme.colorScheme.onSurfaceVariant)
            .padding(top = 80.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold,
            color = LightPink,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(id = R.string.email_university, email, university),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.ExtraBold,
            color = LightPink,
            textAlign = TextAlign.Center
        )

        Text(
            text = description,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.ExtraBold,
            color = PurpleGrey40,
            textAlign = TextAlign.Center
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp),
        ) {
            repeat(contacts.size) { index ->
                ContactItem(
                    icon = contacts[index].icon,
                    value = contacts[index].value,
                    url = contacts[index].url,
                    modifier = Modifier.weight(1f)
                )
            }
        }

    }
}

@Composable
@Preview(showBackground = true)
fun ProfileScreenPreview() {
    GradientBackground {
        ProfileTheme {
            ProfileScreen(
                uiState = UiState.Success,
                profile = ProfileDataSource.profile
            )
        }
    }
}