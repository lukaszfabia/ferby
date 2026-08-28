//
//  RoutingView.swift
//  iosApp
//
//  Created by Lukasz Fabia on 28/08/2026.
//

import SwiftUI

struct RoutingView<Destination: RouterDestinationProtocol, Content: View>: View {
    @State var router: Router<Destination>
    @ViewBuilder var content: (Destination) -> Content
    var body: some View {
        NavigationStack(path: $router.path) {
            content(router.root)
                .navigationDestination(for: Destination.self, destination: content)
        }
    }
}
