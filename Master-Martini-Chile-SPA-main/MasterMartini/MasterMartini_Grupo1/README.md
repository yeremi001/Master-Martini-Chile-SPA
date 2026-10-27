# Master Martini Academia – MVP móvil (DSY1105 · Evaluación Parcial 2)

Prototipo académico de una plataforma móvil de **recetas, videos técnicos y cursos** para clientes y prospectos de
**Master Martini Chile SPA** (comercializadora de insumos para alimentos). Desarrollado en **Kotlin + Jetpack Compose
+ Material 3** con arquitectura **MVVM**.

> Todos los datos son **ficticios o sintéticos**. No se usan datos personales reales ni material interno no autorizado.

## Integrantes
| Nombre y apellido | Rol en el equipo |
|---|---|
| _Estudiante 1_ | _(completar)_ |
| _Estudiante 2_ | _(completar)_ |

Equipo N.º: _(completar)_ · Asignatura DSY1105 Desarrollo de Aplicaciones Móviles · Docente: _(completar)_

## Contexto del caso
Master Martini difunde hoy sus videos por WhatsApp y YouTube, sin constancia ni segmentación. La app busca una relación
más fluida con clientes y prospectos (incluyendo emprendedores **mayores de 50 años** y con **dificultades visuales**),
reforzar la marca y mantener información de intereses segmentada, con posibilidad de escalar a la plataforma web.

## Funcionalidades implementadas
- **Splash** con animación y carga inicial (CircularProgressIndicator).
- **Login** y **Registro** con validación por campo, íconos, mensajes de error y estado de carga.
- **Encuesta de intereses** (segmentación) editable desde el perfil.
- **Catálogo** de contenidos con búsqueda, filtros (receta / video / curso) y recomendaciones según intereses.
- **Detalle** del contenido, favoritos y compartir (Intent).
- **Favoritos** persistentes.
- **Perfil** con foto desde **galería** o **cámara**, datos del usuario, intereses y opción **Texto grande**
  (accesibilidad), además de cerrar sesión.
- **Diseño adaptable** con Window Size Classes: barra inferior + 1 columna (compacta), NavigationRail + 2 columnas
  (mediana), 3 columnas (expandida).
- **Animaciones**: transiciones entre pantallas, AnimatedVisibility, AnimatedContent, animateColorAsState,
  spring en favoritos, Crossfade en la foto, animateContentSize.

## Cumplimiento de la rúbrica
| Indicador | Dónde se evidencia |
|---|---|
| IE 2.1.1 Interfaz coherente y navegación | `ui/screens`, `ui/components/MainScaffold.kt`, `navigation/` |
| IE 2.1.2 Formularios con validación visual | `LoginScreen`, `RegistroScreen`, `EncuestaScreen`, `ui/components/CampoTexto.kt` |
| IE 2.2.1 Validación desacoplada de la UI | `util/Validadores.kt`, `viewmodel/*ViewModel.kt` (+ tests unitarios) |
| IE 2.2.2 Animaciones funcionales | ver lista de animaciones arriba |
| IE 2.3.1 Modularidad + persistencia local | carpetas MVVM, `data/UserRepository.kt` (DataStore Preferences) |
| IE 2.3.2 GitHub y Trello | ver `docs/ENTREGA.md` |
| IE 2.4.1 Dos recursos nativos | **Cámara** (permiso + FileProvider) y **Galería**; además compartir por Intent |

## Estructura del proyecto
```
app/src/main/java/com/mastermartini/app
├── model/        Modelos y enums (Contenido, Perfil, Categoria…)
├── data/         ContenidoRepository (datos simulados) y UserRepository (DataStore)
├── viewmodel/    AppViewModel, LoginViewModel, RegistroViewModel, ContenidoViewModel
├── navigation/   Screen (rutas tipadas) y AppNavigation (NavHost)
├── util/         Validadores (lógica de validación) y Seguridad (hash)
└── ui/
    ├── theme/        Tema Material 3 + modo texto grande
    ├── components/   CampoTexto, ImagenInteligente, ContenidoCard, MainScaffold
    ├── screens/      Splash, Login, Registro, Encuesta, Home, Detalle, Favoritos, Perfil
    └── utils/        WindowSizeUtils
```

## Cómo ejecutar
1. Abrir **Android Studio** (Koala o superior, JDK 17) → *Open* → seleccionar esta carpeta.
2. Esperar la sincronización de Gradle (requiere internet la primera vez).
3. Elegir un emulador (API 26+) o un teléfono físico con depuración USB.
4. Ejecutar ▶ **app**.
5. Primer uso: *Crear cuenta* con datos ficticios (ej. RUT `12.345.678-5`, teléfono `+56 9 1234 5678`,
   contraseña con 8+ caracteres, letras y números).
6. Probar la cámara en un **dispositivo físico** (en emulador usa la cámara virtual).

Pruebas unitarias de validación: `./gradlew test` (o clic derecho en `ValidadoresTest`).

## Notas del MVP
- Hay una sola cuenta local por dispositivo (persistida con DataStore); la contraseña se guarda como hash SHA-256.
- Colores y logo son **provisionales**: reemplazar por la identidad corporativa autorizada.
- Los videos técnicos no están incluidos (material pendiente de autorización); el catálogo los muestra como contenido simulado.
