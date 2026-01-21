package com.example.composeapp.ui.reutilizableOno

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composeapp.ui.util.Dimens

@Composable
public fun RatingStars(
    rating: Int,
    onRatingChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(Dimens.StarsSpacing)) {
        for (i in 1..5) {
            IconButton(onClick = { onRatingChanged(i) }) {
                if (i <= rating) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = "Star $i",
                        tint = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Icon(
                        Icons.Outlined.StarBorder,
                        contentDescription = "Star $i",
                        tint = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
    }
}