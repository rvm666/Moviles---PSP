package com.example.composeapp.ui.reutilizableOno

import android.app.DatePickerDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.composeapp.common.Constantes
import java.util.Calendar

@Composable
public fun FechaPickerField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }

    OutlinedTextField(
        value = value,
        onValueChange = { },
        label = { Text(Constantes.LANZAMIENTO) },
        readOnly = true,
        modifier = modifier,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            disabledBorderColor = MaterialTheme.colorScheme.outline,
            disabledTextColor = MaterialTheme.colorScheme.onSurface
        ),
        trailingIcon = {
            TextButton(
                onClick = {
                    val year = calendar.get(Calendar.YEAR)
                    val month = calendar.get(Calendar.MONTH)
                    val day = calendar.get(Calendar.DAY_OF_MONTH)

                    DatePickerDialog(
                        context,
                        { _, y, m, d ->
                            val mm = (m + 1).toString().padStart(2, Constantes.CERO)
                            val dd = d.toString().padStart(2, Constantes.CERO)
                            onValueChange("$dd/$mm/$y")
                        },
                        year,
                        month,
                        day
                    ).show()
                }
            ) {
                Text(
                    Constantes.ELEGIR,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    )
}