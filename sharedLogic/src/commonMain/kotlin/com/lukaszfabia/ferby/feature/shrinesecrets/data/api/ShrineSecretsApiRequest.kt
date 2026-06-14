package com.lukaszfabia.ferby.feature.shrinesecrets.data.api

import com.lukaszfabia.ferby.data.networking.model.ApiRequest

/**
 * Provider for API request configurations related to the Shrine Secrets feature.
 *
 * This object centralizes the definitions for network requests used to fetch data
 * from the Nightlight API, specifically focusing on the current Shrine of Secrets
 * rotation and promotions.
 */
object ShrineSecretsApiRequestProvider {
    // TODO: move literal to config file
    private const val NIGHTLIGHT_URL = "https://api.nightlight.gg/v1"
    val currentPromotion = ApiRequest(
        apiUrl = NIGHTLIGHT_URL,
        path = "/shrine"
    )
}