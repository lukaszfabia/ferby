package com.lukaszfabia.ferby.feature.shrinesecrets.domain.model

import com.lukaszfabia.ferby.domain.deadbydaylight.model.Perk
import kotlinx.datetime.LocalDateTime

/** Represents a shrine's secrets, weekly available perks to buy in the game.
 * @property [perks] Set of [Perk]s that can be bought.
 * @property [start] Full date and time of the start of the promotion.
 * @property [end] Full date and time of the end of the promotion.
 * @property [week] Information about the week series.
 * */
data class ShrineSecrets(
    val perks: Set<Perk>,
    val start: LocalDateTime,
    val end: LocalDateTime,
    val week: Int,
)
