package com.lukaszfabia.ferby

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lukaszfabia.ferby.features.shrinesecrets.ShrineSecretsView
import com.lukaszfabia.ferby.features.shrinesecrets.ShrineSecretsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainHost() {
    val viewModel = koinViewModel<ShrineSecretsViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize().safeContentPadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ShrineSecretsView(state = state)
        }
    }
}