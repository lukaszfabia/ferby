//
//  SignInState.swift
//  iosApp
//
//  Created by Lukasz Fabia on 02/09/2026.
//

import SharedLogic
import GoogleSignInSwift

struct SignInState {
    var isLoading: Bool = false
    var error: FerbyError? = nil
    
    mutating func onError(error: FerbyError) {
        self.error = error
        self.isLoading = false
    }
}
