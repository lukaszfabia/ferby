package com.lukaszfabia.ferby.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.lukaszfabia.ferby.common.navigation.NavigationDelegate
import com.lukaszfabia.ferby.common.navigation.NavigationEvent
import com.lukaszfabia.ferby.feature.home.HomeRoute
import org.koin.compose.koinInject

@Composable
fun MainHost() {
    val navController = rememberNavController()
    val navigationDelegate = koinInject<NavigationDelegate>()

    LaunchedEffect(Unit) {
        navigationDelegate.events.collect { event ->
            when (event) {
                is NavigationEvent.Navigate ->
                    navController.navigate(event.route)
                is NavigationEvent.PopStackBack ->
                    navController.popBackStack(event.route, event.inclusive)
                NavigationEvent.NavigateBack ->
                    navController.popBackStack()
                NavigationEvent.NavigateHome -> {
                    navController.navigate(HomeRoute)
                }
            }
        }
    }

    MainView(navController)
}