package com.amirnlz.chaoswallet

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun TouchSurface(
    modifier: Modifier = Modifier, onNextScreen: () -> Unit, viewModel: TouchViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isButtonEnabled: Boolean = state.touches.size == state.maxTaps

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    viewModel.onAction(TouchAction.OnTap(offset))
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.matchParentSize()
        ) {
            state.touches.forEach {
                drawCircle(color = Color.Black, radius = 8f, center = it)
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "${state.touches.size}/${state.maxTaps}",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Tap anywhere to generate entropy",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onNextScreen,
                enabled = isButtonEnabled,
            ) {
                Text("Display Wallet Address")
            }
        }

        LinearProgressIndicator(
            progress = { state.touches.size.toFloat() / state.maxTaps },
            trackColor = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(8.dp)
        )
    }
}
