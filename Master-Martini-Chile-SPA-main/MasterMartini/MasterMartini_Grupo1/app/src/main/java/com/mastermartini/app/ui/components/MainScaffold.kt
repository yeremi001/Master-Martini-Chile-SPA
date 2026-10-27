@file:OptIn(ExperimentalMaterial3Api::class)

package com.mastermartini.app.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mastermartini.app.navigation.Screen

private data class ItemNavegacion(val pantalla: Screen, val etiqueta: String, val icono: ImageVector)

private val itemsNavegacion = listOf(
    ItemNavegacion(Screen.Home, "Inicio", Icons.Filled.Home),
    ItemNavegacion(Screen.Favoritos, "Favoritos", Icons.Filled.Favorite),
    ItemNavegacion(Screen.Perfil, "Mi perfil", Icons.Filled.Person)
)

/**
 * Estructura común de las pantallas principales: TopAppBar + navegación inferior
 * (pantallas compactas) o NavigationRail lateral (pantallas medianas y expandidas).
 */
@Composable
fun MainScaffold(
    navController: NavController,
    titulo: String,
    anchoVentana: WindowWidthSizeClass,
    snackbarHostState: SnackbarHostState? = null,
    content: @Composable () -> Unit
) {
    val entrada by navController.currentBackStackEntryAsState()
    val rutaActual = entrada?.destination?.route
    val compacto = anchoVentana == WindowWidthSizeClass.Compact

    val navegar: (Screen) -> Unit = { destino ->
        if (rutaActual != destino.route) {
            navController.navigate(destino.route) {
                popUpTo(Screen.Home.route) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(titulo) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            if (compacto) {
                NavigationBar {
                    itemsNavegacion.forEach { item ->
                        NavigationBarItem(
                            selected = rutaActual == item.pantalla.route,
                            onClick = { navegar(item.pantalla) },
                            icon = { Icon(item.icono, contentDescription = null) },
                            label = { Text(item.etiqueta) }
                        )
                    }
                }
            }
        },
        snackbarHost = { snackbarHostState?.let { SnackbarHost(it) } }
    ) { padding ->
        Row(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (!compacto) {
                NavigationRail {
                    itemsNavegacion.forEach { item ->
                        NavigationRailItem(
                            selected = rutaActual == item.pantalla.route,
                            onClick = { navegar(item.pantalla) },
                            icon = { Icon(item.icono, contentDescription = null) },
                            label = { Text(item.etiqueta) }
                        )
                    }
                }
            }
            Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                content()
            }
        }
    }
}
