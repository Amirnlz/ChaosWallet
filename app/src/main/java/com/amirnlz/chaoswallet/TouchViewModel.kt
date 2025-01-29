package com.amirnlz.chaoswallet

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.bitcoinj.core.Address
import org.bitcoinj.core.ECKey
import org.bitcoinj.core.NetworkParameters
import org.bitcoinj.params.TestNet2Params

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

    // Use Bitcoin testnet for development
    private val networkParams: NetworkParameters = TestNet2Params.get()

    fun onAction(action: TouchAction) {
        when (action) {
            is TouchAction.OnTap -> onTap(action.offset)
            TouchAction.GenerateWallet -> generateWallet()
        }
    }

    private fun onTap(offset: Offset) {
        _state.update { currentState ->
            val newTouches = (currentState.touches + offset).takeLast(currentState.maxTaps)

            // Auto-generate wallet when max taps reached
            if (newTouches.size == currentState.maxTaps) {
                generateWallet()
            }

            currentState.copy(touches = newTouches)
        }
    }

    private fun generateWallet() {
        val ecKey = ECKey() // Generate new key pair
        val address = Address.fromP2SHHash(networkParams, ecKey.pubKeyHash).toString()

        _state.update {
            it.copy(
                walletAddress = address,
                privateKey = ecKey.getPrivateKeyEncoded(networkParams).toString(),
                publicKey = ecKey.publicKeyAsHex
            )
        }
    }
}