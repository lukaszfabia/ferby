package com.lukaszfabia.ferby.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.lukaszfabia.ferby.common.ui.navigation.TabView
import com.lukaszfabia.ferby.feature.home.homeFlow
import com.lukaszfabia.ferby.feature.profile.profileFlow
import com.lukaszfabia.ferby.feature.search.searchFlow
import com.lukaszfabia.ferby.feature.shrinesecrets.shrineSecretsFlow
import com.lukaszfabia.ferby.feature.startup.startupFlow
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainView(navHostController: NavHostController) {
    val viewModel = koinViewModel<MainViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    MainView(navHostController = navHostController, state = state, eventHandler = viewModel::handleEvent)
}

@Composable
private fun MainView(navHostController: NavHostController, state: MainState, eventHandler: MainEventHandler) {
    MaterialTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                TabView(
                    selectedTab = state.selectedTab,
                    tabs = state.tabsUi,
                    onTabItemClick = { eventHandler(MainEvent.OnTabItemClick(it)) }
                )
            }
        ) { scaffoldPadding ->
            NavHost(
                navController = navHostController,
                startDestination = state.selectedTab,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding)
            ) {
                homeFlow()
                profileFlow()
                searchFlow()
                startupFlow()
                shrineSecretsFlow()
            }
        }
    }
}