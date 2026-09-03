//
//  HomeView.swift
//  iosApp
//
//  Created by Lukasz Fabia on 02/09/2026.
//

import SwiftUI

struct HomeView: View {
    @Environment(Router<HomeDestination>.self) var router
    @State var viewModel: HomeViewModel
    
    var body: some View {
        RoutingView(router: router) { destination in
            switch destination {
            case .root:
                RootView(viewModel: viewModel)
            }
        }
    }
}

private struct RootView: View {
    @Bindable var viewModel: HomeViewModel
    
    var body: some View {
        VStack {
            Button("Sign out") {
                Task {
                    await viewModel.send(action: .onSignOutClick)
                }
            }
        }
    }
}
