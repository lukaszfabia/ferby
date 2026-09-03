//
//  HomeProvider.swift
//  iosApp
//
//  Created by Lukasz Fabia on 02/09/2026.
//

import SwiftUI

@MainActor
struct HomeProvider {
    private let router: Router<HomeDestination>
    private let viewModel: HomeViewModel = .init()

    init(router: Router<HomeDestination>) {
        self.router = router
    }

    var content: some View {
        HomeView(viewModel: viewModel)
            .environment(router)
    }
}
