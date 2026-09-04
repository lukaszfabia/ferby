//
//  MainViewModel.swift
//  iosApp
//
//  Created by Lukasz Fabia on 01/09/2026.
//

import Observation
import SharedLogic

@MainActor
@Observable
final class MainViewModel: ViewModelProtocol {
    private let observeSession: ObserveSessionUseCase = AuthenticationModule.shared.observeSessionUseCase()
    
    var state: MainState = .loading
    
    func send(action: MainAction) async {
        switch action {
        case .observeUser:
            await observeUser()
        }
    }

    private func observeUser() async {
        for await session in observeSession.invoke() {
            self.state = session != nil ? .authenticated : .notAuthenticated
        }
    }
}
