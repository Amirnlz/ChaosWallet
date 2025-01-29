package com.amirnlz.chaoswallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amirnlz.chaoswallet.ui.theme.ChaosWalletTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChaosWalletTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TouchSurface(
                        Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        viewModel<TouchViewModel>()
                    )
                }
            }
        }
    }
}