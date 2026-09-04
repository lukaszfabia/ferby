//
//  FerbyHaptics.swift
//  iosApp
//
//  Created by Lukasz Fabia on 03/09/2026.
//

import UIKit

enum FerbyHaptics {
    static func tap() {
        let generator = UIImpactFeedbackGenerator(style: .light)
        generator.impactOccurred()
    }

    static func success() {
        let generator = UINotificationFeedbackGenerator()
        generator.notificationOccurred(.success)
    }

    static func error() {
        let generator = UINotificationFeedbackGenerator()
        generator.notificationOccurred(.error)
    }
}
