package com.alvaro.profile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.alvaro.profile.ui.theme.Gradient

@Composable
fun GradientBackground(
    colorList: List<Color> = Gradient,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    ) {
    Box(
        modifier = modifier
            .background(brush = Brush.verticalGradient(colorList))
    ) {
        content()
    }
}