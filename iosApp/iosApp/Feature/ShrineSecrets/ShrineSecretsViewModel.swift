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

    var state: ShrineSecretsState = .loading
    
    func send(action: ShrineSecretsAction) async {
        switch action {
        case .load:
            await loadCurrentShrineSecrets()
        }
    }
}

extension ShrineSecretsViewModel {
    private func loadCurrentShrineSecrets() async {
        let result = mapToFerbyResultState(
            try? await getCurrentShrineSecrets.invoke()
        )

        self.state = result.fold(onSuccess: { .success($0) }, onFailure: { .failure($0) })
    }
}

