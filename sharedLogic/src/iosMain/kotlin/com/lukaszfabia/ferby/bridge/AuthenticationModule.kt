package com.lukaszfabia.ferby.bridge

import com.lukaszfabia.ferby.domain.authentication.usecase.ObserveSessionUseCase
import com.lukaszfabia.ferby.domain.authentication.usecase.SignInWithGoogleUseCase
import com.lukaszfabia.ferby.domain.authentication.usecase.SignOutUseCase
import org.koin.mp.KoinPlatform.getKoin

object AuthenticationModule {
    fun signWithGoogleUseCase(): SignInWithGoogleUseCase = getKoin().get()

    fun signOutUseCase(): SignOutUseCase = getKoin().get()

    fun observeSessionUseCase(): ObserveSessionUseCase = getKoin().get()
}
