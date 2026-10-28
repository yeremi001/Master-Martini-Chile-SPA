# Master Martini Academia - MVP movil (DSY1105 · Evaluacion Parcial 2)

Prototipo academico de una plataforma movil de recetas, videos tecnicos y cursos para clientes y prospectos de Master Martini Chile SPA (comercializadora de insumos para alimentos). Desarrollado en Kotlin + Jetpack Compose + Material 3 con arquitectura MVVM.

> Todos los datos son ficticios o sinteticos. No se usan datos personales reales ni material interno no autorizado.

---

## Integrantes

| Nombre y apellido | Rol en el equipo |
|---|---|
| Yeremi Vásquez | Lead UI/UX Developer & Accessibility Specialist |
| Benjamín Salas | Backend & Data Architecture Lead |

**Equipo N.º:** (completar) · **Asignatura:** DSY1105 Desarrollo de Aplicaciones Moviles · **Docente:** (completar)

---

## Contexto del caso

Master Martini difunde hoy sus videos por WhatsApp y YouTube, sin constancia ni segmentacion. La app busca una relacion mas fluida con clientes y prospectos (incluyendo emprendedores mayores de 50 anos y con dificultades visuales), reforzar la marca y mantener informacion de intereses segmentada, con posibilidad de escalar a la plataforma web.

---

## Funcionalidades implementadas

* **Splash:** Animacion y carga inicial con CircularProgressIndicator.
* **Login y Registro:** Validacion por campo, iconos, mensajes de error y estado de carga.
* **Encuesta de intereses:** Segmentacion editable desde el perfil.
* **Catalogo:** Contenidos con busqueda, filtros (receta / video / curso) y recomendaciones segun intereses.
* **Detalle:** Contenido, favoritos y opcion de compartir mediante Intent.
* **Favoritos:** Persistencia local de contenidos seleccionados.
* **Perfil:** Foto desde galeria o camara, datos del usuario, intereses, opcion de Texto grande (accesibilidad) y cierre de sesion.
* **Diseno adaptable:** Implementacion con Window Size Classes:
  * Barra inferior + 1 columna (compacta).
  * NavigationRail + 2 columnas (mediana).
  * 3 columnas (expandida).
* **Animaciones:** Transiciones entre pantallas, AnimatedVisibility, AnimatedContent, animateColorAsState, spring en favoritos, Crossfade en la foto y animateContentSize.

---

## Cumplimiento de la rubrica

| Indicador | Donde se evidencia |
|---|---|
| **IE 2.1.1** Interfaz coherente y navegacion | `ui/screens`, `ui/components/MainScaffold.kt`, `navigation/` |
| **IE 2.1.2** Formularios con validacion visual | `LoginScreen`, `RegistroScreen`, `EncuestaScreen`, `ui/components/CampoTexto.kt` |
| **IE 2.2.1** Validacion desacoplada de la UI | `util/Validadores.kt`, `viewmodel/*ViewModel.kt` (+ tests unitarios) |
| **IE 2.2.2** Animaciones funcionales | Revisar seccion de animaciones implementadas |
| **IE 2.3.1** Modularidad + persistencia local | Carpetas MVVM, `data/UserRepository.kt` (DataStore Preferences) |
| **IE 2.3.2** GitHub y Trello | Archivo `docs/ENTREGA.md` |
| **IE 2.4.1** Dos recursos nativos | Camara (permiso + FileProvider)
