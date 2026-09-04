import SwiftUI
import SharedLogic

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    
    var body: some Scene {
        WindowGroup {
            MainView(appState: .init(), viewModel: .init())
                .preferredColorScheme(.dark)
        }
    }
}
