package com.lukaszfabia.ferby.di

import com.lukaszfabia.ferby.features.shrinesecrets.ShrineSecretsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featuresModule = module {

    viewModel {
        ShrineSecretsViewModel(get())
    }
}