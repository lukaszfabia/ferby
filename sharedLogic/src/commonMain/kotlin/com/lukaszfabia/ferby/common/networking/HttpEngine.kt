package com.lukaszfabia.ferby.common.networking

import io.ktor.client.engine.HttpClientEngine

expect fun getEngine(): HttpClientEngine
