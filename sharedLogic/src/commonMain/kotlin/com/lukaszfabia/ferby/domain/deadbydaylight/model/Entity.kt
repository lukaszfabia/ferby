package com.lukaszfabia.ferby.domain.deadbydaylight.model

import com.lukaszfabia.ferby.domain.deadbydaylight.type.Role

/** Data class representing an entity in the game.
 *  @property [id] Identifier for the character.
 *  @property [role] Role of the entity it might be [Role.SURVIVOR] either [Role.KILLER].
 *  @property [name] Name of the entity.
 *  @property [description] Describes the entity.
 *  @property [image] URI of the image of the entity.
 *  @property [perks] Set of [Perk]s that the entity has, each perk is unique.
 * */
data class Entity(
    val id: String,
    val role: Role,
    val name: String,
    val description: String,
    val image: String,
    val perks: Set<Perk>,
)
