//
//  ShrineSecretsView.swift
//  iosApp
//
//  Created by Lukasz Fabia on 22/08/2026.
//

import SharedLogic
import SwiftUI

struct ShrineSecretsView: View {
    @Environment(Router<ShrineSecretsDestination>.self) private var router
    @State var viewModel: ShrineSecretsViewModel

    var body: some View {
        RoutingView(router: router) { destination in
            switch destination {
            case .root:
                RootView(viewModel: viewModel)
            case let .detail(perk):
                ShrineSecretsDetailProvider(perk).content
            }
        }
        .environment(router)
    }
}

private struct RootView: View {
    @Environment(Router<ShrineSecretsDestination>.self) private var router
    @Bindable var viewModel: ShrineSecretsViewModel

    var body: some View {
        VStack {
            switch viewModel.state {
            case .loading:
                ProgressView()
            case .success(let data):
                List(data.perks.map { $0 }, id: \.self) { perk in
                    Button {
                        router.navigate(to: .detail(perk: perk))
                    } label: {
                        Text(perk.name)
                    }
                }
            case .failure(let error):
                Text(FerbyErrorMapper.shared.map(error: error))
            }
        }.task {
            await viewModel.loadCurrentShrineSecrets()
        }
    }
}
