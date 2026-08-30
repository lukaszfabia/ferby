package com.lukaszfabia.ferby.feature.shrinesecrets.detail

import androidx.lifecycle.ViewModel
import com.lukaszfabia.ferby.core.cache.MemoryStore
import com.lukaszfabia.ferby.domain.model.Perk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShrineSecretsDetailViewModel(
    memoryStore: MemoryStore<Perk>
) : ViewModel() {
    private val _state = MutableStateFlow(ShrineSecretsDetailState(perk = requireNotNull(memoryStore.get())))
    val state = _state.asStateFlow()
}