package com.example.navegacioncifradopsp.ui.pantallaRegistrar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices.PIXEL_8
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.navegacioncifradopsp.common.UiEvent
import com.example.navegacioncifradopsp.domain.model.Usuario
import com.example.navegacioncifradopsp.ui.reutilizableOno.AuthPasswordField
import com.example.navegacioncifradopsp.ui.reutilizableOno.AuthScaffold
import com.example.navegacioncifradopsp.ui.reutilizableOno.AuthTextField
import com.example.navegacioncifradopsp.ui.reutilizableOno.Boton
import com.example.navegacioncifradopsp.ui.theme.NavegacionCifradoPSPTheme
import com.example.navegacioncifradopsp.ui.util.Dimens

@Composable
fun RegisterScreenViewModel(
    viewModel: RegistrarViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
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


    RegisterScreen(
        snackbarHostState = snackbarHostState,
        onRegister = { usuario ->
            viewModel.register(usuario)
        },
        onBackToLogin = onNavigateBack
        )

}
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onRegister: (Usuario) -> Unit,
    onBackToLogin: () -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    AuthScaffold(
        modifier = modifier,
        snackbarHostState = snackbarHostState,
        title = "Registrarse"
    ) {
        AuthTextField(
            value = name,
            onValueChange = { name = it },
            label = "Nombre",
            keyboardType = KeyboardType.Text
        )

        AuthTextField(
            value = email,
            onValueChange = { email = it },
            label = "Correo",
            keyboardType = KeyboardType.Email
        )

        AuthTextField(
            value = username,
            onValueChange = { username = it },
            label = "Usuario",
            keyboardType = KeyboardType.Text
        )

        AuthPasswordField(
            value = password,
            onValueChange = { password = it },
            label = "Contraseña"
        )

        Spacer(Modifier.height(Dimens.SectionSpacingCompact))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Dimens.ButtonsRowSpacingCompact)
        ) {
            Boton(
                text = "Crear cuenta",
                color = MaterialTheme.colorScheme.primary,
                onClick = { onRegister(Usuario(name.trim(), username.trim(), email.trim(),false, password)) }
            )

            Boton(
                text = "Volver a login",
                color = MaterialTheme.colorScheme.secondary,
                onClick = onBackToLogin
            )
        }
    }
}

@Preview(showBackground = true, device = PIXEL_8, showSystemUi = true)
@Composable
fun GreetingPreview() {
    NavegacionCifradoPSPTheme {
        RegisterScreen(
            onRegister = { _ -> },
            onBackToLogin = {}
        )
    }
}