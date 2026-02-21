package com.example.navegacioncifradopsp.ui.listaSecretosRecibidos

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices.PIXEL_8
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.navegacioncifradopsp.common.UiEvent
import com.example.navegacioncifradopsp.ui.listaSecretosCreados.ListaSecretosCreadosViewModel

@Composable
fun SecretosRecibidosScreenViewModel(
    viewModel: ListaSecretosRecibidosViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = { },
) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val lifecycleOwner = LocalLifecycleOwner.current


    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is UiEvent.ShowSnackbar -> {
                        snackbarHostState.showSnackbar(
                            message = event.message,
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        }
    }

    SecretosRecibidosScreen()
}


@Composable
fun SecretosRecibidosScreen(
    modifier: Modifier = Modifier,

    ){

}


@Preview(showBackground = true, device = PIXEL_8, showSystemUi = true)
@Composable
fun SecretosRecibidosScreenPreview(){
    SecretosRecibidosScreen()
}