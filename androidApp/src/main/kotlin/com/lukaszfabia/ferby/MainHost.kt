package com.lukaszfabia.ferby

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.lukaszfabia.ferby.features.shrinesecrets.ShrineSecretsRoute
import com.lukaszfabia.ferby.features.shrinesecrets.shrineSecretsFlow
import com.lukaszfabia.ferby.features.startup.startupFlow

@Composable
fun MainHost() {
    val navController = rememberNavController()

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
