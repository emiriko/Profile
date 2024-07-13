package com.alvaro.profile.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.alvaro.profile.ui.theme.LightPink
import com.alvaro.profile.ui.theme.PurpleGrey40
import com.wiryadev.bootstrapiconscompose.BootstrapIcons
import com.wiryadev.bootstrapiconscompose.BsIcon
import com.wiryadev.bootstrapiconscompose.bootstrapicons.Filled
import com.wiryadev.bootstrapiconscompose.bootstrapicons.Normal
import com.wiryadev.bootstrapiconscompose.bootstrapicons.filled.Envelope
import com.wiryadev.bootstrapiconscompose.bootstrapicons.normal.Envelope

@Composable
fun ContactItem(
    icon: ImageVector, 
    value: String, 
    url: String,
    iconColor: Color = LightPink,
    textColor: Color = PurpleGrey40,
    clickable: Boolean = true,
    modifier: Modifier = Modifier
) {

    val intent: Intent = if (
            icon == BootstrapIcons.Normal.Envelope || 
            icon == BootstrapIcons.Filled.Envelope 
        ) {
        Intent(
            Intent.ACTION_SENDTO,
            Uri.parse("$url?subject=Nice to meet you!&body=Hello, I'm interested in your profile.")
        )
    } else {
        Intent(Intent.ACTION_VIEW, Uri.parse(url))
    }
    
    val context = LocalContext.current

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable (clickable) {
                context.startActivity(intent)
            }
    ) {
        BsIcon(bsIcon = icon, tint = iconColor)
        
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            color = textColor
        )
    }
}