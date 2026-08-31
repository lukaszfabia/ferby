package com.lukaszfabia.ferby.main.model

import com.lukaszfabia.ferby.common.navigation.TabRoute
import com.lukaszfabia.ferby.feature.home.HomeRoute
import com.lukaszfabia.ferby.feature.profile.ProfileRoute
import com.lukaszfabia.ferby.feature.search.SearchRoute

fun TabRoute.toUi(): TabUi =
    when(this) {
        HomeRoute -> TabUi.Home
        SearchRoute -> TabUi.Search
        ProfileRoute -> TabUi.Profile
        else -> TabUi.Home
    }