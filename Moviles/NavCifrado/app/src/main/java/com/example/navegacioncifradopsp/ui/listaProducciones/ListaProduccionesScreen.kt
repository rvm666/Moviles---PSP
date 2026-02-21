package com.example.navegacioncifradopsp.ui.listaProducciones

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices.PIXEL_8
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.navegacioncifradopsp.common.UiEvent
import com.example.navegacioncifradopsp.domain.model.Produccion
import com.example.navegacioncifradopsp.ui.theme.NavegacionCifradoPSPTheme
import com.example.navegacioncifradopsp.ui.util.Dimens

@Composable
fun ListProduccionesScreenViewModel(
    viewModel: LisaProduccionesViewModel = hiltViewModel(),
    onNavigateDetalle: (Int) -> Unit = {},
    onAdd: () -> Unit = {}
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


    ListScreen(uiState = uiState,
        onItemClick = {produccion -> onNavigateDetalle(produccion.id)},
        onAdd = onAdd,
        onDelete = {produccion -> viewModel.delete(produccion)}
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    uiState: ListaProduccionesState,
    modifier: Modifier = Modifier,
    onItemClick: (Produccion) -> Unit,
    onAdd: () -> Unit,
    onDelete: (Produccion) -> Unit = {},
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Añadir")
            }
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(
                    horizontal = Dimens.ScreenPaddingHorizontalCompact,
                    vertical = Dimens.ScreenPaddingVerticalCompact
                )
        ) {

            Spacer(Modifier.height(Dimens.SectionSpacingCompact))

            if (uiState.producciones.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay elementos",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(Dimens.SectionSpacingCompact)
                ) {
                    items(uiState.producciones, key = { it.id }) { item ->
                        SwipeToDismissBox(
                            state = rememberSwipeToDismissBoxState(
                                confirmValueChange = { value ->
                                    if (value == SwipeToDismissBoxValue.EndToStart) {
                                        onDelete(item)
                                        true
                                    } else false
                                }
                            ),
                            backgroundContent = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Red)
                                        .padding(16.dp),
                                    contentAlignment = Alignment.CenterEnd
                                ) {
                                    Icon(Icons.Default.Delete, contentDescription = null)
                                }
                            }
                        ) {
                            ElevatedCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onItemClick(item) }
                            ) {
                                Column(
                                    modifier = Modifier.padding(Dimens.InlineSpacing),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = item.nombre,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = item.director,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, device = PIXEL_8, showSystemUi = true)
@Composable
fun GreetingPreview() {
    NavegacionCifradoPSPTheme {
        ListScreen(
            uiState = ListaProduccionesState(
                producciones = listOf(
                    Produccion(
                    nombre = "Pulp fiction",
                    director = "Quentin Tarantino"
                ), Produccion(
                    nombre = "Pulp fiction",
                    director = "Quentin Tarantino"
                ), Produccion(
                    nombre = "Pulp fiction",
                    director = "Quentin Tarantino"
                ))
            ),
            onItemClick = {},
            onAdd = {}
        )
    }
}


