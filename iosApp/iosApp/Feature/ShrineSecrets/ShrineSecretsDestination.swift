//
//  ShrineSecretsDestination.swift
//  iosApp
//
//  Created by Lukasz Fabia on 28/08/2026.
//

import SharedLogic

enum ShrineSecretsDestination: RouterDestinationProtocol {
    case root, detail(perk: Perk)
}
