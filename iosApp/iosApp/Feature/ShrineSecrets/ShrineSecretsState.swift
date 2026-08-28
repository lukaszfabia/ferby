//
//  ShrineSecretsState.swift
//  iosApp
//
//  Created by Lukasz Fabia on 22/08/2026.
//

import Foundation
import SharedLogic

enum ShrineSecretsState {
    case loading
    case success(ShrineSecrets)
    case failure(FerbyError)
}
