package com.lukaszfabia.ferby.main.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.lukaszfabia.ferby.R
import com.lukaszfabia.ferby.common.navigation.TabRoute
import com.lukaszfabia.ferby.feature.home.HomeRoute
import com.lukaszfabia.ferby.feature.profile.ProfileRoute
import com.lukaszfabia.ferby.feature.search.SearchRoute

sealed interface TabUi {
    val title: Int
    val route: TabRoute
    val icon: ImageVector

    data object Home : TabUi {
        override val title = R.string.home
        override val route = HomeRoute
        override val icon = Icons.Default.Home
    }

    data object Profile : TabUi {
        override val title = R.string.profile
        override val route = ProfileRoute
        override val icon = Icons.Default.Person
    }

    data object Search : TabUi {
        override val title = R.string.search
        override val route = SearchRoute
        override val icon = Icons.Default.Search
    }
}