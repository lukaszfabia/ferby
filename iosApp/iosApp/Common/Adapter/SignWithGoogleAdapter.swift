//
//  SignWithGoogleAdapter.swift
//  iosApp
//
//  Created by Lukasz Fabia on 31/08/2026.
//

import UIKit
import SharedLogic
import GoogleSignIn

@MainActor
protocol SignWithGoogleAdapter {
    func signIn() async -> FerbyResultState<GoogleCredential>
}

@MainActor
final class SignWithGoogleAdapterImpl: SignWithGoogleAdapter {
    
    func signIn() async -> FerbyResultState<GoogleCredential> {
        do {
            guard let viewController = presentingViewController else {
                return .failure(GoogleSsoErrorConfiguration.shared)
            }

            let result = try await GIDSignIn.sharedInstance.signIn(
                withPresenting: viewController
            )

            guard let idToken = result.user.idToken?.tokenString else {
                return .failure(GoogleSsoErrorMissingToken.shared)
            }
            let accessToken = result.user.accessToken.tokenString

            return .success(GoogleCredential(idToken: idToken, accessToken: accessToken))
        } catch let error as GIDSignInError {
            if (error.code == .canceled) {
                return .failure(GoogleSsoErrorCancelled.shared)
            }
            
            return .failure(GoogleSsoErrorSignInFailed.shared)
        } catch {
            return .failure(GoogleSsoErrorSignInFailed.shared)
        }
    }

    private var presentingViewController: UIViewController? {
        UIApplication.shared.connectedScenes
            .compactMap { $0 as? UIWindowScene }
            .flatMap(\.windows)
            .first { $0.isKeyWindow }?
            .rootViewController
    }
}
