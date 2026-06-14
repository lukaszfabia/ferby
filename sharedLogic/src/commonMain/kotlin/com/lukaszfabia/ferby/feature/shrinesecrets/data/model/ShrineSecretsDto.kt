package com.lukaszfabia.ferby.feature.shrinesecrets.data.model

import kotlinx.serialization.Serializable

/**
 * Data Transfer Object representing the response for the Shrine of Secrets.
 *
 * @property status The status of the API response (e.g., "success").
 * @property error An optional error message if the request failed.
 * @property data The contained [ShrineSecretsData] including active dates and available perks.
 */
@Serializable
data class ShrineSecretsDto(
    val status: String = "",
    val error: String? = null,
    val data: ShrineSecretsData,
)

/**
 * Represents the specific details of a Shrine of Secrets rotation.
 *
 * @property start The start date and time of the current rotation.
 * @property end The end date and time of the current rotation.
 * @property week The rotation week identifier.
 * @property perks The list of [ShrineSecretsPerk] available in this rotation.
 */
@Serializable
data class ShrineSecretsData(
    val start: String,
    val end: String,
    val week: Int,
    val perks: List<ShrineSecretsPerk>,
)

/**
 * Represents a specific perk available in the Shrine of Secrets.
 *
 * @property id The unique identifier for the perk.
 * @property bloodpoints The amount of Bloodpoints awarded if the perk is already owned.
 * @property shards The cost of the perk in Iridescent Shards.
 * @property name The display name of the perk.
 * @property image The URL or file path for the perk's icon.
 * @property character The name of the character associated with this perk.
 * @property usageTier The meta-ranking or usage frequency tier of the perk.
 */
@Serializable
data class ShrineSecretsPerk(
    val id: Int,
    val bloodpoints: Int,
    val shards: Int,
    val name: String,
    val image: String,
    val character: String,
    val usageTier: String,
)
