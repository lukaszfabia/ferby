import SwiftUI
import SharedLogic

@main
struct iOSApp: App {
    
    init() {
        Koin.shared.setupBackend()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
