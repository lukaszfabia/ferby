package com.lukaszfabia.ferby.feature.shrinesecrets

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lukaszfabia.ferby.common.navigation.FerbyRoute
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.shrineSecretsFlow() {
    composable<ShrineSecretsRoute> {
        val viewModel = koinViewModel<ShrineSecretsViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        ShrineSecretsView(state = state, eventHandler = viewModel::handleEvent)
    }
}

@Serializable
object ShrineSecretsRoute: FerbyRoute