package com.lukaszfabia.ferby.feature.shrinesecrets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lukaszfabia.ferby.common.mapper.toMessageId
import com.lukaszfabia.ferby.data.networking.model.FerbyError
import com.lukaszfabia.ferby.domain.model.Perk
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.model.ShrineSecrets

@Composable
fun ShrineSecretsView(
    state: ShrineSecretsState,
    eventHandler: ShrineSecretsEventHandler,
) {
    Scaffold(
        topBar = {
            Text("shrine top")
        },
        bottomBar = {
            Text("shrine bottom")
        },
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when (state) {
                is ShrineSecretsState.Loading -> ShrineSecretsLoadingView()
                is ShrineSecretsState.Failure -> ShrineSecretsFailureView(state.error)
                is ShrineSecretsState.Success -> ShrineSecretsSuccessView(
                    shrineSecrets = state.shrineSecrets,
                    onPerkClick = { eventHandler(ShrineSecretsEvent.OnPerkClick(it)) }
                )
            }
        }
    }
}

@Composable
private fun ShrineSecretsLoadingView() {
    CircularProgressIndicator()
}

@Composable
private fun ShrineSecretsFailureView(error: FerbyError) {
    Text(text = stringResource(error.toMessageId()))
}

@Composable
private fun ShrineSecretsSuccessView(shrineSecrets: ShrineSecrets, onPerkClick: (Perk) -> Unit) {
    Column {
        shrineSecrets.perks.forEach {
            Button(onClick = { onPerkClick(it) }) {
                Text(it.name)
            }
        }
    }
}