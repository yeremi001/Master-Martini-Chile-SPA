package com.mastermartini.app.model

enum class TipoContenido(val etiqueta: String) {
    RECETA("Receta"),
    VIDEO("Video técnico"),
    CURSO("Curso")
}

enum class Categoria(val etiqueta: String) {
    CHOCOLATERIA("Chocolatería"),
    PASTELERIA_VITRINA("Pastelería de vitrina"),
    HELADERIA("Heladería"),
    BOLLERIA("Bollería"),
    MASAS_HOJA("Masas de hoja"),
    GALLETAS("Galletas"),
    EMPANADAS("Empanadas"),
    HORNEO("Productos de horneo")
}

enum class TipoNegocio(val etiqueta: String) {
    PANADERIA("Panadería"),
    PASTELERIA("Pastelería"),
    CHOCOLATERIA("Chocolatería"),
    HELADERIA("Heladería"),
    CASINO("Casino"),
    RESTAURANTE("Restaurante"),
    OTRO("Otro")
}

enum class TamanoNegocio(val etiqueta: String) {
    MICRO("Micro (1 a 4 personas)"),
    PEQUENO("Pequeño (5 a 19 personas)"),
    MEDIANO("Mediano (20 o más personas)")
}

/** Contenido de valor del catálogo (datos ficticios para el MVP). */
data class Contenido(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val tipo: TipoContenido,
    val categoria: Categoria,
    val duracion: String,
    val nivel: String,
    val aprenderas: List<String>,
    /** Recursos drawable para el carrusel (vacío = se muestra el ícono del tipo). */
    val imagenes: List<Int> = emptyList()
)

/** Perfil del usuario (datos ficticios) que se persiste localmente. */
data class Perfil(
    val nombre: String,
    val correo: String,
    val telefono: String,
    val rutEmpresa: String,
    val tipoNegocio: TipoNegocio,
    val tamano: TamanoNegocio,
    val intereses: Set<Categoria> = emptySet(),
    val fotoUri: String? = null
)
