package com.example.anadirusuarios1.ui.pantallaInfo

import com.example.anadirusuarios1.domain.model.Produccion
import com.example.anadirusuarios1.ui.common.UiEvent

data class InfoState(
    val produccion: Produccion,
    val isEnable: Boolean = true,
    val uiEvent: UiEvent? = null,
)
