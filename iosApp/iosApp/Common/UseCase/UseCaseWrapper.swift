//
//  UseCaseWrapper.swift
//  iosApp
//
//  Created by Lukasz Fabia on 28/08/2026.
//

import SharedLogic

/// Perfrom use case without data transformation. Use when you just want to display only data from use case.
/// - Parameter useCase: Use case to execute.
/// - Returns: Typed state for the view.
func execute<T>(
    useCase: () async throws -> FerbyResult<T>
) async -> ViewState<T> {
    return await execute(useCase: useCase, transform: { $0 })
}

/// Perform use case with side effects provided in the transform closure.
/// - Parameters:
///   - useCase: Use case to execute.
///   - transform: Data transformation - your side effects.
/// - Returns: Typed state for the view.
func execute<T, R>(
    useCase: () async throws -> FerbyResult<T>,
    transform: @escaping (T) -> R
) async -> ViewState<R> {
    do {
        let result = try await useCase()
        var state: ViewState<R> = .loading
        
        result.fold { data in
            guard let data else {
                state = .failure(ApiErrorNoDataError.shared)
                return
            }
            
            state = .success(transform(data))
            return
        } onFailure: { error in
            state = .failure(error)
            return
        }

        return state
    } catch {
        return .failure(ApiErrorClientError.shared)
    }
}
