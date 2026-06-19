package com.lukaszfabia.ferby.features.shrinesecrets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lukaszfabia.ferby.data.networking.model.ApiResult
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.usecase.GetCurrentShrineSecretsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** ViewModel for the Shrine Secrets feature. */
class ShrineSecretsViewModel(
    private val getCurrentShrineSecretsUseCase: GetCurrentShrineSecretsUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<ShrineSecretsState>(ShrineSecretsState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = when (val result = getCurrentShrineSecretsUseCase()) {
                is ApiResult.Success -> ShrineSecretsState.Success(shrineSecrets = result.data)
                is ApiResult.Failure -> ShrineSecretsState.Failure(error = result.error)
            }
        }
    }
}