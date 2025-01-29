package com.amirnlz.chaoswallet

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun TouchSurface(modifier: Modifier = Modifier, viewModel: TouchViewModel) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val leftTap = state.maxTaps - state.touches.size
    val targetProgress =
        if (state.maxTaps > 0) (state.touches.size.toFloat() / state.maxTaps) else 0f

    val animatedProgress by animateFloatAsState(
        targetValue = targetProgress,
        animationSpec = tween(durationMillis = 300),
        label = "Progress Animation"
    )

    LinearProgressIndicator(
        progress = { animatedProgress },
        modifier = Modifier
            .fillMaxWidth()
            .height(15.dp),
        color = Color.Blue,
        trackColor = Color.White,
    )
    Canvas(
        modifier = modifier
            .clipToBounds()
            .pointerInput(key1 = true) {
                detectTapGestures { offset: Offset ->
                    viewModel.onAction(TouchAction.OnTap(offset))
                }
            }
    ) {
        state.touches.forEach {
            drawCircle(color = Color.Black, radius = 8f, center = it)
        }
    }
}