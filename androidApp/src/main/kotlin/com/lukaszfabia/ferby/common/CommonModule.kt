package com.lukaszfabia.ferby.common

import com.lukaszfabia.ferby.common.adapter.adapterModule
import com.lukaszfabia.ferby.common.navigation.navigationModule
import org.koin.dsl.module

val commonModule = module {
    includes(navigationModule, adapterModule)
}