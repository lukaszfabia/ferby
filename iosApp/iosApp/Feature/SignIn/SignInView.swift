//
//  SignIn.swift
//  iosApp
//
//  Created by Lukasz Fabia on 31/08/2026.
//

import SwiftUI
import SharedLogic
import GoogleSignIn

struct SignInView: View {
    @Environment(Router<SignInDestination>.self) private var router
    @State var viewModel: SignInViewModel
    
    var body: some View {
        RoutingView(router: router) { destination in
            switch destination {
            case .root:
                RootView(viewModel: viewModel)
            }
        }
        .environment(router)
    }
}

private struct RootView: View {
    @Bindable var viewModel: SignInViewModel
    
    var body: some View {
        VStack {
            if viewModel.state.isLoading {
                ProgressView()
            }
            else {
                Button("Sign in with Google") {
                    Task {
                        await viewModel.send(action: .onSignInClick)
                    }
                }
                
                if let error = viewModel.state.error {
                    Text(error.localized())
                }
            }
        }
        .onAppear { viewModel.send(action: .reset) }
    }
}

