package com.lukaszfabia.ferby.feature

import com.lukaszfabia.ferby.feature.shrinesecrets.di.shrineSecretsModule
import org.koin.dsl.module

val featureModule =
    module {
        includes(shrineSecretsModule)
    }
