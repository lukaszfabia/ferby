//
//  AppDelegate.swift
//  iosApp
//
//  Created by Lukasz Fabia on 30/08/2026.
//

import UIKit
import FirebaseCore
import SharedLogic

final class AppDelegate: NSObject, UIApplicationDelegate {
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil
    ) -> Bool {
        Koin.shared.setupBackend()
        FirebaseApp.configure()
        return true
    }
}
