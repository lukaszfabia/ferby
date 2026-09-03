package com.lukaszfabia.ferby.feature.shrinesecrets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lukaszfabia.ferby.common.navigation.NavigationDelegate
import com.lukaszfabia.ferby.core.cache.MemoryStore
import com.lukaszfabia.ferby.domain.deadbydaylight.model.Perk
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.usecase.GetCurrentShrineSecretsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** ViewModel for the Shrine Secrets feature. */
class ShrineSecretsViewModel(
    private val getCurrentShrineSecretsUseCase: GetCurrentShrineSecretsUseCase,
    private val memoryStore: MemoryStore<Perk>,
    private val navigation: NavigationDelegate,
) : ViewModel() {
    private val _state = MutableStateFlow<ShrineSecretsState>(ShrineSecretsState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = getCurrentShrineSecretsUseCase()
                .fold(
                    onSuccess = { ShrineSecretsState.Success(it) },
                    onFailure = { ShrineSecretsState.Failure(it) })
        }
    }

    fun handleEvent(event: ShrineSecretsEvent) {
        when (event) {
            is ShrineSecretsEvent.OnPerkClick -> onPerkClick(event.perk)
        }
    }

    private fun onPerkClick(perk: Perk) {
        viewModelScope.launch {
            memoryStore.set(perk)
            navigation.navigate(ShrineSecretsDetailRoute)
        }
    }
}