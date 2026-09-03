package com.lukaszfabia.ferby.core.networking

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual fun getEngine(): HttpClientEngine = Darwin.create()
