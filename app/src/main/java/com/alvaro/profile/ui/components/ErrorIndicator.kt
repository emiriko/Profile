package com.alvaro.profile.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alvaro.profile.R
import com.wiryadev.bootstrapiconscompose.BootstrapIcons
import com.wiryadev.bootstrapiconscompose.bootstrapicons.Normal
import com.wiryadev.bootstrapiconscompose.bootstrapicons.normal.ShieldX

@Composable
fun ErrorIndicator(
    error: String,
    iconSize: Dp = 60.dp,
    hasButton: Boolean = false,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ){
        Icon(
            imageVector = BootstrapIcons.Normal.ShieldX,
            contentDescription = "Error",
            tint = Color.Red,
            modifier = Modifier
                .size(iconSize),
        )
        Text(
            text = error,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        if (hasButton) {
            Button(onClick = onClick) {
                Text(
                    text = "Retry",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}


@Composable
fun ErrorMessage(
    error: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    orientation: Orientation = Orientation.Horizontal
) {
    val button: @Composable () -> Unit = {
        OutlinedButton(
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.onSurfaceVariant),
            onClick = onClick,
        ) {
            Text(
                text = stringResource(id = R.string.retry),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
    
    if (orientation == Orientation.Vertical) {
        Column(
            modifier = modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            button()
        }
    } else {
        Row(
            modifier = modifier.padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(1f),
                maxLines = 2,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
            button()
        }
    }
}

enum class Orientation {
    Vertical, Horizontal
}
