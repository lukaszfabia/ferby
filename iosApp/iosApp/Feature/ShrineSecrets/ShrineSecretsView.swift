//
//  ShrineSecretsView.swift
//  iosApp
//
//  Created by Lukasz Fabia on 22/08/2026.
//

import SwiftUI

struct ShrineSecretsView: View {
    @State private var viewModel = ShrineSecretsViewModel()
    
    var body: some View {
        VStack {
            switch(viewModel.state) {
            case .loading:
                ProgressView()
            case .success(let data):
                List(data.perks.map { $0 }, id: \.self) { perk in
                    Text(perk.name)
                }
            case .failure:
                Text("erro")
            }
        }.task {
            await viewModel.loadCurrentShrineSecrets()
        }
    }
}

#Preview {
    ShrineSecretsView()
}
