package com.example.navegacioncifradopsp.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.navegacioncifradopsp.ui.listaProducciones.ListProduccionesScreenViewModel
import com.example.navegacioncifradopsp.ui.listaSecretosCreados.SecretosCreadosScreenViewModel
import com.example.navegacioncifradopsp.ui.listaSecretosRecibidos.SecretosRecibidosScreenViewModel
import com.example.navegacioncifradopsp.ui.navigation.Routes.*
import com.example.navegacioncifradopsp.ui.pantallaLogin.LoginScreenViewModel
import com.example.navegacioncifradopsp.ui.pantallaPerfil.PerfilScreenViewModel
import com.example.navegacioncifradopsp.ui.pantallaProducción.ProduccionScreenViewModel
import com.example.navegacioncifradopsp.ui.pantallaRegistrar.RegisterScreenViewModel
import com.example.navegacioncifradopsp.ui.secretoDetailOAniadir.SecretoDetailScreenViewModel


@Composable
fun NavigationWrapper() {
    val backStack = rememberNavBackStack(Login)
    val current = backStack.lastOrNull()

    val showBars = current !is Login && current !is Register

    fun navigateTo(route: Routes) { backStack.add(route) }

    fun navigateToTab(tab: Routes) {
        if (backStack.lastOrNull() == tab) return

        while (true) {
            val top = backStack.lastOrNull() ?: break
            val isRootTab = top is ListaProducciones || top is Secretos || top is Perfil
            if (isRootTab) break
            backStack.removeLastOrNull()
        }

        backStack.removeLastOrNull()
        backStack.add(tab)
    }

    fun pop() { backStack.removeLastOrNull() }


    if (!showBars) {
        NavDisplay(
            backStack = backStack,
            onBack = { pop() },
            entryProvider = entryProvider {
                entry<Login> {
                    LoginScreenViewModel(
                        navigateToRegister = { navigateTo(Register) },
                        navigateToHome = {
                            pop()
                            navigateTo(ListaProducciones)
                        }
                    )
                }
                entry<Register> {
                    RegisterScreenViewModel(
                        onNavigateBack = { pop() }
                    )
                }
            }
        )
        return
    }


    MainScaffold(
        currentRoute = current,
        onBackClick = { pop() },
        onTabSelected = { tabRoute ->
            navigateToTab(tabRoute)
        }
    ) { padding ->
        NavDisplay(
            backStack = backStack,
            onBack = {pop()},
            entryProvider = entryProvider{
                entry<Login> {
                    LoginScreenViewModel (
                        navigateToRegister = { navigateTo(Register)},
                        navigateToHome = { navigateTo(ListaProducciones)}
                    )
                }
                entry<Register>{
                    RegisterScreenViewModel{
                        pop()
                    }
                }
                entry<ListaProducciones>{
                    ListProduccionesScreenViewModel(
                        onAdd = { navigateTo(ProduccionDetail(-1)) },
                        onNavigateDetalle = { produccionId ->  navigateTo(ProduccionDetail(produccionId)) }
                    )

                }
                entry<ProduccionDetail>{key ->
                    ProduccionScreenViewModel(
                        produccionId = key.id,
                        onNavigateBack = { pop() }
                    )

                }
                entry<Perfil>{
                    PerfilScreenViewModel(
                        onLogout = {
                            while (backStack.removeLastOrNull() != null) { }
                            navigateTo(Login)
                        }
                    )
                }
                entry<Secretos> {
                    SecretosCreadosScreenViewModel(
                        onAdd = { navigateTo(SecretoDetail(-1)) },
                        onNavigateDetalle = { id -> navigateTo(SecretoDetail(id)) },
                        onVerRecibidos = { navigateTo(SecretosRecibidos) }
                    )
                }
                entry<SecretoDetail> { key ->
                    SecretoDetailScreenViewModel(
                        onNavigateBack = { pop() },
                        secretoId = key.id
                    )
                }
                entry<SecretosRecibidos> {
                    SecretosRecibidosScreenViewModel(
                        onNavigateBack = { pop() }
                    )
                }
            },
            transitionSpec = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(250)
                ) togetherWith slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(250)
                )
            },
            popTransitionSpec = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(250)
                ) togetherWith slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(250)
                )
            },
            predictivePopTransitionSpec = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(250)
                ) togetherWith slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(250)
                )
            }
        )

    }

}


