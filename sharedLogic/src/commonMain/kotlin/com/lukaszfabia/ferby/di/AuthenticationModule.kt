package com.lukaszfabia.ferby.di

import com.lukaszfabia.ferby.data.authentication.api.AuthenticationApi
import com.lukaszfabia.ferby.data.authentication.api.AuthenticationApiImpl
import com.lukaszfabia.ferby.data.authentication.repository.AuthenticationRepositoryImpl
import com.lukaszfabia.ferby.domain.authentication.repository.AuthenticationRepository
import com.lukaszfabia.ferby.domain.authentication.usecase.AuthenticateWithGoogleUseCase
import com.lukaszfabia.ferby.domain.authentication.usecase.AuthenticateWithGoogleUseCaseImpl
import com.lukaszfabia.ferby.domain.authentication.usecase.ObserveSessionUseCase
import com.lukaszfabia.ferby.domain.authentication.usecase.ObserveSessionUseCaseImpl
import com.lukaszfabia.ferby.domain.authentication.usecase.SignInWithGoogleUseCase
import com.lukaszfabia.ferby.domain.authentication.usecase.SignInWithGoogleUseCaseImpl
import com.lukaszfabia.ferby.domain.authentication.usecase.SignOutUseCase
import com.lukaszfabia.ferby.domain.authentication.usecase.SignOutUseCaseImpl
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import org.koin.dsl.module

val authenticationModule =
    module {

        single {
            Firebase.auth
        }

        single<AuthenticationApi> {
            AuthenticationApiImpl(get())
        }

        single<AuthenticationRepository> {
            AuthenticationRepositoryImpl(get())
        }

        single<AuthenticateWithGoogleUseCase> {
            AuthenticateWithGoogleUseCaseImpl(get())
        }

        single<ObserveSessionUseCase> {
            ObserveSessionUseCaseImpl(get())
        }

        single<SignInWithGoogleUseCase> {
            SignInWithGoogleUseCaseImpl(get(), get(), get())
        }

        single<SignOutUseCase> {
            SignOutUseCaseImpl(get())
        }
    }
