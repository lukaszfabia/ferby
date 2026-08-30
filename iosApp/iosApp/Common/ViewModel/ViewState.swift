//
//  ViewState.swift
//  iosApp
//
//  Created by Lukasz Fabia on 28/08/2026.
//

import Foundation
import SharedLogic

/// Safe state for the view which contains main states for simple view. Use it when your state is trivial and has these states.
enum ViewState<T> {
    case loading
    case success(T)
    case failure(FerbyError)
}
