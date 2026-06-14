package com.lukaszfabia.ferby

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewModelScope
import com.lukaszfabia.ferby.data.networking.model.ApiResult
import com.lukaszfabia.ferby.data.networking.model.Error
import com.lukaszfabia.ferby.di.AppModule
import com.lukaszfabia.ferby.domain.model.Entity
import com.lukaszfabia.ferby.domain.model.Perk
import com.lukaszfabia.ferby.feature.shrinesecrets.di.ShrineSecretsModule
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.model.ShrineSecrets
import com.lukaszfabia.ferby.feature.shrinesecrets.domain.usecase.GetCurrentShrineSecretsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            Nav()
        }
    }
}

// TODO: move it into separate dir
@Composable
fun Nav() {
    val viewModel: TestViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TestViewModel(ShrineSecretsModule(AppModule).useCase) as T
            }
        }
    )
    val state by viewModel.state.collectAsStateWithLifecycle()
    UnderConstruction(state)
}

data class TestState(
    val isLoading: Boolean = true,
    val shrineSecrets: ShrineSecrets? = null,
    val error: Error? = null,
)

class TestViewModel(
    private val getCurrentShrineSecretsUseCase: GetCurrentShrineSecretsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(TestState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                when(val result = getCurrentShrineSecretsUseCase()) {
                    is ApiResult.Success -> it.copy(shrineSecrets = result.data)
                    is ApiResult.Failure -> it.copy(error = result.error)
                }
            }
            _state.update { it.copy(isLoading = false)}
        }
    }
}

@Composable
private fun UnderConstruction(state: TestState) {
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize().safeContentPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else if (state.shrineSecrets != null) {
                state.shrineSecrets.perks.forEach {
                    Text(text = it.name)
                }
            } else {
                Text(text = state.error.toString())
            }
        }
    }
}
