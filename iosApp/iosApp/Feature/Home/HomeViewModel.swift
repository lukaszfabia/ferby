//
//  HomeViewModel.swift
//  iosApp
//
//  Created by Lukasz Fabia on 02/09/2026.
//

import Observation
import SharedLogic

@MainActor
@Observable
final class HomeViewModel: ViewModelProtocol {
    private let signOut: SignOutUseCase =
        AuthenticationModule.shared.signOutUseCase()
    
    var state: HomeState = .init()
    
    func send(action: HomeAction) async {
        switch action {
        case .onSignOutClick:
            await onSignOutClick()
        }
    }
    
    private func onSignOutClick() async {
        self.state.isLoading = true
        _ = try? await signOut.invoke()
    }
}
