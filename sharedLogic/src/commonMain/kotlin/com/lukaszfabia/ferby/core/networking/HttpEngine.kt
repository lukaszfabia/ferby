package com.lukaszfabia.ferby.core.networking

import io.ktor.client.engine.HttpClientEngine

expect fun getEngine(): HttpClientEngine
