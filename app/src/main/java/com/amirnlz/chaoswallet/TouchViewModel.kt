package com.amirnlz.chaoswallet

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bitcoinj.core.Address
import org.bitcoinj.core.ECKey
import org.bitcoinj.core.NetworkParameters
import org.bitcoinj.params.MainNetParams

data class TouchState(
    val touches: List<Offset> = emptyList(),
    val maxTaps: Int = 30,
    val walletAddress: String? = null,
    val privateKey: String? = null,
    val publicKey: String? = null
)

sealed interface TouchAction {
    data class OnTap(val offset: Offset) : TouchAction
    data object GenerateWallet : TouchAction
}

class TouchViewModel : ViewModel() {
    private val _state = MutableStateFlow(TouchState())
    val state = _state.asStateFlow()

    private val networkParams: NetworkParameters = MainNetParams.get()

    fun onAction(action: TouchAction) {
        when (action) {
            is TouchAction.OnTap -> onTap(action.offset)
            TouchAction.GenerateWallet -> generateWallet()
        }
    }

    private fun onTap(offset: Offset) {
        _state.update { currentState ->
            val newTouches = (currentState.touches + offset).takeLast(currentState.maxTaps)


            if (newTouches.size == currentState.maxTaps) {
                generateWallet()
            }

            currentState.copy(touches = newTouches)
        }
    }

    private fun generateWallet() {
        viewModelScope.launch {
            try {
                val (ecKey, address) = withContext(Dispatchers.IO) {
                    val key = ECKey()
                    val addr = Address.fromP2SHHash(networkParams, key.pubKeyHash).toString()
                    key to addr
                }


                _state.update {
                    it.copy(
                        walletAddress = address,
                        privateKey = ecKey.getPrivateKeyEncoded(networkParams).toString(),
                        publicKey = ecKey.publicKeyAsHex
                    )
                }
            } catch (e: Exception) {
                // Handle failures (e.g., network errors)
                // Update state to show error message
            }
        }
    }
}