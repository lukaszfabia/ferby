//
//  ShrineSecretsProvider.swift
//  iosApp
//
//  Created by Lukasz Fabia on 22/08/2026.
//

import SwiftUI

struct ShrineSecretsProvider {
    private let router: Router<ShrineSecretsDestination>
    private let viewModel: ShrineSecretsViewModel
    init(
        router: Router<ShrineSecretsDestination>,
        viewModel: ShrineSecretsViewModel
    ) {
        self.router = router
        self.viewModel = viewModel
    }
    var content: some View {
        ShrineSecretsView(viewModel: viewModel)
            .environment(router)
    }
}
