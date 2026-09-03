//
//  SignInProvider.swift
//  iosApp
//
//  Created by Lukasz Fabia on 01/09/2026.
//

import SwiftUI

@MainActor
struct SignInProvider {
    private let router: Router<SignInDestination>
    private let viewModel: SignInViewModel = .init(signWithGoogleAdapter: SignWithGoogleAdapterImpl())

    init(router: Router<SignInDestination>) {
        self.router = router
    }

    var content: some View {
        SignInView(viewModel: viewModel)
            .environment(router)
    }
}
