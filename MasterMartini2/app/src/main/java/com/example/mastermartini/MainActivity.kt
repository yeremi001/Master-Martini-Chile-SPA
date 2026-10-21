package com.example.mastermartini

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mastermartini.navigation.AppNavigation
import com.example.mastermartini.ui.theme.MasterMartiniTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MasterMartiniTheme {
                // Llama al sistema de navegación principal
                AppNavigation()
            }
        }
    }
}