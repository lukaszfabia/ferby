package com.lukaszfabia.ferby.feature.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lukaszfabia.ferby.common.navigation.TabRoute
import kotlinx.serialization.Serializable

fun NavGraphBuilder.profileFlow() {
    composable<ProfileRoute> {
        ProfileView()
    }
}

@Serializable
object ProfileRoute: TabRoute