package com.lukaszfabia.ferby.domain.deadbydaylight.model

/** Data class representing a perk.
 *  @property [id] Identifier for the perk.
 *  @property [name] Name of the perk.
 *  @property [owner] Entity that owns the perk.
 *  @property [description] Describes how perk actually works.
 *  @property [image] Link to the perk's image.
 * */
data class Perk(
    val id: String,
    val name: String,
    val owner: Entity,
    val description: String,
    val image: String,
)
