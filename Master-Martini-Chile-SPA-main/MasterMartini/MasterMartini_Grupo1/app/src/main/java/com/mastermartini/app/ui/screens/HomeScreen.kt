@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package com.mastermartini.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mastermartini.app.model.TipoContenido
import com.mastermartini.app.navigation.Screen
import com.mastermartini.app.ui.components.ContenidoCard
import com.mastermartini.app.ui.components.MainScaffold
import com.mastermartini.app.ui.utils.columnas
import com.mastermartini.app.viewmodel.AppViewModel
import com.mastermartini.app.viewmodel.ContenidoViewModel

/** Catálogo adaptable: 1 columna (compacta), 2 (mediana) o 3 (expandida). */
@Composable
fun HomeScreen(
    appViewModel: AppViewModel,
    contenidoViewModel: ContenidoViewModel,
    anchoVentana: WindowWidthSizeClass,
    navController: NavController
) {
    val perfil by appViewModel.perfil.collectAsState()
    val favoritos by appViewModel.favoritos.collectAsState()
    val resultados by contenidoViewModel.resultados.collectAsState()
    val busqueda by contenidoViewModel.busqueda.collectAsState()
    val tipo by contenidoViewModel.tipo.collectAsState()

    val intereses = perfil?.intereses.orEmpty()
    val ordenados = remember(resultados, intereses) {
        resultados.sortedByDescending { it.categoria in intereses }
    }
    val primerNombre = perfil?.nombre?.trim()?.substringBefore(' ')

    MainScaffold(navController, "Master Martini", anchoVentana) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(anchoVentana.columnas()),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        if (primerNombre.isNullOrBlank()) "Hola" else "Hola, $primerNombre",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Recetas, videos técnicos y cursos para tu negocio.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    AnimatedVisibility(visible = perfil != null && intereses.isEmpty()) {
                        Card(
                            onClick = { navController.navigate(Screen.Encuesta.route) },
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Filled.Star, contentDescription = null)
                                Spacer(Modifier.width(12.dp))
                                Column {
                                    Text("Cuéntanos qué te interesa", fontWeight = FontWeight.Bold)
                                    Text("Personaliza tus recomendaciones en un minuto.")
                                }
                            }
                        }
                    }
                }
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                OutlinedTextField(
                    value = busqueda,
                    onValueChange = contenidoViewModel::onBusquedaChange,
                    placeholder = { Text("Buscar recetas, videos o cursos") },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                    trailingIcon = if (busqueda.isNotEmpty()) {
                        {
                            IconButton(onClick = { contenidoViewModel.onBusquedaChange("") }) {
                                Icon(Icons.Filled.Clear, contentDescription = "Borrar búsqueda")
                            }
                        }
                    } else null,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        ChipFiltro("Todos", tipo == null) { contenidoViewModel.onTipoChange(null) }
                    }
                    items(TipoContenido.entries) { t ->
                        ChipFiltro(t.etiqueta, tipo == t) { contenidoViewModel.onTipoChange(t) }
                    }
                }
            }

            if (ordenados.isEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Filled.SearchOff, contentDescription = null, modifier = Modifier.size(56.dp))
                        Text("No encontramos resultados", style = MaterialTheme.typography.titleMedium)
                        Text("Prueba con otra palabra o cambia el filtro.")
                    }
                }
            } else {
                items(ordenados, key = { it.id }) { contenido ->
                    ContenidoCard(
                        contenido = contenido,
                        esFavorito = contenido.id in favoritos,
                        recomendado = contenido.categoria in intereses,
                        onClick = { navController.navigate(Screen.Detalle.crearRuta(contenido.id)) },
                        onFavorito = { appViewModel.toggleFavorito(contenido.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ChipFiltro(texto: String, seleccionado: Boolean, onClick: () -> Unit) {
    FilterChip(
        selected = seleccionado,
        onClick = onClick,
        label = { Text(texto, style = MaterialTheme.typography.bodyLarge) },
        leadingIcon = if (seleccionado) {
            { Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(18.dp)) }
        } else null
    )
}
