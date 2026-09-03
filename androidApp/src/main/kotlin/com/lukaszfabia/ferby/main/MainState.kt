package com.lukaszfabia.ferby.main

import com.lukaszfabia.ferby.common.navigation.TabRoute
import com.lukaszfabia.ferby.domain.user.model.User
import com.lukaszfabia.ferby.feature.home.HomeRoute
import com.lukaszfabia.ferby.main.model.TabUi

sealed interface MainState {

    data object Loading: MainState

    data object NotAuthenticated: MainState

    data class Authenticated(
        val tabsUi: List<TabUi> = emptyList(),
        val selectedTab: TabRoute = HomeRoute,
    ): MainState
}