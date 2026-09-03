package com.lukaszfabia.ferby.bridge.feature

import com.lukaszfabia.ferby.feature.shrinesecrets.domain.usecase.GetCurrentShrineSecretsUseCase
import org.koin.mp.KoinPlatform

object ShrineSecretsModule {
    fun getCurrentShrineSecrets(): GetCurrentShrineSecretsUseCase = KoinPlatform.getKoin().get()
}
