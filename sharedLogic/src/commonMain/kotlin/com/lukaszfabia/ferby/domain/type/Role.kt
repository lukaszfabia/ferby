package com.lukaszfabia.ferby.domain.type

/** Represents roles in the game. */
enum class Role {
    SURVIVOR,
    KILLER;

    companion object {
        /** Creates [Role] from [name]. Might return null if [name] is not a valid [Role]. */
        fun fromName(name: String): Role? = runCatching {
            valueOf(name.trim())
        }.getOrNull()
    }
}