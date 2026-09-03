//
//  SignInViewModel.swift
//  iosApp
//
//  Created by Lukasz Fabia on 01/09/2026.
//

import Observation
import SharedLogic

@Observable
@MainActor
final class SignInViewModel: ViewModelProtocol {
    private let signWithGoogle: SignInWithGoogleUseCase =
    AuthenticationModule.shared.signWithGoogleUseCase()
    
    let signWithGoogleAdapter: SignWithGoogleAdapter
    
    var state: SignInState = .init()
    
    init(signWithGoogleAdapter: SignWithGoogleAdapter) {
        self.signWithGoogleAdapter = signWithGoogleAdapter
    }
    
    func send(action: SignInAction) async {
        switch action {
        case .onSignInClick:
            await onSignInClick()
        case .reset:
            self.state = .init()
        }
    }
    
    private func onSignInClick() async {
        self.state.isLoading = true
        
        let result = await signWithGoogleAdapter.signIn()
        await result.fold { credential in
            await authenticate(with: credential)
        } onFailure: { error in
            guard let _ = error as? GoogleSsoErrorCancelled else {
                self.state.error = error
                self.state.isLoading = false
                return
            }
            self.state = .init()
        }
    }
    
    private func authenticate(with credential: GoogleCredential) async {
        let ferbyResult = try? await signWithGoogle.invoke(credential: credential)
        let result = mapToFerbyResultState(ferbyResult)
        
        result.fold(
            onSuccess: { _ in }, onFailure: {
                self.state.error = $0
                self.state.isLoading = false
            }
        )
    }
}
