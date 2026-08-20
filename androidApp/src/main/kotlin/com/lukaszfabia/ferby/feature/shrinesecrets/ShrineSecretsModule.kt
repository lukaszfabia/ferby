package com.lukaszfabia.ferby.feature.shrinesecrets

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/** Module for features registration. */
val shrineSecretsModule = module {

    viewModel {
        ShrineSecretsViewModel(get(), get())
    }
}