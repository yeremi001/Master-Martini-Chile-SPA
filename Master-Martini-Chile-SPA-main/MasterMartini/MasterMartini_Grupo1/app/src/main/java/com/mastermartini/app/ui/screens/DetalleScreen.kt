@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package com.mastermartini.app.ui.screens

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mastermartini.app.data.ContenidoRepository
import com.mastermartini.app.ui.components.BotonFavorito
import com.mastermartini.app.ui.components.icono
import com.mastermartini.app.viewmodel.AppViewModel

@Composable
fun DetalleScreen(contenidoId: Int, appViewModel: AppViewModel, onVolver: () -> Unit) {
    val contenido = remember(contenidoId) { ContenidoRepository.porId(contenidoId) }
    val favoritos by appViewModel.favoritos.collectAsState()
    val ctx = LocalContext.current
    var abierto by rememberSaveable { mutableStateOf(true) }
    val giro by animateFloatAsState(if (abierto) 180f else 0f, label = "giro_flecha")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    if (contenido != null) {
                        BotonFavorito(
                            esFavorito = contenido.id in favoritos,
                            onClick = { appViewModel.toggleFavorito(contenido.id) }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        if (contenido == null) {
            Box(Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No encontramos este contenido", style = MaterialTheme.typography.titleMedium)
            }
        } else {
            Box(Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                Column(
                    modifier = Modifier
                        .widthIn(max = 640.dp)
                        .verticalScroll(rememberScrollState())
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            contenido.tipo.icono(),
                            contentDescription = contenido.tipo.etiqueta,
                            modifier = Modifier.size(80.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Text(
                        contenido.titulo,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AssistChip(onClick = {}, label = { Text(contenido.tipo.etiqueta) },
                            leadingIcon = { Icon(contenido.tipo.icono(), null, Modifier.size(18.dp)) })
                        AssistChip(onClick = {}, label = { Text(contenido.categoria.etiqueta) },
                            leadingIcon = { Icon(Icons.Filled.Category, null, Modifier.size(18.dp)) })
                        AssistChip(onClick = {}, label = { Text(contenido.duracion) },
                            leadingIcon = { Icon(Icons.Filled.Schedule, null, Modifier.size(18.dp)) })
                        AssistChip(onClick = {}, label = { Text(contenido.nivel) },
                            leadingIcon = { Icon(Icons.Filled.SignalCellularAlt, null, Modifier.size(18.dp)) })
                    }
                    Text(contenido.descripcion, style = MaterialTheme.typography.bodyLarge)

                    Card(modifier = Modifier.fillMaxWidth().animateContentSize()) {
                        Column(Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(onClickLabel = "Mostrar u ocultar") { abierto = !abierto },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "Qué aprenderás",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(1f)
                                )
                                Icon(Icons.Filled.ExpandMore, contentDescription = null, modifier = Modifier.rotate(giro))
                            }
                            AnimatedVisibility(visible = abierto) {
                                Column(
                                    modifier = Modifier.padding(top = 12.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    contenido.aprenderas.forEach { punto ->
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                Icons.Filled.CheckCircle,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.primary
                                            )
                                            Spacer(Modifier.width(8.dp))
                                            Text(punto, style = MaterialTheme.typography.bodyLarge)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Button(
                        onClick = {
                            val envio = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, "Mira este contenido de Master Martini: ${contenido.titulo}")
                            }
                            ctx.startActivity(Intent.createChooser(envio, "Compartir contenido"))
                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp)
                    ) {
                        Icon(Icons.Filled.Share, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Compartir", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }
}
