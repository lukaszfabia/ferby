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
final class ShrineSecretsViewModel {
    private let getCurrentShrineSecrets =
        ShrineSecretsModule.shared.getCurrentShrineSecrets()

    var state: ShrineSecretsState = .loading

    func loadCurrentShrineSecrets() async {
        guard let result = try? await getCurrentShrineSecrets.invoke()
        else { return }

        result.fold { data in
            guard let data else {
                self.state = .failure(ApiErrorNoDataError.shared)
                return
            }

            self.state = .success(data)
            return
        } onFailure: { error in
            self.state = .failure(error)
        }
    }
}
