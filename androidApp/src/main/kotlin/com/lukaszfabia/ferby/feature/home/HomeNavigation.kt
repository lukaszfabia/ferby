package com.lukaszfabia.ferby.feature.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lukaszfabia.ferby.common.navigation.TabRoute
import kotlinx.serialization.Serializable

fun NavGraphBuilder.homeFlow() {
    composable<HomeRoute> {
        HomeView()
    }
}

@Serializable
object HomeRoute: TabRoute