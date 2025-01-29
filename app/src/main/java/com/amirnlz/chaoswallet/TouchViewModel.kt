package com.amirnlz.chaoswallet

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class TouchState(
    val touches: List<Offset> = emptyList(),
    val maxTaps: Int = 30,
    )

sealed interface TouchAction {
    data class OnTap(val offset: Offset) : TouchAction
}

class TouchViewModel : ViewModel() {
    private val _state = MutableStateFlow(TouchState())

    val state = _state.asStateFlow()

    fun onAction(action: TouchAction) {
        when (action) {
            is TouchAction.OnTap -> onTap(action.offset)
        }
    }

    private fun onTap(offset: Offset) {
        _state.update { it.copy(touches = it.touches + offset) }
    }

}