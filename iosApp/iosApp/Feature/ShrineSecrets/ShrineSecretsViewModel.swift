//
//  ShrineSecretsViewModel.swift
//  iosApp
//
//  Created by Lukasz Fabia on 22/08/2026.
//

import Foundation
import SharedLogic

@Observable
@MainActor
final class ShrineSecretsViewModel: ViewModelProtocol {
    private let getCurrentShrineSecrets =
        ShrineSecretsModule.shared.getCurrentShrineSecrets()

    var state: ViewState<ShrineSecrets> = .loading
    
    func send(action: ShrineSecretsAction) async {
        switch action {
        case .load:
            await loadCurrentShrineSecrets()
        }
    }
}

extension ShrineSecretsViewModel {
    func loadCurrentShrineSecrets() async {
        self.state = await execute {
            try await getCurrentShrineSecrets.invoke()
        }
    }
}
