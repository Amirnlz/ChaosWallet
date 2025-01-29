package com.amirnlz.chaoswallet

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun TouchSurface(modifier: Modifier = Modifier, viewModel: TouchViewModel) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    Canvas(
        modifier = modifier
            .clipToBounds()
            .pointerInput(
                key1 = true
            ) {
                detectTapGestures { offset: Offset -> viewModel.onAction(TouchAction.OnTap(offset)) }
            }
    ) {
        state.touches.forEach { drawCircle(color = Color.Black, radius = 8f, center = it) }


    }
}