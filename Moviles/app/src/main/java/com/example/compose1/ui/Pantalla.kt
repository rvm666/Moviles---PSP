package com.example.compose1.ui

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.anadirusuarios1.ui.MainViewModel

@Composable
fun UserFormScreenViewModel(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val snackbarHostState = remember { SnackbarHostState() }

    // Observar eventos de un solo uso con lifecycle awareness
    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is com.example.composeapp.viewmodel.UiEvent.ShowSnackbar -> {
                        snackbarHostState.showSnackbar(
                            message = event.message,
                            duration = SnackbarDuration.Short
                        )
                    }
                    is com.example.composeapp.viewmodel.UiEvent.Navigate -> {
                        // Aquí puedes manejar navegación si es necesario
                    }
                }
            }
        }
    }

    UserFormScreen(uiState = uiState,
        snackbarHostState = snackbarHostState,
        onChangeUsusario = { usuario -> viewModel.updateUsuario(usuario) },
        onLimpiarFormulario = { viewModel.updateUsuario(Usuario()) },
        onNavegarSiguiente = { viewModel.cargarUsuario(uiState.indiceActual)},
        onNavegarAnterior = {
            viewModel.cargarUsuario(uiState.indiceActual - 1)
        },
        onGuardar = {
            viewModel.guardarUsuario()
        },
        onBorrar = { viewModel.borrarUsuario() },
        onActualizar = { viewModel.actualizarUsuario() },
    )






}