package com.lukaszfabia.ferby.feature

import com.lukaszfabia.ferby.feature.home.homeModule
import com.lukaszfabia.ferby.feature.shrinesecrets.shrineSecretsModule
import com.lukaszfabia.ferby.feature.signin.signInModule
import org.koin.dsl.module

/** Module for features registration. */
val featuresModule = module {

    includes(
        shrineSecretsModule,
        signInModule,
        homeModule,
    )
}