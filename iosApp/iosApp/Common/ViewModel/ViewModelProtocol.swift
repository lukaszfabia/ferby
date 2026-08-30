//
//  ViewModelProtocol.swift
//  iosApp
//
//  Created by Lukasz Fabia on 30/08/2026.
//

@MainActor
protocol ViewModelProtocol: AnyObject {
    associatedtype State
    associatedtype Action

    var state: State { get }
    func send(action: Action) async
}

extension ViewModelProtocol {
    func send(action: Action) {
        Task {
            await send(action: action)
        }
    }
}
