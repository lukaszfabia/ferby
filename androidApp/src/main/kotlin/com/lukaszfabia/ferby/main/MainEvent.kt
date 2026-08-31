package com.lukaszfabia.ferby.main

import com.lukaszfabia.ferby.common.navigation.TabRoute

typealias MainEventHandler = (MainEvent) -> Unit

sealed interface MainEvent {
    data class OnTabItemClick(val route: TabRoute): MainEvent
}