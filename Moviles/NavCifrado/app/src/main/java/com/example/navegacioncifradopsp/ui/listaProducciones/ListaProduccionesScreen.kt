package com.example.navegacioncifradopsp.ui.listaProducciones

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
fun ListScreenViewModel(
    viewModel: LisaProduccionesViewModel = hiltViewModel(),
    onNavigateDetalle: (Produccion) -> Unit = {},
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
        onItemClick = onNavigateDetalle,
        onAdd = {}
    )

}

@Composable
fun ListScreen(
    uiState: ListaProduccionesState,
    modifier: Modifier = Modifier,
    title: String = "Listado",
    onItemClick: (Produccion) -> Unit,
    onAdd: () -> Unit,
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
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

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
                    items(uiState.producciones) { item ->
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


