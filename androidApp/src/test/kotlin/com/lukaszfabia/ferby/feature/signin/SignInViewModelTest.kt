package com.lukaszfabia.ferby.feature.signin

import android.app.Activity
import com.lukaszfabia.ferby.common.adapter.SignWithGoogleAdapter
import com.lukaszfabia.ferby.core.result.FerbyError
import com.lukaszfabia.ferby.core.result.FerbyResult
import com.lukaszfabia.ferby.core.result.GoogleSsoError
import com.lukaszfabia.ferby.domain.authentication.model.GoogleCredential
import com.lukaszfabia.ferby.domain.authentication.usecase.SignInWithGoogleUseCase
import com.lukaszfabia.ferby.domain.user.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.time.Clock
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class SignInViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private val signInWithGoogle = FakeSignInWithGoogleUseCase()
    private val signWithGoogleAdapter = FakeSignWithGoogleAdapter()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun handleEvent_onSignInWithGoogleClick_onSuccess_doesNotUpdatesLoadingState() = runTest {
        // Given
        val credential = GoogleCredential("token", null)
        val user = User(
            id = "id",
            email = "email",
            name = "name",
            providerId = "provider",
            photoUrl = "photo",
            username = "username",
            createdAt = Clock.System.now()
        )
        signWithGoogleAdapter.result = FerbyResult.Success(credential)
        signInWithGoogle.result = FerbyResult.Success(user)
        val viewModel = SignInViewModel(signInWithGoogle, signWithGoogleAdapter)

        // When
        viewModel.handleEvent(SignInEvent.OnSignInWithGoogleClick(DummyActivity()))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(true, viewModel.state.value.isLoading)
        assertEquals(null, viewModel.state.value.error)
    }

    @Test
    fun handleEvent_onSignInWithGoogleClick_onFailure_updatesErrorState() = runTest {
        // Given
        val error = GoogleSsoError.SignInFailed
        signWithGoogleAdapter.result = FerbyResult.Failure(error)
        val viewModel = SignInViewModel(signInWithGoogle, signWithGoogleAdapter)

        // When
        viewModel.handleEvent(SignInEvent.OnSignInWithGoogleClick(DummyActivity()))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(false, viewModel.state.value.isLoading)
        assertEquals(error, viewModel.state.value.error)
    }
}

private class FakeSignInWithGoogleUseCase : SignInWithGoogleUseCase {
    var result: FerbyResult<User> = FerbyResult.Failure(FerbyError.Unknown)
    override suspend fun invoke(credential: GoogleCredential): FerbyResult<User> = result
}

private class FakeSignWithGoogleAdapter : SignWithGoogleAdapter {
    var result: FerbyResult<GoogleCredential> = FerbyResult.Failure(GoogleSsoError.SignInFailed)
    override suspend fun signIn(activity: Activity): FerbyResult<GoogleCredential> = result
}

private class DummyActivity : Activity()
