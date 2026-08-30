//
//  MainView.swift
//  iosApp
//
//  Created by Lukasz Fabia on 30/08/2026.
//

import SwiftUI

struct MainView: View {
    @State var appState: AppState

    var body: some View {
        TabView(selection: $appState.tabRouter.selectedTab) {
            Tab("Shrine Secrets", systemImage: "", value: .shrineSecrets) {
                appState.shrineSecretsProvider.content
            }
            Tab("Home", systemImage: "home", value: .home) {
                Text("Commit schemacide with seppuku wish a fake")
            }
        }
    }
}
