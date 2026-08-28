//
//  ShrineSecretsDetailProvider.swift
//  iosApp
//
//  Created by Lukasz Fabia on 28/08/2026.
//

import SharedLogic
import SwiftUI

struct ShrineSecretsDetailProvider {
    private let viewModel: ShrineSecretsDetailViewModel
    
    init(_ perk: Perk) {
        self.viewModel = ShrineSecretsDetailViewModel(perk: perk)
    }
    
    var content: some View {
        ShrineSecretsDetailView(viewModel: viewModel)
    }
}
