package com.lukaszfabia.ferby.common.navigation

import org.koin.dsl.module

val navigationModule = module {
    single<NavigationDelegateImpl> {
        NavigationDelegateImpl()
    }

    single<NavigationDelegate> {
        get<NavigationDelegateImpl>()
    }
}