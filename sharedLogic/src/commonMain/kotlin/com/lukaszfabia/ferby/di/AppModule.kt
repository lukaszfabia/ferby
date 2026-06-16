package com.lukaszfabia.ferby.di

import com.lukaszfabia.ferby.core.di.coreModule
import com.lukaszfabia.ferby.feature.shrinesecrets.di.shrineSecretsModule
import org.koin.dsl.module

val appModule =
    module {
        includes(coreModule, shrineSecretsModule)
    }
