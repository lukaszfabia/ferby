package com.lukaszfabia.ferby.common.di

import com.lukaszfabia.ferby.common.navigation.navigationModule
import org.koin.dsl.module

val commonModule = module {
    includes(navigationModule)
}