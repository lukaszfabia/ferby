package com.lukaszfabia.ferby.feature.shrinesecrets.di

import com.lukaszfabia.ferby.feature.shrinesecrets.data.api.ShrineSecretsApi
import com.lukaszfabia.ferby.feature.shrinesecrets.data.api.ShrineSecretsApiImpl
import com.lukaszfabia.ferby.feature.shrinesecrets.data.repository.ShrineSecretsRepositoryImpl
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.repository.ShrineSecretsRepository
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.usecase.GetCurrentShrineSecretsUseCase
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.usecase.GetCurrentShrineSecretsUseCaseImpl
import org.koin.dsl.module

val shrineSecretsModule =
    module {

        single<ShrineSecretsApi> {
            ShrineSecretsApiImpl(get())
        }

        single<ShrineSecretsRepository> {
            ShrineSecretsRepositoryImpl(get())
        }

        single<GetCurrentShrineSecretsUseCase> {
            GetCurrentShrineSecretsUseCaseImpl(get())
        }
    }
