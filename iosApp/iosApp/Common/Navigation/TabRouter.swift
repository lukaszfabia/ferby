//
//  TabRouter.swift
//  iosApp
//
//  Created by Lukasz Fabia on 30/08/2026.
//

import Observation

@Observable
final class TabRouter<T: RouterDestinationProtocol> {
    let tabs: [T]
    var selectedTab: T
    
    init(tabs: [T], selectedTab: T) {
        self.tabs = tabs
        self.selectedTab = selectedTab
    }
    
    func select(_ tab: T) {
        self.selectedTab = tab
    }
}
