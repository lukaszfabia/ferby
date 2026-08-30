//
//  Router.swift
//  iosApp
//
//  Created by Lukasz Fabia on 28/08/2026.
//

import Observation

@Observable
final class Router<Destination: RouterDestinationProtocol> {
    var root: Destination
    var path: [Destination] = []
    
    init(root: Destination, path: [Destination] = []) {
        self.root = root
        self.path = path
    }
    
    func reset(to root: Destination) {
        self.path = []
        self.root = root
    }
    
    func navigate(to destination: Destination) {
        self.path.append(destination)
    }
    
    func pop() {
        guard !self.path.isEmpty else { return }
        self.path.removeLast()
    }
}
