//
//  AppState.swift
//  iosApp
//
//  Created by Lukasz Fabia on 30/08/2026.
//

import Observation

enum TabRouterDestination: RouterDestinationProtocol {
    case home
    case shrineSecrets
}

@Observable
@MainActor
final class AppState {
    private let shrineSecretsRouter = Router<ShrineSecretsDestination>(root: .root)
    
    @ObservationIgnored
    private(set) lazy var shrineSecretsProvider = {
        ShrineSecretsProvider(
            router: MainActor.assumeIsolated {
                shrineSecretsRouter
            }
        )
    }()
    
    var tabRouter: TabRouter<TabRouterDestination> = .init(tabs: [.home, .shrineSecrets], selectedTab: .shrineSecrets)
}
