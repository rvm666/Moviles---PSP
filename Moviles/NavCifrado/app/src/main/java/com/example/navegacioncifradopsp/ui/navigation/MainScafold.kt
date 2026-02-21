package com.example.navegacioncifradopsp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    currentRoute: Any?,
    onBackClick: () -> Unit,
    onTabSelected: (Routes) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val isTabRoot = currentRoute is Routes.ListaProducciones ||
            currentRoute is Routes.Secretos ||
            currentRoute is Routes.Perfil

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(currentRoute.toString()) },
                navigationIcon = {
                    if (!isTabRoot) {
                        IconButton(onClick = onBackClick) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute is Routes.ListaProducciones,
                    onClick = { onTabSelected(Routes.ListaProducciones) },
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("Inicio") }
                )
                NavigationBarItem(
                    selected = currentRoute is Routes.Secretos,
                    onClick = { onTabSelected(Routes.Secretos) },
                    icon = { Icon(Icons.Default.Lock, null) },
                    label = { Text("Secretos") }
                )
                NavigationBarItem(
                    selected = currentRoute is Routes.Perfil,
                    onClick = { onTabSelected(Routes.Perfil) },
                    icon = { Icon(Icons.Default.Person, null) },
                    label = { Text("Perfil") }
                )
            }
        },
        content = content
    )
}