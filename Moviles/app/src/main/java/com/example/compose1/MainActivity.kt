package com.example.compose1

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.anadirusuarios1.ui.MainState
import com.example.compose.AppTheme
import com.example.compose1.ui.theme.Dimens
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                MiPantalla()
            }
        }
    }
}

@Composable
fun MiPantalla(modifier: Modifier = Modifier,
               uiState: MainState){
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(
                text = "Añadir Pelicula/Serie",
                fontSize = Dimens.textSizeTitle,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            // Fila: Campo Nombre + CheckBox TV
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.spacingMedium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = uiState.produccion.nombre,
                    onValueChange = { onChangeUsusario(uiState.produccion.copy(nombre = it)) },
                    label = { Text("Nombre") },
                    leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null) },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = Dimens.paddingSmall)
                ) {
                    Checkbox(
                        checked = uiState.usuarioActual.tieneTV,
                        onCheckedChange = { onChangeUsusario(uiState.usuarioActual.copy(tieneTV = it)) }
                    )
                    Text("¿TV?", fontSize = Dimens.textSizeMedium)
                }
            }


            // Fila: Apellidos + Teléfono
            ApellidosTelefono(
                apellidos = uiState.usuarioActual.apellidos,
                telefono = uiState.usuarioActual.telefono,
                onApellidosChange = { onChangeUsusario(Usuario(apellidos = it)) },
                onTelefonoChange = { onChangeUsusario(Usuario(telefono = it)) },
            )


            // Fila: Email + Fecha Nacimiento
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.spacingSmall)
            ) {
                OutlinedTextField(
                    value = uiState.usuarioActual.email,
                    onValueChange = { onChangeUsusario(uiState.usuarioActual.copy(email = it)) },
                    label = { Text("Email") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = uiState.usuarioActual.fechaNacimiento,
                    onValueChange = { },
                    label = { Text("Fecha Nac.") },
                    leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                    readOnly = true,
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            val calendar = Calendar.getInstance()
                            DatePickerDialog(
                                context,
                                { _, year, month, day ->
                                    onChangeUsusario(uiState.usuarioActual.copy(fechaNacimiento = "$day/${month + 1}/$year"))
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            ).show()
                        }
                )
            }


            // Género
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Género:",
                    fontSize = Dimens.textSizeMedium,
                    color = Color.Black,
                    modifier = Modifier.padding(end = Dimens.paddingSmall)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = uiState.usuarioActual.genero == "M",
                            onClick = { onChangeUsusario(uiState.usuarioActual.copy(genero = "M")) }
                        )
                        Text("M", modifier = Modifier.padding(end = Dimens.paddingSmall))
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = uiState.usuarioActual.genero == "F",
                            onClick = { onChangeUsusario(uiState.usuarioActual.copy(genero = "F")) }
                        )
                        Text("F", modifier = Modifier.padding(end = Dimens.paddingSmall))
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = uiState.usuarioActual.genero == "Otro",
                            onClick = { onChangeUsusario(uiState.usuarioActual.copy(genero = "Otro")) }
                        )
                        Text("Otro")
                    }
                }
            }


            // Comentarios
            OutlinedTextField(
                value = uiState.usuarioActual.comentarios,
                onValueChange = { onChangeUsusario(uiState.usuarioActual.copy(comentarios = it)) },
                label = { Text("Comentarios") },
                leadingIcon = { Icon(Icons.Default.Info, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.textAreaHeight),
                maxLines = 4
            )
            botonera(indiceActual = uiState.indiceActual,
                size = uiState.usuarios.size,
                isEmpty = uiState.usuarios.isEmpty(),)
        }

    }
}

@Composable
fun botonera(modifier: Modifier = Modifier,
             indiceActual : Int,
             size : Int,
             isEmpty : Boolean,
             onLimpiarFormulario: () -> Unit = {},
             onNavegarSiguiente: () -> Unit = {},
             onNavegarAnterior: () -> Unit = {},
             onGuardar: () -> Unit = {},
             onBorrar: () -> Unit = {},
             onActualizar: () -> Unit = {},
){


    // Botones de navegación
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { onNavegarAnterior() },
            enabled = indiceActual > 0,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0099CC)),
            modifier = Modifier
                .weight(1f)
                .height(Dimens.buttonHeightSmall)
                .padding(end = Dimens.paddingExtraSmall)
        ) {
            Text("← Ant.", fontSize = Dimens.textSizeSmall)
        }

        Text(
            text = if (isEmpty) "0/0" else "${indiceActual + 1}/${size}",
            fontSize = Dimens.textSizeMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = Dimens.paddingSmall)
                .widthIn(min = 40.dp)
        )

        Button(
            onClick = { onNavegarSiguiente() },
            enabled = indiceActual < size - 1,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0099CC)),
            modifier = Modifier
                .weight(1f)
                .height(Dimens.buttonHeightSmall)
                .padding(start = Dimens.paddingExtraSmall)
        ) {
            Text("Sig. →", fontSize = Dimens.textSizeSmall)
        }
    }
    BotonesActtion(
        enableBorrar = !isEmpty,
        enableActualizar = !isEmpty,
        onGuardar = onGuardar,
        onLimpiarFormulario = onLimpiarFormulario,
        onBorrar = onBorrar,
        onActualizar = onActualizar,
    )



}


@Composable
private fun ApellidosTelefono(
    apellidos: String,
    telefono: String,
    onApellidosChange: (String) -> Unit,
    onTelefonoChange: (String) -> Unit,

    ) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Dimens.spacingSmall)
    ) {
        OutlinedTextField(
            value = apellidos,
            onValueChange = onApellidosChange,
            label = { Text("Apellidos") },
            leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null) },
            singleLine = true,
            modifier = Modifier.weight(1f)
        )

        OutlinedTextField(
            value = telefono,
            onValueChange = onTelefonoChange,
            label = { Text("Teléfono") },
            leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
            singleLine = true,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = name,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme{
        MiPantalla()
    }
}