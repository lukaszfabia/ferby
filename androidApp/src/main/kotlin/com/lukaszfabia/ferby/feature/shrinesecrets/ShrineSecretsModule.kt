package com.lukaszfabia.ferby.feature.shrinesecrets

import com.lukaszfabia.ferby.domain.deadbydaylight.model.Perk
import  com.lukaszfabia.ferby.core.cache.MemoryStore
import  com.lukaszfabia.ferby.core.cache.MemoryStoreImpl
import com.lukaszfabia.ferby.feature.shrinesecrets.detail.ShrineSecretsDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/** Module for features registration. */
val shrineSecretsModule = module {

    single<MemoryStore<Perk>> {
        MemoryStoreImpl()
    }

    viewModel {
        ShrineSecretsViewModel(get(), get(), get())
    }

    viewModel {
        ShrineSecretsDetailViewModel(get())
    }
}