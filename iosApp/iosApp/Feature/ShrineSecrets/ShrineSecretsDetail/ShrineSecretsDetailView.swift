//
//  ShrineSecretsDetailView.swift
//  iosApp
//
//  Created by Lukasz Fabia on 28/08/2026.
//

import SwiftUI

struct ShrineSecretsDetailView: View {
    @State var viewModel: ShrineSecretsDetailViewModel
    
    var body: some View {
        PerkCard(perk: viewModel.state)
    }
}
