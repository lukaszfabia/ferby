package com.lukaszfabia.ferby.feature.shrinesecrets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lukaszfabia.ferby.common.mapper.toMessageId
import com.lukaszfabia.ferby.data.networking.model.FerbyError
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.model.ShrineSecrets

@Composable
fun ShrineSecretsView(
    state: ShrineSecretsState,
    eventHandler: ShrineSecretsEventHandler,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (state) {
            is ShrineSecretsState.Loading -> ShrineSecretsLoadingView()
            is ShrineSecretsState.Failure -> ShrineSecretsFailureView(state.error)
            is ShrineSecretsState.Success -> ShrineSecretsSuccessView(
                shrineSecrets = state.shrineSecrets,
                onButtonClick = { eventHandler(ShrineSecretsEvent.OnButtonClick) }
            )
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
private fun ShrineSecretsSuccessView(shrineSecrets: ShrineSecrets, onButtonClick: () -> Unit) {
    Row {
        shrineSecrets.perks.forEach {
            Column {
                Text(text = it.name)
                Text(text = it.owner.name)
            }
        }
    }

    Button(onClick = onButtonClick) {
        Text("Go to the other view")
    }
}