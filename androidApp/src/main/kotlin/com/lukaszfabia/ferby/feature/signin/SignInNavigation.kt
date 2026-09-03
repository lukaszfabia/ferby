package com.lukaszfabia.ferby.feature.signin

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lukaszfabia.ferby.common.navigation.FerbyRoute
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.signInFlow() {
    composable<SignInRoute> {
        val viewModel = koinViewModel<SignInViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        SignInView(state = state, eventHandler = viewModel::handleEvent)
    }
}

@Serializable
object SignInRoute: FerbyRoute