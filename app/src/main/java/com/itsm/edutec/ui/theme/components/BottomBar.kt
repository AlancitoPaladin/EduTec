package com.itsm.edutec.ui.theme.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Preview
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.itsm.edutec.ui.theme.navigation.StudentTab

@Composable
fun BottomBar(
    selectedTab: StudentTab,
    onTabSelected: (StudentTab) -> Unit
) {
    val items = StudentTab.entries.toTypedArray()
    val selectedIcons = listOf(Icons.Filled.Preview, Icons.Filled.Apps, Icons.Filled.Person)
    val unselectedIcons = listOf(Icons.Outlined.Preview, Icons.Outlined.Apps, Icons.Outlined.Person)

    NavigationBar {
        items.forEachIndexed { index, tab ->
            NavigationBarItem(
                icon = {
                    Icon(
                        if (selectedTab == tab) selectedIcons[index] else unselectedIcons[index],
                        contentDescription = tab.title
                    )
                },
                label = { Text(tab.title, fontSize = 12.sp) },
                selected = selectedTab == tab,
                onClick = { onTabSelected(tab) }
            )
        }
    }
}
