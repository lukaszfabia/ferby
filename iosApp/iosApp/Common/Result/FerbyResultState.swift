//
//  FerbyResult.swift
//  iosApp
//
//  Created by Lukasz Fabia on 01/09/2026.
//

import SharedLogic

/// Result type used to handle native functions and their results.
enum FerbyResultState<T> {
    case success(T)
    case failure(FerbyError)
}

extension FerbyResultState {
    func fold<R>(onSuccess: (T) -> R, onFailure: (FerbyError) -> R) -> R {
        switch self {
        case .success(let t):
            return onSuccess(t)
        case .failure(let ferbyError):
            return onFailure(ferbyError)
        }
    }
    
    func fold<R>(onSuccess: (T) async -> R, onFailure: (FerbyError) async -> R) async -> R {
        switch self {
        case .success(let t):
            return await onSuccess(t)
        case .failure(let ferbyError):
            return await onFailure(ferbyError)
        }
    }
}

func mapToFerbyResultState<T>(_ ferbyResult: FerbyResult<T>?) -> FerbyResultState<T> {
    guard let ferbyResult else {
        return .failure(ApiErrorNoData())
    }
    
    switch onEnum(of: ferbyResult) {
    case .success(let successResult):
        guard let data = successResult.data else {
            return .failure(ApiErrorNoData())
        }
        
        return .success(data)
    case .failure(let failureResult):
        return .failure(failureResult.error)
    }
}
