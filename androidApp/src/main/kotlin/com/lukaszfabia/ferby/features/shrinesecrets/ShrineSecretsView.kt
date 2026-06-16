package com.lukaszfabia.ferby.features.shrinesecrets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.lukaszfabia.ferby.common.extension.toMessageId
import com.lukaszfabia.ferby.data.networking.model.Error
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.model.ShrineSecrets

@Composable
fun ShrineSecretsView(
    state: ShrineSecretsState
) {
    Column {
        when(state) {
            is ShrineSecretsState.Loading -> ShrineSecretsLoadingView()
            is ShrineSecretsState.Failure -> ShrineSecretsFailureView(state.error)
            is ShrineSecretsState.Success -> ShrineSecretsSuccessView(state.shrineSecrets)
        }
    }
}

@Composable
private fun ShrineSecretsLoadingView() {
    CircularProgressIndicator()
}

@Composable
private fun ShrineSecretsFailureView(error: Error) {
    Text(text = stringResource(error.toMessageId()))
}

@Composable
private fun ShrineSecretsSuccessView(shrineSecrets: ShrineSecrets) {
    Row {
        shrineSecrets.perks.forEach {
            Column {
                Text(text = it.name)
                Text(text = it.owner.name)
            }
        }
    }
}