package com.lukaszfabia.ferby.main

import com.lukaszfabia.ferby.common.navigation.TabRoute
import com.lukaszfabia.ferby.feature.home.HomeRoute
import com.lukaszfabia.ferby.main.model.TabUi

data class MainState(
    val tabsUi: List<TabUi> = emptyList(),
    val selectedTab: TabRoute = HomeRoute
)