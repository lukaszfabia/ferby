//
//  ShrineSecretsDetailViewModel.swift
//  iosApp
//
//  Created by Lukasz Fabia on 28/08/2026.
//

import SharedLogic

final class ShrineSecretsDetailViewModel: ViewModelProtocol {
    let state: Perk
    
    init(perk: Perk) {
        self.state = perk
    }
    
    func send(action: ShrineSecretsDetailAction) async {
        
    }
}
