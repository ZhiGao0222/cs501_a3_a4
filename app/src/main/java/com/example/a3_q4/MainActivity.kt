package com.example.a3_q4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ResponsiveAppScreen()
            }
        }
    }
}

data class NavItem(val label: String, val icon: ImageVector)

@Composable
fun ResponsiveAppScreen() {
    val navItems = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("Profile", Icons.Default.Person),
        NavItem("Settings", Icons.Default.Settings)
    )

    var selectedIndex by remember { mutableIntStateOf(0) }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isWideScreen = this.maxWidth.value > 600f

        if (isWideScreen) {
            WideLayout(
                navItems = navItems,
                selectedIndex = selectedIndex,
                onSelectItem = { selectedIndex = it }
            )
        } else {
            NarrowLayout(
                navItems = navItems,
                selectedIndex = selectedIndex,
                onSelectItem = { selectedIndex = it }
            )
        }
    }
}

@Composable
fun WideLayout(
    navItems: List<NavItem>,
    selectedIndex: Int,
    onSelectItem: (Int) -> Unit
) {
    Row(modifier = Modifier.fillMaxSize()) {
        NavigationRail(
            modifier = Modifier.fillMaxHeight()
        ) {
            Spacer(Modifier.height(32.dp))
            navItems.forEachIndexed { index, item ->
                NavigationRailItem(
                    icon = { Icon(item.icon, contentDescription = item.label) },
                    label = { Text(item.label) },
                    selected = selectedIndex == index,
                    onClick = { onSelectItem(index) }
                )
            }
        }

        Divider(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight(),
            color = MaterialTheme.colorScheme.outlineVariant
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            ScreenContent(navItems[selectedIndex].label)
        }
    }
}

@Composable
fun NarrowLayout(
    navItems: List<NavItem>,
    selectedIndex: Int,
    onSelectItem: (Int) -> Unit
) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                navItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = selectedIndex == index,
                        onClick = { onSelectItem(index) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            ScreenContent(navItems[selectedIndex].label)
        }
    }
}

@Composable
fun ScreenContent(title: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Welcome to the $title screen!",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}