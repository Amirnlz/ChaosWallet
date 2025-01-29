package com.amirnlz.chaoswallet

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WalletDisplay(
    modifier: Modifier = Modifier,
    viewModel: TouchViewModel,
    onReset: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Your Bitcoin Wallet",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        state.walletAddress?.let { address ->
            InfoCard(title = "Address", content = address)
        }

        Spacer(modifier = Modifier.height(16.dp))

        state.publicKey?.let { publicKey ->
            InfoCard(title = "Public Key", content = publicKey)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "⚠️ Never share your private key! ⚠️",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.labelLarge
        )

        state.privateKey?.let { privateKey ->
            InfoCard(title = "Private Key (WIF)", content = privateKey)
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = onReset) {
            Text("Generate New Wallet")
        }
    }
}

@Composable
fun InfoCard(title: String, content: String) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(8.dp)
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(4.dp))
                .padding(8.dp)
        )
    }
}