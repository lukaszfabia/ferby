package com.lukaszfabia.ferby.common.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.dp

object Spacing {
    val XS = 4.dp
    val S = 8.dp
    val M = 16.dp
    val L = 24.dp
    val XL = 32.dp
    val XXL = 48.dp
}

val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = Spacing