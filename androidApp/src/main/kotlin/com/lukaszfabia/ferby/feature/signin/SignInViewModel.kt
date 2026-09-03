package com.lukaszfabia.ferby.feature.signin

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lukaszfabia.ferby.common.adapter.SignWithGoogleAdapter
import com.lukaszfabia.ferby.core.result.flatMap
import com.lukaszfabia.ferby.domain.authentication.usecase.SignInWithGoogleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val signInWithGoogle: SignInWithGoogleUseCase,
    private val signWithGoogleAdapter: SignWithGoogleAdapter,
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun handleEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.OnSignInWithGoogleClick -> onSignInWithGoogleClick(event.activity)
        }
    }

    private fun onSignInWithGoogleClick(activity: Activity) {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            signWithGoogleAdapter
                .signIn(activity)
                .flatMap { credential -> signInWithGoogle(credential = credential) }
                .fold(
                    onSuccess = {},
                    onFailure = { error -> _state.update { it.copy(isLoading = false, error = error) } }
                )
        }
    }
}