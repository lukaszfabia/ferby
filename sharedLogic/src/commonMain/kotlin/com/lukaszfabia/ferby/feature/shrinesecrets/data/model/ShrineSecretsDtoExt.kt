package com.lukaszfabia.ferby.feature.shrinesecrets.data.model

import com.lukaszfabia.ferby.domain.deadbydaylight.model.Entity
import com.lukaszfabia.ferby.domain.deadbydaylight.model.Perk
import com.lukaszfabia.ferby.domain.deadbydaylight.type.Role
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.model.ShrineSecrets
import kotlinx.datetime.LocalDateTime

/** Maps [ShrineSecretsDto] to [ShrineSecrets].*/
fun ShrineSecretsDto.toDomain(): ShrineSecrets =
    ShrineSecrets(
        perks =
            this.data.perks
                .map { it.toDomain() }
                .toSet(),
        // TODO: add perks mapping, problem with joining owner
        start = LocalDateTime.parse(this.data.start),
        end = LocalDateTime.parse(this.data.end),
        week = this.data.week,
    )

/** Maps [ShrineSecretsPerk] to [Perk].*/
fun ShrineSecretsPerk.toDomain(): Perk =
    Perk(
        id = this.id.toString(),
        name = this.name,
        owner =
            Entity(
                id = "",
                name = this.character,
                role = Role.SURVIVOR, // TODO: add role mapping
                description = "", // TODO: add description mapping
                image = "", // TODO: add image mapping
                perks = emptySet(),
            ),
        description = "", // TODO: add perks description mapping
        image = this.image,
    )
