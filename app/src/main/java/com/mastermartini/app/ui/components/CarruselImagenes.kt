package com.mastermartini.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

/**
 * Carrusel de imágenes deslizable con avance automático e indicadores.
 * El avance automático se pausa mientras el usuario arrastra.
 */
@Composable
fun CarruselImagenes(
    imagenes: List<Int>,
    descripcion: String,
    modifier: Modifier = Modifier,
    alto: Dp = 220.dp,
    autoAvance: Boolean = true
) {
    if (imagenes.isEmpty()) return
    val pagerState = rememberPagerState(pageCount = { imagenes.size })

    if (autoAvance && imagenes.size > 1) {
        LaunchedEffect(pagerState) {
            while (true) {
                delay(3500)
                if (!pagerState.isScrollInProgress) {
                    val siguiente = (pagerState.currentPage + 1) % imagenes.size
                    pagerState.animateScrollToPage(siguiente)
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(alto)
            .clip(MaterialTheme.shapes.extraLarge)
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { pagina ->
            Image(
                painter = painterResource(imagenes[pagina]),
                contentDescription = "$descripcion, foto ${pagina + 1} de ${imagenes.size}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        if (imagenes.size > 1) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                repeat(imagenes.size) { i ->
                    val activo = pagerState.currentPage == i
                    Box(
                        Modifier
                            .size(if (activo) 10.dp else 8.dp)
                            .clip(CircleShape)
                            .background(if (activo) Color.White else Color.White.copy(alpha = 0.55f))
                    )
                }
            }
        }
    }
}
