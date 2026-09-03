//
//  ShrineSecretsState.swift
//  iosApp
//
//  Created by Lukasz Fabia on 02/09/2026.
//

import SharedLogic

enum ShrineSecretsState {
    case loading
    case success(ShrineSecrets)
    case failure(FerbyError)
}
