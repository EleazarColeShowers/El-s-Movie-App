package com.example.elsmovieapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomBar() {
    var selectedItem by remember { mutableStateOf("Home") }


    val items = listOf(
        "Home" to Icons.Default.Home,
        "Search" to Icons.Default.Search,
        "Profile" to Icons.Default.Person
    )

    NavigationBar(
        containerColor = Color.Transparent,
        modifier = Modifier.height(65.dp)
    ) {
        items.forEach { (label, icon) ->
            val isSelected = selectedItem == label
            NavigationBarItem(
                selected = isSelected,
                onClick = { selectedItem = label },
                icon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = label,
                            tint = if (isSelected) Color.Cyan else Color.Gray
                        )
                        if (isSelected) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = label,
                                color = Color.Cyan,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent // no ripple bg
                )
            )
        }
    }
}
