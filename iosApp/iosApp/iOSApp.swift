import SwiftUI
import SharedLogic

@main
struct iOSApp: App {
    @State private var router = Router<ShrineSecretsDestination>.init(root: .root)
    
    init() {
        Koin.shared.setupBackend()
    }
    
    var body: some Scene {
        WindowGroup {
            ShrineSecretsProvider(router: router, viewModel: ShrineSecretsViewModel()).content
        }
    }
}
