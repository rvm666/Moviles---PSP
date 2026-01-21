package com.example.composeapp.ui


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices.PIXEL_8
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.composeapp.common.Constantes
import com.example.composeapp.common.UiEvent
import com.example.composeapp.domain.model.Produccion
import com.example.composeapp.ui.theme.AppTheme
import com.example.composeapp.ui.reutilizableOno.Boton
import com.example.composeapp.ui.reutilizableOno.DropdownField
import com.example.composeapp.ui.reutilizableOno.FechaPickerField
import com.example.composeapp.ui.reutilizableOno.RatingStars
import com.example.composeapp.ui.util.DeviceConfiguration
import com.example.composeapp.ui.util.Dimens


@Composable
fun ProduccionScreenViewModel(
    viewModel: MainViewModel = hiltViewModel()
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


    ProduccionScreen(uiState = uiState,
        snackbarHostState = snackbarHostState,
        onLimpiar = { viewModel.limpiarPantalla() },
        onNavegarSiguiente = { viewModel.siguienteProduccion()},
        onNavegarAnterior = {
            viewModel.anteriorProduccion()
        },
        onGuardar = {
            produccion -> viewModel.clickBotonGuardar(produccion)
        },
        onBorrar = { produccion -> viewModel.clickBotonBorrar(produccion) },
        onActualizar = { produccion -> viewModel.actualizarProduccion(produccion) },
        onEsPelicula = { isChecked -> viewModel.esPelicula(isChecked) }
    )

}
@Composable
fun ProduccionScreen(
    modifier: Modifier = Modifier,
    uiState: MainState,
    snackbarHostState : SnackbarHostState = remember { SnackbarHostState() },
    onLimpiar: () -> Unit = {},
    onNavegarSiguiente: () -> Unit = {},
    onNavegarAnterior: () -> Unit = {},
    onGuardar: (Produccion) -> Unit = {},
    onBorrar: (Produccion) -> Unit = {},
    onActualizar: (Produccion) -> Unit = {},
    onEsPelicula: (Boolean) -> Unit = {},
) {

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass


    var nombre by remember { mutableStateOf("") }
    var director by remember { mutableStateOf("") }
    var esPelicula by remember { mutableStateOf<Boolean?>(null) }
    var lanzamiento by remember { mutableStateOf("") }
    var temporadas by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var pais by remember { mutableStateOf("") }
    var valoracion by remember { mutableIntStateOf(0) }


    LaunchedEffect(uiState.produccion, uiState.indiceProduccion) {
        val p = uiState.produccion
        nombre = p.nombre
        director = p.director
        esPelicula = p.esPelicula
        lanzamiento = p.lanzamiento.orEmpty()
        temporadas = p.numeroTemporadas?.toString().orEmpty()
        genero = p.genero
        pais = p.pais
        valoracion = p.valoracion.toInt().coerceIn(0, 5)
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
                .padding(horizontal = if(DeviceConfiguration.fromWindowSizeClass(windowSizeClass) == DeviceConfiguration.MOBILE_PORTRAIT)  Dimens.ScreenPaddingHorizontalCompact else Dimens.ScreenPaddingHorizontal,
                    vertical = if(DeviceConfiguration.fromWindowSizeClass(windowSizeClass) == DeviceConfiguration.MOBILE_PORTRAIT) Dimens.ScreenPaddingVerticalCompact else Dimens.ScreenPaddingVertical),
            verticalArrangement = Arrangement.spacedBy(if(DeviceConfiguration.fromWindowSizeClass(windowSizeClass) == DeviceConfiguration.MOBILE_PORTRAIT) Dimens.SectionSpacingCompact else Dimens.SectionSpacing)
        ) {
            Text(
                text = Constantes.TITULO_PANTALLA,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                style = if(DeviceConfiguration.fromWindowSizeClass(windowSizeClass) == DeviceConfiguration.MOBILE_PORTRAIT)  MaterialTheme.typography.titleLarge else MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            if(DeviceConfiguration.fromWindowSizeClass(windowSizeClass) == DeviceConfiguration.MOBILE_PORTRAIT) {
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


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        Constantes.TIPO,
                        modifier = Modifier.padding(end = Dimens.LabelEndPadding),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(Dimens.InlineSpacing)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = esPelicula == true,
                                onClick = { esPelicula = true; onEsPelicula(true) },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MaterialTheme.colorScheme.primary
                                )
                            )
                            Text(
                                Constantes.PELICULA,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = esPelicula == false,
                                onClick = { esPelicula = false; onEsPelicula(false) },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MaterialTheme.colorScheme.primary
                                )
                            )
                            Text(
                                Constantes.SERIE,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }

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

                FechaPickerField(
                    value = lanzamiento,
                    onValueChange = { lanzamiento = it },
                    modifier = Modifier.fillMaxWidth()
                )


                if (esPelicula == false) {
                    OutlinedTextField(
                        value = temporadas,
                        onValueChange = { temporadas = it.filter { ch -> ch.isDigit() } },
                        label = { Text(Constantes.N_SEASONS) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            focusedLabelColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }

                DropdownField(
                    label = Constantes.GENERO,
                    value = genero,
                    options = listOf(Constantes.GENERO, Constantes.COMEDIA, Constantes.DRAMA, Constantes.FICCION, Constantes.TERROR),
                    onSelected = { genero = it },
                    modifier = Modifier.fillMaxWidth()
                )

                DropdownField(
                    label = Constantes.PAIS,
                    value = pais,
                    options = listOf(Constantes.PAIS, Constantes.ESTADOS_UNIDOS, Constantes.CHINA, Constantes.JAPON, Constantes.REINO_UNIDO, Constantes.FRANCIA, Constantes.ESPANIA),
                    onSelected = { pais = it },
                    modifier = Modifier.fillMaxWidth()
                )


                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        Constantes.VALORACION,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    RatingStars(
                        rating = valoracion,
                        onRatingChanged = { valoracion = it },
                        modifier = Modifier.padding(top = Dimens.RatingTopPadding)
                    )
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.LandscapeColumnGap)
                ) {
                    Column(
                        modifier = Modifier.weight(Dimens.LandscapeColumnWeight),
                        verticalArrangement = Arrangement.spacedBy(Dimens.SectionSpacing)
                    ) {
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


                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                Constantes.TIPO,
                                modifier = Modifier.padding(end = Dimens.LabelEndPadding),
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(Dimens.InlineSpacing)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(
                                        selected = esPelicula == true,
                                        onClick = { esPelicula = true; onEsPelicula(true) },
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = MaterialTheme.colorScheme.primary
                                        )
                                    )
                                    Text(
                                        Constantes.PELICULA,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(
                                        selected = esPelicula == false,
                                        onClick = { esPelicula = false; onEsPelicula(false) },
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = MaterialTheme.colorScheme.primary
                                        )
                                    )
                                    Text(
                                        Constantes.SERIE,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            }
                        }

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

                        FechaPickerField(
                            value = lanzamiento,
                            onValueChange = { lanzamiento = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Column(
                        modifier = Modifier.weight(Dimens.LandscapeColumnWeight),
                        verticalArrangement = Arrangement.spacedBy(Dimens.SectionSpacing)
                    ) {
                        if (esPelicula == false) {
                            OutlinedTextField(
                                value = temporadas,
                                onValueChange = { temporadas = it.filter { ch -> ch.isDigit() } },
                                label = { Text(Constantes.N_SEASONS) },
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                                    focusedLabelColor = MaterialTheme.colorScheme.primary
                                )
                            )
                        }

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

                        DropdownField(
                            label = Constantes.PAIS,
                            value = pais,
                            options = listOf(
                                Constantes.PAIS,
                                Constantes.ESTADOS_UNIDOS,
                                Constantes.CHINA,
                                Constantes.JAPON,
                                Constantes.REINO_UNIDO,
                                Constantes.FRANCIA,
                                Constantes.ESPANIA
                            ),
                            onSelected = { pais = it },
                            modifier = Modifier.fillMaxWidth()
                        )


                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                Constantes.VALORACION,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            RatingStars(
                                rating = valoracion,
                                onRatingChanged = { valoracion = it },
                                modifier = Modifier.padding(top = Dimens.RatingTopPadding)
                            )
                        }
                    }
                }
            }



            Spacer(modifier = Modifier.height(Dimens.SpacerSmall))

            if(DeviceConfiguration.fromWindowSizeClass(windowSizeClass) == DeviceConfiguration.MOBILE_PORTRAIT){
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
                            text = Constantes.BORRAR,
                            color = MaterialTheme.colorScheme.error,
                            onClick = { onBorrar(produccionActual(nombre, director, esPelicula, lanzamiento, temporadas, genero, pais, valoracion)) }
                        )
                        Boton(
                            text = Constantes.ACTUALIZAR,
                            color = MaterialTheme.colorScheme.tertiary,
                            onClick = { onActualizar(produccionActual(nombre, director, esPelicula, lanzamiento, temporadas, genero, pais, valoracion)) }
                        )
                        Boton(
                            text = Constantes.GUARDAR,
                            color = MaterialTheme.colorScheme.primary,
                            onClick = { onGuardar(produccionActual(nombre, director, esPelicula, lanzamiento, temporadas, genero, pais, valoracion)) }
                        )
                    }
                }
            } else {
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
                        text = Constantes.BORRAR,
                        color = MaterialTheme.colorScheme.error,
                        onClick = { onBorrar(produccionActual(nombre, director, esPelicula, lanzamiento, temporadas, genero, pais, valoracion)) }
                    )
                    Boton(
                        text = Constantes.ACTUALIZAR,
                        color = MaterialTheme.colorScheme.tertiary,
                        onClick = { onActualizar(produccionActual(nombre, director, esPelicula, lanzamiento, temporadas, genero, pais, valoracion)) }
                    )
                    Boton(
                        text = Constantes.GUARDAR,
                        color = MaterialTheme.colorScheme.primary,
                        onClick = { onGuardar(produccionActual(nombre, director, esPelicula, lanzamiento, temporadas, genero, pais, valoracion)) }
                    )
                }
            }



            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimens.NavButtonsTopPadding),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = { onNavegarAnterior() },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = SolidColor(MaterialTheme.colorScheme.primary)
                    )
                ) {
                    Text(
                        Constantes.ANTERIOR,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                OutlinedButton(
                    onClick = { onNavegarSiguiente() },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = SolidColor(MaterialTheme.colorScheme.primary)
                    )
                ) {
                    Text(
                        Constantes.SIGUIENTE,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

private fun produccionActual(
    nombre: String,
    director: String,
    esPelicula: Boolean?,
    lanzamiento: String,
    temporadas: String,
    genero: String,
    pais: String,
    valoracion: Int
): Produccion {
    return Produccion(
        esPelicula = esPelicula,
        nombre = nombre,
        director = director,
        lanzamiento = lanzamiento.ifBlank { null },
        numeroTemporadas = temporadas.toIntOrNull(),
        genero = genero,
        pais = pais,
        valoracion = valoracion.toDouble()
    )
}



@Preview(showBackground = true, device = PIXEL_8, showSystemUi = true)
@Composable
fun GreetingPreview() {
    AppTheme{
        ProduccionScreen(uiState = MainState(
            produccion = Produccion(
                esPelicula = true,
                nombre = "",
                director = "",
                lanzamiento = "",
                numeroTemporadas = null,
                genero = "Ficción",
                pais = "Estados Unidos",
                valoracion = 4.0
            ),
            indiceProduccion = 0
        ))
    }
}


@Preview(showBackground = true,device = "spec:width= 891dp, height=411dp", showSystemUi = true)
@Composable
fun UserFormScreenPreviewTablet() {
    AppTheme {
        ProduccionScreen(
            uiState = MainState(
                produccion = Produccion(
                    esPelicula = true,
                    nombre = "",
                    director = "",
                    lanzamiento = "",
                    numeroTemporadas = null,
                    genero = "Ficción",
                    pais = "Estados Unidos",
                    valoracion = 4.0
                ),
                indiceProduccion = 0
            )
        )
    }
}