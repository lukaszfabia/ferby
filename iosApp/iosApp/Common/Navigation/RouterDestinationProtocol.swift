//
//  RouterDestinationProtocol.swift
//  iosApp
//
//  Created by Lukasz Fabia on 28/08/2026.
//

import Foundation

protocol RouterDestinationProtocol: Equatable, Hashable, Identifiable {}

extension RouterDestinationProtocol {
    var id: Self { self }
}
