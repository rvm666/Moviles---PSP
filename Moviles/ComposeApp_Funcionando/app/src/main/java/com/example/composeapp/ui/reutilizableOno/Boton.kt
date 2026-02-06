package com.example.composeapp.ui.reutilizableOno

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composeapp.ui.util.Dimens

@Composable
public fun Boton(
    text: String,
    color: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        contentPadding = PaddingValues(horizontal = Dimens.ButtonPaddingHorizontal, vertical = Dimens.ButtonPaddingVertical)
    ) {
        Text(text)
    }
}