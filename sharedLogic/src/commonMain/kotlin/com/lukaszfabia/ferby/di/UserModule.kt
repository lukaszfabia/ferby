package com.lukaszfabia.ferby.di

import com.lukaszfabia.ferby.data.user.api.UserApi
import com.lukaszfabia.ferby.data.user.api.UserApiImpl
import com.lukaszfabia.ferby.data.user.repository.UserRepositoryImpl
import com.lukaszfabia.ferby.domain.authentication.usecase.ObserveSessionUseCase
import com.lukaszfabia.ferby.domain.user.repository.UserRepository
import com.lukaszfabia.ferby.domain.user.usecase.CreateUserUseCase
import com.lukaszfabia.ferby.domain.user.usecase.CreateUserUseCaseImpl
import com.lukaszfabia.ferby.domain.user.usecase.GetUserUseCase
import com.lukaszfabia.ferby.domain.user.usecase.GetUserUseCaseImpl
import com.lukaszfabia.ferby.domain.user.usecase.ObserveCurrentUserUseCase
import com.lukaszfabia.ferby.domain.user.usecase.ObserveCurrentUserUseCaseImpl
import com.lukaszfabia.ferby.domain.user.usecase.ObserveUserUseCase
import com.lukaszfabia.ferby.domain.user.usecase.ObserveUserUseCaseImpl
import com.lukaszfabia.ferby.domain.user.usecase.SaveUserUseCase
import com.lukaszfabia.ferby.domain.user.usecase.SaveUserUseCaseImpl
import org.koin.dsl.module

val userModule =
    module {

        single<UserApi> {
            UserApiImpl(get(), get())
        }

        single<UserRepository> {
            UserRepositoryImpl(get())
        }

        single<CreateUserUseCase> {
            CreateUserUseCaseImpl(get())
        }

        single<GetUserUseCase> {
            GetUserUseCaseImpl(get())
        }

        single<ObserveUserUseCase> {
            ObserveUserUseCaseImpl(get())
        }

        single<SaveUserUseCase> {
            SaveUserUseCaseImpl(get())
        }

        single<ObserveCurrentUserUseCase> {
            ObserveCurrentUserUseCaseImpl(
                get<ObserveSessionUseCase>(),
                get<ObserveUserUseCase>(),
            )
        }
    }
