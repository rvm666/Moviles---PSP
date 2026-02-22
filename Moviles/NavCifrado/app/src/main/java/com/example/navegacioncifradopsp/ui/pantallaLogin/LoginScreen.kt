package com.example.navegacioncifradopsp.ui.pantallaLogin


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
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
import com.example.navegacioncifradopsp.ui.reutilizableOno.AuthPasswordField
import com.example.navegacioncifradopsp.ui.reutilizableOno.AuthScaffold
import com.example.navegacioncifradopsp.ui.reutilizableOno.AuthTextField
import com.example.navegacioncifradopsp.ui.reutilizableOno.Boton
import com.example.navegacioncifradopsp.ui.theme.NavegacionCifradoPSPTheme
import com.example.navegacioncifradopsp.ui.util.Dimens

@Composable
fun LoginScreenViewModel(
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit
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
                    is UiEvent.NavigateToHome -> navigateToHome()
                }
            }
        }
    }



    LoginScreen(
        snackbarHostState = snackbarHostState,
        onLogin = { username, password -> viewModel.login(username, password)},
        navigateToRegister = navigateToRegister
        )

}
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onLogin: (username: String, password: String) -> Unit,
    navigateToRegister: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    AuthScaffold(
        modifier = modifier,
        snackbarHostState = snackbarHostState,
        title = "Iniciar sesión"
    ) {
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
                text = "Login",
                color = MaterialTheme.colorScheme.primary,
                onClick = { onLogin(username.trim(), password) }
            )

            Boton(
                text = "Registrarse",
                color = MaterialTheme.colorScheme.secondary,
                onClick = navigateToRegister
            )
        }
    }
}

@Preview(showBackground = true, device = PIXEL_8, showSystemUi = true)
@Composable
fun GreetingPreview() {
    NavegacionCifradoPSPTheme {
        LoginScreen(
            onLogin = { _, _ -> },
            navigateToRegister = {}
        )
    }
}


