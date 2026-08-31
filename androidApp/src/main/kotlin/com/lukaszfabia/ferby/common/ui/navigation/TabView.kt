package com.lukaszfabia.ferby.common.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.lukaszfabia.ferby.common.navigation.TabRoute
import com.lukaszfabia.ferby.main.model.TabUi

@Composable
fun TabView(selectedTab: TabRoute, tabs: List<TabUi>, onTabItemClick: (route: TabRoute) -> Unit) {
    NavigationBar {
        for(tab in tabs) {
            NavigationBarItem(
                selected = tab.route == selectedTab,
                onClick = { onTabItemClick(tab.route) },
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = stringResource(tab.title)
                    )
                },
            )
        }
    }
}