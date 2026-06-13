package com.lukaszfabia.ferby.domain.model

import com.lukaszfabia.ferby.domain.type.Role

/** Data class representing an entity in the game.
 *  @property [role] Role of the entity it might be [Role.SURVIVOR] either [Role.KILLER].
 *  @property [name] Name of the entity.
 *  @property [description] Describes the entity.
 *  @property [imageUri] URI of the image of the entity.
 *  @property [perks] Set of [Perk]s that the entity has, each perk is unique.
 * */
data class Entity(
    val role: Role,
    val name: String,
    val description: String,
    val imageUri: String,
    val perks: Set<Perk>,
)