package com.lukaszfabia.ferby.feature.shrinesecrets.detail

import com.lukaszfabia.ferby.core.cache.MemoryStoreImpl
import com.lukaszfabia.ferby.domain.deadbydaylight.model.Entity
import com.lukaszfabia.ferby.domain.deadbydaylight.model.Perk
import com.lukaszfabia.ferby.domain.deadbydaylight.type.Role
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ShrineSecretsDetailViewModelTest {

    @Test
    fun init_withPerkInMemoryStore_setsStateWithPerk() {
        // Given
        val perk = Perk(
            id = "1",
            name = "Perk 1",
            owner = Entity("1", Role.SURVIVOR, "Owner", "Desc", "img", emptySet()),
            description = "Description",
            image = "image"
        )
        val memoryStore = MemoryStoreImpl<Perk>()
        memoryStore.set(perk)

        // When
        val viewModel = ShrineSecretsDetailViewModel(memoryStore)

        // Then
        assertEquals(ShrineSecretsDetailState(perk), viewModel.state.value)
    }

    @Test
    fun init_withoutPerkInMemoryStore_throwsException() {
        // Given
        val memoryStore = MemoryStoreImpl<Perk>()

        // When & Then
        assertFailsWith<IllegalArgumentException> {
            ShrineSecretsDetailViewModel(memoryStore)
        }
    }
}
