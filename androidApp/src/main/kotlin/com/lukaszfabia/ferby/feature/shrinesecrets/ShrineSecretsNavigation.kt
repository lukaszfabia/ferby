package com.lukaszfabia.ferby.features.shrinesecrets

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.shrineSecretsFlow() {
    composable<ShrineSecretsRoute> {
        val viewModel = koinViewModel<ShrineSecretsViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        ShrineSecretsView(state)
    }
}

@Serializable
object ShrineSecretsRoute