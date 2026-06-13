package com.lukaszfabia.ferby.domain.model

/** Data class representing a perk.
 *  @property [name] Name of the perk.
 *  @property [owner] Entity that owns the perk.
 *  @property [description] Describes how perk actually works.
 * */
data class Perk(
    val name: String,
    val owner: Entity,
    val description: String,
)
