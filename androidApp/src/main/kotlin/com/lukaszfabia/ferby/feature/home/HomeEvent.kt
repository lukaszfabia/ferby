package com.lukaszfabia.ferby.feature.home

typealias HomeEventHandler = (HomeEvent) -> Unit

sealed interface HomeEvent {
    data object OnSignOutClick: HomeEvent
}