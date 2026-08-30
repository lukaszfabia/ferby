import SwiftUI
import SharedLogic

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    private let appState = AppState()
    
    init() {
        Koin.shared.setupBackend()
    }
    
    var body: some Scene {
        WindowGroup {
            MainView(appState: appState)
        }
    }
}
