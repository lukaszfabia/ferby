package com.lukaszfabia.ferby.core.di

import com.lukaszfabia.ferby.core.networking.di.networkingModule
import org.koin.dsl.module

val coreModule = module {
    includes(networkingModule)
}