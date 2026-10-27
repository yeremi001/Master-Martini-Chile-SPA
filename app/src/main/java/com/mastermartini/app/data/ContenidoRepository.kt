package com.mastermartini.app.data

import com.mastermartini.app.R
import com.mastermartini.app.model.Categoria
import com.mastermartini.app.model.Contenido
import com.mastermartini.app.model.TipoContenido

/**
 * Origen de datos simulado (contenido ficticio). A futuro se reemplaza por
 * una API/plataforma web de Master Martini sin cambiar los ViewModels.
 */
object ContenidoRepository {

    private fun c(
        id: Int, titulo: String, descripcion: String, tipo: TipoContenido,
        categoria: Categoria, duracion: String, nivel: String, vararg aprenderas: String
    ) = Contenido(id, titulo, descripcion, tipo, categoria, duracion, nivel, aprenderas.toList(),
        imagenes = IMAGENES_RECETAS[id].orEmpty())

    /** Fotos de cada receta (id de contenido -> drawables del carrusel). */
    private val IMAGENES_RECETAS: Map<Int, List<Int>> = mapOf(
        1 to listOf(R.drawable.receta_brownie_1, R.drawable.receta_brownie_2, R.drawable.receta_brownie_3),
        4 to listOf(R.drawable.receta_bizcocho_1, R.drawable.receta_bizcocho_2, R.drawable.receta_bizcocho_3),
        6 to listOf(R.drawable.receta_helado_1, R.drawable.receta_helado_2, R.drawable.receta_helado_3),
        9 to listOf(R.drawable.receta_hojaldre_1, R.drawable.receta_hojaldre_2, R.drawable.receta_hojaldre_3),
        10 to listOf(R.drawable.receta_galletas_1, R.drawable.receta_galletas_2, R.drawable.receta_galletas_3),
        11 to listOf(R.drawable.receta_empanadas_1, R.drawable.receta_empanadas_2, R.drawable.receta_empanadas_3)
    )

    val todos: List<Contenido> = listOf(
        c(1, "Brownie de chocolate fácil",
            "Receta simple para preparar un brownie húmedo, ideal para vender por porción en tu local.",
            TipoContenido.RECETA, Categoria.CHOCOLATERIA, "25 min", "Básico",
            "Medir y mezclar ingredientes", "Punto de horneado", "Corte y presentación por porción"),
        c(2, "Atemperado de chocolate paso a paso",
            "Video técnico que muestra la técnica de atemperado para lograr brillo y buen quiebre.",
            TipoContenido.VIDEO, Categoria.CHOCOLATERIA, "12 min", "Intermedio",
            "Curva de temperatura", "Atemperado en mesa", "Errores frecuentes"),
        c(3, "Curso de chocolatería de especialidad",
            "Curso por módulos para profesionales: bombones, rellenos y moldeado.",
            TipoContenido.CURSO, Categoria.CHOCOLATERIA, "6 clases", "Avanzado",
            "Bombones y rellenos", "Moldeado", "Conservación y costos"),
        c(4, "Torta de vitrina: bizcocho y relleno",
            "Receta base de pastelería de vitrina con bizcocho húmedo y relleno estable.",
            TipoContenido.RECETA, Categoria.PASTELERIA_VITRINA, "45 min", "Intermedio",
            "Bizcocho base", "Relleno estable", "Decoración simple"),
        c(5, "Montaje y conservación de vitrina",
            "Video con recomendaciones para exhibir y conservar productos de pastelería.",
            TipoContenido.VIDEO, Categoria.PASTELERIA_VITRINA, "10 min", "Básico",
            "Orden visual", "Temperaturas de exhibición", "Rotación de productos"),
        c(6, "Base de helado artesanal",
            "Receta de base cremosa para helados, con variantes de sabores.",
            TipoContenido.RECETA, Categoria.HELADERIA, "40 min", "Intermedio",
            "Equilibrio de ingredientes", "Maduración de la base", "Variantes de sabor"),
        c(7, "Curso de heladería para emprendedores",
            "Curso introductorio para iniciar una heladería: producción, vitrina y costos.",
            TipoContenido.CURSO, Categoria.HELADERIA, "4 clases", "Básico",
            "Producción diaria", "Presentación en vitrina", "Cálculo de costos"),
        c(8, "Croissant: laminado y fermentación",
            "Video técnico sobre el laminado de la masa y el control de la fermentación.",
            TipoContenido.VIDEO, Categoria.BOLLERIA, "18 min", "Avanzado",
            "Laminado", "Fermentación controlada", "Horneado"),
        c(9, "Masa de hojaldre rápida",
            "Receta práctica de masa de hoja para preparaciones dulces y saladas.",
            TipoContenido.RECETA, Categoria.MASAS_HOJA, "35 min", "Intermedio",
            "Dobleces", "Reposo de la masa", "Usos de la masa"),
        c(10, "Galletas de mantequilla para vender",
            "Receta de galletas con buen rendimiento para pequeños negocios.",
            TipoContenido.RECETA, Categoria.GALLETAS, "30 min", "Básico",
            "Mezcla y reposo", "Cortes uniformes", "Envasado"),
        c(11, "Empanadas de horno: masa y pino",
            "Receta clásica de empanadas de horno con masa resistente y relleno jugoso.",
            TipoContenido.RECETA, Categoria.EMPANADAS, "60 min", "Intermedio",
            "Masa para armar", "Relleno de pino", "Repulgue y horneado"),
        c(12, "Curso de productos de horneo",
            "Curso para ampliar la oferta de horneo de tu panadería o casino.",
            TipoContenido.CURSO, Categoria.HORNEO, "5 clases", "Intermedio",
            "Masas base", "Control de horno", "Planificación de producción")
    )

    fun porId(id: Int): Contenido? = todos.firstOrNull { it.id == id }

    fun buscar(texto: String, tipo: TipoContenido?): List<Contenido> {
        val q = texto.trim()
        return todos.filter { c ->
            (tipo == null || c.tipo == tipo) &&
                (q.isEmpty() ||
                    c.titulo.contains(q, ignoreCase = true) ||
                    c.categoria.etiqueta.contains(q, ignoreCase = true))
        }
    }
}
