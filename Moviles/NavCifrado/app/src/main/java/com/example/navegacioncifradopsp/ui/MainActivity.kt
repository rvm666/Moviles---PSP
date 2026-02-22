package com.example.navegacioncifradopsp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.navegacioncifradopsp.ui.navigation.NavigationWrapper
import com.example.navegacioncifradopsp.ui.theme.NavegacionCifradoPSPTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavegacionCifradoPSPTheme {
                NavigationWrapper()
            }
        }
    }
}
