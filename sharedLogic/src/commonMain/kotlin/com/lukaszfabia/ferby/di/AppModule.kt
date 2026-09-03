package com.lukaszfabia.ferby.di

import com.lukaszfabia.ferby.core.di.coreModule
import com.lukaszfabia.ferby.feature.featureModule
import org.koin.dsl.module

val appModule =
    module {
        includes(coreModule, featureModule, userModule, authenticationModule)
    }
