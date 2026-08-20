package com.lukaszfabia.ferby.feature.startup

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lukaszfabia.ferby.common.navigation.FerbyRoute
import kotlinx.serialization.Serializable

fun NavGraphBuilder.startupFlow() {
    composable<StartUpRoute> {
        StartUpView()
    }
}

@Serializable
object StartUpRoute: FerbyRoute