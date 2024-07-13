package com.alvaro.profile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ehsanmsz.mszprogressindicator.progressindicator.BallClipRotateProgressIndicator

@Composable
fun LoadingIndicator(
    backgroundColor: Color = Color.Transparent,
    loadingColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    diameter: Dp = 60.dp,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        BallClipRotateProgressIndicator(
            color = loadingColor,
            minDiameter = diameter,
            maxDiameter = diameter,
            modifier = Modifier
                .background(backgroundColor)
        )
    }
}