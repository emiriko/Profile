package com.alvaro.profile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.alvaro.profile.R
import com.alvaro.profile.ui.theme.LightPink
import com.alvaro.profile.ui.theme.LightPinkPurple
import com.alvaro.profile.utils.parseDate
import com.wiryadev.bootstrapiconscompose.BootstrapIcons
import com.wiryadev.bootstrapiconscompose.BsIcon
import com.wiryadev.bootstrapiconscompose.bootstrapicons.Filled
import com.wiryadev.bootstrapiconscompose.bootstrapicons.filled.Heart
import com.wiryadev.bootstrapiconscompose.bootstrapicons.filled.SuitHeart
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun PostCard(
    picture: String,
    likes: Int,
    tags: List<String>,
    text: String,
    publishDate: String,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .width(300.dp)
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onSurfaceVariant)
                .padding(16.dp)
        ) {
            SubcomposeAsyncImage(
                model = picture,
                loading = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingIndicator(
                            loadingColor = LightPinkPurple,
                            diameter = 30.dp,
                        )
                    }
                },
                contentDescription = stringResource(id = R.string.post_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = LightPinkPurple
            )

            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                BsIcon(
                    bsIcon = BootstrapIcons.Filled.Heart,
                    tint = Color.Red,
                    size = 20.dp
                )
                Text(
                    text = stringResource(id = R.string.likes, likes),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(bottom = 8.dp),
                    color = LightPinkPurple,
                )
            }
            
            Row(
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                tags.forEach { tag ->
                    Text(
                        text = tag,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .background(LightPinkPurple, RoundedCornerShape(12.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            val formattedDate = publishDate.parseDate()

            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = stringResource(id = R.string.published_on, formattedDate),
                style = MaterialTheme.typography.bodySmall,
                color = LightPink,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}