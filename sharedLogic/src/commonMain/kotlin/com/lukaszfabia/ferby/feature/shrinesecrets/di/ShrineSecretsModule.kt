package com.lukaszfabia.ferby.feature.shrinesecrets.di

import com.lukaszfabia.ferby.di.AppModule
import com.lukaszfabia.ferby.feature.shrinesecrets.data.api.ShrineSecretsApiImpl
import com.lukaszfabia.ferby.feature.shrinesecrets.data.repository.ShrineSecretsRepositoryImpl
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.usecase.GetCurrentShrineSecretsUseCase
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.usecase.GetCurrentShrineSecretsUseCaseImpl

class ShrineSecretsModule(
    appModule: AppModule,
) {
    private val shrineSecretsApiImpl = ShrineSecretsApiImpl(appModule.httpClient)
    private val repositoryImpl = ShrineSecretsRepositoryImpl(shrineSecretsApiImpl)
    val useCase: GetCurrentShrineSecretsUseCase = GetCurrentShrineSecretsUseCaseImpl(repositoryImpl)
}
