package com.lukaszfabia.ferby.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lukaszfabia.ferby.common.navigation.NavigationDelegate
import com.lukaszfabia.ferby.common.navigation.TabRoute
import com.lukaszfabia.ferby.feature.home.HomeRoute
import com.lukaszfabia.ferby.feature.profile.ProfileRoute
import com.lukaszfabia.ferby.feature.search.SearchRoute
import com.lukaszfabia.ferby.main.model.toUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val navigationDelegate: NavigationDelegate
) : ViewModel() {

    // Change to provider/usecase
    private val tabs = listOf(
        HomeRoute,
        ProfileRoute,
        SearchRoute
    )
    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        _state.update { it.copy(tabsUi = tabs.map { tab -> tab.toUi() }) }
    }

    fun handleEvent(event: MainEvent) {
        when(event) {
            is MainEvent.OnTabItemClick -> onTabItemClick(event.route)
        }
    }

    private fun onTabItemClick(route: TabRoute) {
        _state.update { it.copy(selectedTab = route) }

        viewModelScope.launch {
            navigationDelegate.navigate(route)
        }
    }
}