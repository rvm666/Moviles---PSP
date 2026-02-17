package com.example.navegacioncifradopsp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.navegacioncifradopsp.ui.listaProducciones.ListScreenViewModel
import com.example.navegacioncifradopsp.ui.pantallaLogin.LoginScreenViewModel
import com.example.navegacioncifradopsp.ui.pantallaPerfil.PerfilScreenViewModel
import com.example.navegacioncifradopsp.ui.pantallaProducción.ProduccionScreenViewModel
import com.example.navegacioncifradopsp.ui.pantallaRegistrar.RegisterScreenViewModel

@Composable
fun NavigationWrapper() {
    val backStack = rememberNavBackStack {mutableStateListOf<Any>(Login())}


    NavDisplay(
        backStack = backStack,
        onBack = {backStack.removeLastOrNull()},
        entryProvider ={

        }
    )

}