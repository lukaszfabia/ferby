//
//  ShrineSecretsProvider.swift
//  iosApp
//
//  Created by Lukasz Fabia on 22/08/2026.
//

import SwiftUI

@MainActor
struct ShrineSecretsProvider {
    private let router: Router<ShrineSecretsDestination>
    private let viewModel: ShrineSecretsViewModel = .init()

    init(router: Router<ShrineSecretsDestination>) {
        self.router = router
    }
    
    var content: some View {
        ShrineSecretsView(viewModel: viewModel)
            .environment(router)
    }
}
