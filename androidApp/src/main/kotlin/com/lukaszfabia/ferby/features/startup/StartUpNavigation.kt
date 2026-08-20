package com.lukaszfabia.ferby.features.startup

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

fun NavGraphBuilder.startupFlow() {
    composable<StartUpRoute> {
        StartUpView()
    }
}

@Serializable
object StartUpRoute