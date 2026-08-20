package com.lukaszfabia.ferby.feature.shrinesecrets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lukaszfabia.ferby.common.navigation.NavigationDelegate
import com.lukaszfabia.ferby.data.networking.model.FerbyResult
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.usecase.GetCurrentShrineSecretsUseCase
import com.lukaszfabia.ferby.feature.startup.StartUpRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** ViewModel for the Shrine Secrets feature. */
class ShrineSecretsViewModel(
    private val getCurrentShrineSecretsUseCase: GetCurrentShrineSecretsUseCase,
    private val navigation: NavigationDelegate,
) : ViewModel() {
    private val _state = MutableStateFlow<ShrineSecretsState>(ShrineSecretsState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = when (val result = getCurrentShrineSecretsUseCase()) {
                is FerbyResult.Success -> ShrineSecretsState.Success(shrineSecrets = result.data)
                is FerbyResult.Failure -> ShrineSecretsState.Failure(error = result.error)
            }
        }
    }

    fun handleEvent(event: ShrineSecretsEvent) {
        when (event) {
            ShrineSecretsEvent.OnButtonClick -> onButtonClick()
        }
    }

    private fun onButtonClick() {
        viewModelScope.launch {
            navigation.navigate(StartUpRoute)
        }
    }
}