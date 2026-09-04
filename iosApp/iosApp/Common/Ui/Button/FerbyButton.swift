//
//  FerbyButton.swift
//  iosApp
//
//  Created by Lukasz Fabia on 03/09/2026.
//

import SwiftUI

struct FerbyPrimaryButtonStyle: ButtonStyle {
    func makeBody(configuration: Configuration) -> some View {
        configuration
            .label
    }
}

struct FerbyButton: View {
    let onClick: () -> Void = {}
    var title: String = ""
    var icon: Image? = nil
    var isLoading: Bool = false
    var isDisabled: Bool = false

    var body: some View {
        Button(action: onClick) {
            if isLoading {
                ProgressView()
            } else {
                Text("Sign with Google")
            }
        }.disabled(isDisabled)
    }
}
