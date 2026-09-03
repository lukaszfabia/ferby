package com.lukaszfabia.ferby.feature.signin

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val signInModule = module {
    viewModel {
        SignInViewModel(get(), get())
    }
}