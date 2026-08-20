package com.lukaszfabia.ferby.feature

import com.lukaszfabia.ferby.feature.shrinesecrets.shrineSecretsModule
import org.koin.dsl.module

/** Module for features registration. */
val featuresModule = module {

    includes(
        shrineSecretsModule,
    )
}