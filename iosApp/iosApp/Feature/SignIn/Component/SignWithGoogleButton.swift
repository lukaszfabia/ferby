//
//  SignWithGoogleButton.swift
//  iosApp
//
//  Created by Lukasz Fabia on 03/09/2026.
//

import SwiftUI

fileprivate let googleImageWidth: CGFloat = 188
fileprivate let googleImageHeight: CGFloat = 44
fileprivate let googleImageBorderWidth: CGFloat = 1

struct SignWithGoogleButton: View {
    var isLoading: Bool = false
    var onSignInCLick: () -> Void = {}
    
    var body: some View {
        Button(action: onSignInCLick) {
            Group {
                if isLoading {
                    ProgressView()
                        .tint(.accent)
                } else {
                    Image(.google)
                        .resizable()
                        .scaledToFit()
                }
            }
            .frame(maxWidth: .infinity)
        }
        .frame(width: googleImageWidth, height: googleImageHeight)
        .background(.surface)
        .clipShape(.capsule)
        .overlay(
            Capsule()
                .stroke(Color.tertiary, lineWidth: googleImageBorderWidth)
        )
        .disabled(isLoading)
    }
}
