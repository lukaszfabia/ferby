package com.lukaszfabia.ferby.feature.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lukaszfabia.ferby.common.navigation.TabRoute
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.homeFlow() {
    composable<HomeRoute> {
        val viewModel = koinViewModel<HomeViewModel>()

        HomeView(eventHandler = viewModel::handleEvent)
    }
}

@Serializable
object HomeRoute: TabRoute