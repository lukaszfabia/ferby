package com.lukaszfabia.ferby.feature.search

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lukaszfabia.ferby.common.navigation.TabRoute
import kotlinx.serialization.Serializable

fun NavGraphBuilder.searchFlow() {
    composable<SearchRoute> {
        SearchView()
    }
}

@Serializable
object SearchRoute: TabRoute