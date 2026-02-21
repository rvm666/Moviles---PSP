package com.example.navegacioncifradopsp.ui.pantallaProducción


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.navegacioncifradopsp.common.Constantes
import com.example.navegacioncifradopsp.common.UiEvent
import com.example.navegacioncifradopsp.domain.model.Produccion
import com.example.navegacioncifradopsp.ui.theme.NavegacionCifradoPSPTheme
import com.example.navegacioncifradopsp.ui.reutilizableOno.Boton
import com.example.navegacioncifradopsp.ui.reutilizableOno.DropdownField
import com.example.navegacioncifradopsp.ui.util.Dimens


@Composable
fun ProduccionScreenViewModel(
    viewModel: MainViewModel = hiltViewModel(),
    produccionId: Int,
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
                    is UiEvent.NavigateBack -> {
                        onNavigateBack()
                    }
                }
            }
        }
    }

    LaunchedEffect(produccionId){
        viewModel.cargarProduccion(produccionId)
    }

    ProduccionScreen(uiState = uiState,
        snackbarHostState = snackbarHostState,
        onLimpiar = { viewModel.limpiarPantalla() },
        onGuardar = {
            produccion -> viewModel.clickBotonGuardar(produccion)
        },
        onActualizar = { produccion -> viewModel.actualizarProduccion(produccion) }
    )

}
@Composable
fun ProduccionScreen(
    modifier: Modifier = Modifier,
    uiState: MainState,
    snackbarHostState : SnackbarHostState = remember { SnackbarHostState() },
    onLimpiar: () -> Unit = {},
    onGuardar: (Produccion) -> Unit = {},
    onActualizar: (Produccion) -> Unit = {},
) {



    var nombre by remember { mutableStateOf("") }
    var director by remember { mutableStateOf("") }
    var lanzamiento by remember { mutableStateOf(0) }
    var genero by remember { mutableStateOf("") }



    LaunchedEffect(uiState.produccion) {
        val p = uiState.produccion
        nombre = p.nombre
        director = p.director
        lanzamiento = p.lanzamiento
        genero = p.genero
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = Dimens.ScreenPaddingHorizontalCompact,
                    vertical = Dimens.ScreenPaddingVerticalCompact
                )
        ) {
            Text(
                text = Constantes.TITULO_PANTALLA,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )


                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text(Constantes.NOMBRE) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedLabelColor = MaterialTheme.colorScheme.primary
                    )
                )


                OutlinedTextField(
                    value = director,
                    onValueChange = { director = it },
                    label = { Text(Constantes.DIRECTOR) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedLabelColor = MaterialTheme.colorScheme.primary
                    )
                )


                DropdownField(
                    label = Constantes.GENERO,
                    value = genero,
                    options = listOf(
                        Constantes.GENERO,
                        Constantes.COMEDIA,
                        Constantes.DRAMA,
                        Constantes.FICCION,
                        Constantes.TERROR
                    ),
                    onSelected = { genero = it },
                    modifier = Modifier.fillMaxWidth()
                )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Dimens.ButtonsRowSpacingCompact)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Boton(
                        text = Constantes.LIMPIAR,
                        color = MaterialTheme.colorScheme.secondary,
                        onClick = { onLimpiar() }
                    )
                    Boton(
                        text = Constantes.ACTUALIZAR,
                        color = MaterialTheme.colorScheme.tertiary,
                        onClick = { onActualizar(
                            produccionActual(
                                nombre,
                                director,
                                lanzamiento,
                                genero

                            )
                        )}
                    )
                    Boton(
                        text = Constantes.GUARDAR,
                        color = MaterialTheme.colorScheme.primary,
                        onClick = { onGuardar(
                            produccionActual(
                                nombre,
                                director,
                                lanzamiento,
                                genero
                            )
                        ) }
                    )
                }
            }

        }
    }
}

    fun produccionActual(
        nombre: String,
        director: String,
        lanzamiento: Int,
        genero: String,
    ): Produccion {
        return Produccion(
            nombre = nombre,
            director = director,
            lanzamiento = lanzamiento,
            genero = genero,
        )
    }


    @Preview(showBackground = true, device = PIXEL_8, showSystemUi = true)
    @Composable
    fun GreetingPreview() {
        NavegacionCifradoPSPTheme {
            ProduccionScreen(
                uiState = MainState(
                    produccion = Produccion(
                        nombre = "",
                        director = "",
                        lanzamiento = 0,
                        genero = "Ficción",
                    )
                )
            )
        }
    }



