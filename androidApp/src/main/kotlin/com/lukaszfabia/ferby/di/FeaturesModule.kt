package com.lukaszfabia.ferby.di

import com.lukaszfabia.ferby.feature.shrinesecrets.ShrineSecretsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/** Module for features registration. */
val featuresModule = module {

    viewModel {
        ShrineSecretsViewModel(get())
    }
}