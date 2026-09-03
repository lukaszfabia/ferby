package com.lukaszfabia.ferby.common.adapter

import org.koin.dsl.module

val adapterModule = module {
    single<SignWithGoogleAdapter> {
        SignWithGoogleAdapterImpl()
    }
}