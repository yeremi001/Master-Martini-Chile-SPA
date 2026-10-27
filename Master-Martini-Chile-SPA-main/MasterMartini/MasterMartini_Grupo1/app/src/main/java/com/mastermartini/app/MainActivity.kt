package com.mastermartini.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mastermartini.app.navigation.AppNavigation
import com.mastermartini.app.ui.theme.MasterMartiniTheme
import com.mastermartini.app.ui.utils.obtenerWindowSizeClass
import com.mastermartini.app.viewmodel.AppViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appViewModel: AppViewModel = viewModel()
            val textoGrande by appViewModel.textoGrande.collectAsState()
            val tamanoVentana = obtenerWindowSizeClass()

            MasterMartiniTheme(textoGrande = textoGrande) {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppNavigation(appViewModel, tamanoVentana.widthSizeClass)
                }
            }
        }
    }
}
