package com.lukaszfabia.ferby

import com.lukaszfabia.ferby.di.appModule
import org.koin.core.context.startKoin

object Koin {
    fun setupBackend() {
        startKoin {
            modules(appModule)
        }
    }
}