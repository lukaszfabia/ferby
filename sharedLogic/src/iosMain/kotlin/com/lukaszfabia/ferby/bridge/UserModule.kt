package com.lukaszfabia.ferby.bridge

import com.lukaszfabia.ferby.domain.user.usecase.ObserveCurrentUserUseCase
import com.lukaszfabia.ferby.domain.user.usecase.ObserveUserUseCase
import com.lukaszfabia.ferby.domain.user.usecase.SaveUserUseCase
import org.koin.mp.KoinPlatform.getKoin

object UserModule {
    fun observeCurrentUserUseCase(): ObserveCurrentUserUseCase = getKoin().get()

    fun observeUserUseCase(): ObserveUserUseCase = getKoin().get()

    fun saveUserUseCase(): SaveUserUseCase = getKoin().get()
}
