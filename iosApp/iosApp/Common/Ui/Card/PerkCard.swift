//
//  PerkCard.swift
//  iosApp
//
//  Created by Lukasz Fabia on 28/08/2026.
//

import SwiftUI
import SharedLogic

struct PerkCard: View {
    let perk: Perk
    
    var body: some View {
        VStack {
            Text(perk.name)
            Text(perk.owner.name)
        }
    }
}
