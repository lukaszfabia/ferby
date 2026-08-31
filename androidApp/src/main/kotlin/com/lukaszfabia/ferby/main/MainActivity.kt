package com.lukaszfabia.ferby.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.lukaszfabia.ferby.common.di.commonModule
import com.lukaszfabia.ferby.di.appModule
import com.lukaszfabia.ferby.feature.featuresModule
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        initKoin()
        super.onCreate(savedInstanceState)

        setContent {
            MainHost()
        }
    }
}

/** Initializes the Koin dependency injection framework. */
private fun initKoin(configuration: KoinAppDeclaration? = null) {
    startKoin {
        includes(configuration)
        modules(appModule, commonModule, featuresModule, mainModule)
    }
}
