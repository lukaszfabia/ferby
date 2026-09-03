package com.lukaszfabia.ferby.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lukaszfabia.ferby.domain.authentication.usecase.SignOutUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val signOutUseCase: SignOutUseCase,
): ViewModel() {
    fun handleEvent(event: HomeEvent) {
        when(event) {
            HomeEvent.OnSignOutClick -> onSignOutClick()
        }
    }

    private fun onSignOutClick() {
        viewModelScope.launch {
            signOutUseCase()
        }
    }
}