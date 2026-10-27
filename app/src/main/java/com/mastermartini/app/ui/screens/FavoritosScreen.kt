@file:OptIn(ExperimentalMaterial3Api::class)

package com.mastermartini.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mastermartini.app.data.ContenidoRepository
import com.mastermartini.app.navigation.Screen
import com.mastermartini.app.ui.components.ContenidoCard
import com.mastermartini.app.ui.components.MainScaffold
import com.mastermartini.app.ui.utils.columnas
import com.mastermartini.app.viewmodel.AppViewModel

@Composable
fun FavoritosScreen(
    appViewModel: AppViewModel,
    anchoVentana: WindowWidthSizeClass,
    navController: NavController
) {
    val favoritos by appViewModel.favoritos.collectAsState()
    val lista = remember(favoritos) { ContenidoRepository.todos.filter { it.id in favoritos } }

    MainScaffold(navController, "Mis favoritos", anchoVentana) {
        if (lista.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(32.dp), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Filled.FavoriteBorder, contentDescription = null, modifier = Modifier.size(64.dp))
                    Text("Aún no tienes favoritos", style = MaterialTheme.typography.titleMedium)
                    Text(
                        "Toca el corazón en un contenido para guardarlo aquí.",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(anchoVentana.columnas()),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(lista, key = { it.id }) { contenido ->
                    ContenidoCard(
                        contenido = contenido,
                        esFavorito = true,
                        recomendado = false,
                        onClick = { navController.navigate(Screen.Detalle.crearRuta(contenido.id)) },
                        onFavorito = { appViewModel.toggleFavorito(contenido.id) }
                    )
                }
            }
        }
    }
}
