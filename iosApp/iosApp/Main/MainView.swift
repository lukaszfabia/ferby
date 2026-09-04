//
//  MainView.swift
//  iosApp
//
//  Created by Lukasz Fabia on 30/08/2026.
//

import SwiftUI

struct MainView: View {
    @State var appState: AppState
    @State var viewModel: MainViewModel

    var body: some View {
        Group {
            switch viewModel.state {
            case .loading:
                ProgressView {
                    Text("Starting up")
                }
            case .authenticated:
                TabView(selection: $appState.tabRouter.selectedTab) {
                    Tab("Home", systemImage: "house", value: .home) {
                        appState.homeProvider.content
                    }
                    Tab("Shrine Secrets", systemImage: "circle.hexagonpath.fill", value: .shrineSecrets) {
                        appState.shrineSecretsProvider.content
                    }
                }
            case .notAuthenticated:
                appState.signInProvider.content
            }
        }.task {
            await viewModel.send(action: .observeUser)
        }
        .onChange(of: viewModel.state) {
            FerbyHaptics.tap()
        }
    }
}
