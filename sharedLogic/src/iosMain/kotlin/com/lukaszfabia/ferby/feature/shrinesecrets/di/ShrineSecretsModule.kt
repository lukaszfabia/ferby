package com.lukaszfabia.ferby.feature.shrinesecrets.di

import com.lukaszfabia.ferby.feature.shrinesecrets.domain.usecase.GetCurrentShrineSecretsUseCase
import org.koin.mp.KoinPlatform.getKoin

object ShrineSecretsModule {
    fun getCurrentShrineSecrets(): GetCurrentShrineSecretsUseCase = getKoin().get()
}