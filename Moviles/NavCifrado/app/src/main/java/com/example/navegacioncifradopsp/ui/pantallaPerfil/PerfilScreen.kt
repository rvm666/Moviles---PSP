package com.example.navegacioncifradopsp.ui.pantallaPerfil

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices.PIXEL_8
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.navegacioncifradopsp.common.UiEvent
import com.example.navegacioncifradopsp.domain.model.Usuario
import com.example.navegacioncifradopsp.ui.reutilizableOno.Boton
import com.example.navegacioncifradopsp.ui.reutilizableOno.ProfileRow
import com.example.navegacioncifradopsp.ui.theme.NavegacionCifradoPSPTheme
import com.example.navegacioncifradopsp.ui.util.Dimens

@Composable
fun PerfilScreenViewModel(
    viewModel: PerfilViewModel = hiltViewModel(),
    onLogout: () -> Unit
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

    ProfileScreen(uiState = uiState,
        onLogout = onLogout

        )

}

@Composable
fun ProfileScreen(
    uiState: PerfilState,
    modifier: Modifier = Modifier,
    onEnable2FA: () -> Unit,
    onLogout: () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = Dimens.ScreenPaddingHorizontalCompact,
                    vertical = Dimens.ScreenPaddingVerticalCompact
                ),
            verticalArrangement = Arrangement.spacedBy(Dimens.SectionSpacingCompact)
        ) {
            Text(
                text = "Perfil",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(Dimens.InlineSpacing),
                    verticalArrangement = Arrangement.spacedBy(Dimens.SectionSpacingCompact)
                ) {
                    ProfileRow(label = "Nombre", value = uiState.usuairo.nombre)
                    ProfileRow(label = "Usuario", value = uiState.usuairo.usuario)
                    ProfileRow(label = "Email", value = uiState.usuairo.email)
                    ProfileRow(label = "Rol", value = if (uiState.usuairo.isAdmin) "Admin" else "Usuario")
                }
            }

            Spacer(Modifier.height(Dimens.SectionSpacingCompact))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Dimens.ButtonsRowSpacingCompact)
            ) {
                Boton(
                    text = "Activar 2FA",
                    color = MaterialTheme.colorScheme.primary,
                    onClick = onEnable2FA
                )

                Boton(
                    text = "Cerrar sesión",
                    color = MaterialTheme.colorScheme.secondary,
                    onClick = onLogout
                )
            }
        }
    }
}




@Preview(showBackground = true, device = PIXEL_8, showSystemUi = true)
@Composable
fun GreetingPreview() {
    NavegacionCifradoPSPTheme {
        ProfileScreen(
            uiState = PerfilState(
                usuario = Usuario(
                nombre = "Mariguano",
                usuario = "mariguano23",
                email = "mariguano@email.com",
                isAdmin = false
            )),
            onEnable2FA = {}
        )
    }
}