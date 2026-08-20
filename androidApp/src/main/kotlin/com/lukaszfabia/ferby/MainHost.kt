package com.lukaszfabia.ferby

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.lukaszfabia.ferby.common.navigation.NavigationDelegateImpl
import com.lukaszfabia.ferby.common.navigation.NavigationEvent
import com.lukaszfabia.ferby.feature.startup.startupFlow
import com.lukaszfabia.ferby.feature.shrinesecrets.ShrineSecretsRoute
import com.lukaszfabia.ferby.feature.shrinesecrets.shrineSecretsFlow
import org.koin.compose.koinInject

@Composable
fun MainHost() {
    val navController = rememberNavController()
    val navigationDelegate = koinInject<NavigationDelegateImpl>()

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
                }
            }
        }
    }

    MaterialTheme {
        NavHost(
            navController = navController,
            startDestination = ShrineSecretsRoute
        ) {
            startupFlow()
            shrineSecretsFlow()
        }
    }
}
