package com.isodev.hypermart.presentation.animation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun MaterialContainerTransform(
    targetState: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (targetState) 1f else 0.8f,
        animationSpec = tween(300)
    )
    
    val alpha by animateFloatAsState(
        targetValue = if (targetState) 1f else 0.8f,
        animationSpec = tween(300)
    )

    Box(
        modifier = modifier.graphicsLayer {
            scaleX = scale
            scaleY = scale
            this.alpha = alpha
        }
    ) {
        content()
    }
} 