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
    private let shrineSecretsRouter = Router<ShrineSecretsDestination>(
        root: .root
    )
    private let signInRouter = Router<SignInDestination>(root: .root)
    private let homeRouter = Router<HomeDestination>(root: .root)
    
    @ObservationIgnored
    private(set) lazy var shrineSecretsProvider = {
        ShrineSecretsProvider(
            router: MainActor.assumeIsolated {
                shrineSecretsRouter
            }
        )
    }()
    
    @ObservationIgnored
    private(set) lazy var signInProvider = {
        SignInProvider(
            router: MainActor.assumeIsolated {
                signInRouter
            }
        )
    }()
    
    
    @ObservationIgnored
    private(set) lazy var homeProvider = {
        HomeProvider(
            router: MainActor.assumeIsolated {
                homeRouter
            }
        )
    }()
    
    var tabRouter: TabRouter<TabRouterDestination> = .init(
        tabs: [.home, .shrineSecrets],
        selectedTab: .home
    )
}
