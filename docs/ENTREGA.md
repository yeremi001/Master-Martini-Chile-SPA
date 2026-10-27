# Guía de entrega – Evaluación Parcial 2 (DSY1105)

Esto NO se puede generar automáticamente: debe hacerlo la pareja con actividad real (el IE 2.3.2 vale 20 %).

## Checklist previo a la entrega (semana 11)
- [ ] Repositorio **GitHub público** con acceso al docente y a ambos integrantes.
- [ ] `README.md` con nombres, funcionalidades y pasos para ejecutar (completar tabla de integrantes).
- [ ] Commits de **ambos** integrantes, con mensajes técnicos (no "cambios" ni "listo").
- [ ] Tablero **Trello** compartido con listas Por hacer / En curso / Finalizado y tareas asignadas a cada uno.
- [ ] Código subido al **AVA grupal** (un solo integrante, mismos archivos que el repo).
- [ ] **AVA individual** activado por cada uno (nombre, apellido, N.º de equipo y nombre de la app).
- [ ] **No tocar el repositorio después de la fecha de entrega** (cualquier cambio = nota 1.0).
- [ ] Probar en un teléfono físico: galería, cámara, cancelar acción, cerrar y reabrir la app (persistencia).

## Flujo de ramas sugerido
`main` ← `develop` ← ramas `feature/*`

| Rama | Contenido | Mensaje de commit sugerido |
|---|---|---|
| `main` | Base del proyecto | `chore: inicio de proyecto y estructura base MVVM` |
| `feature/tema-navegacion` | Tema, rutas, MainScaffold | `feat: navegación con NavHost, rutas tipadas y barra inferior` |
| `feature/formularios` | Login, registro, validadores | `feat: formularios con validación por campo y mensajes visuales` |
| `feature/validaciones-test` | Tests de validadores | `test: pruebas unitarias de validación de RUT, correo y clave` |
| `feature/persistencia` | DataStore, sesión, favoritos | `feat: persistencia local con DataStore de perfil, sesión y favoritos` |
| `feature/catalogo-adaptable` | Home, detalle, favoritos | `feat: catálogo adaptable con Window Size Classes` |
| `feature/imagen-inteligente` | Cámara y galería | `feat: foto de perfil desde cámara y galería con FileProvider` |
| `feature/animaciones` | Animaciones y estados | `feat: animaciones de transición, favoritos y estados de carga` |
| `feature/accesibilidad` | Texto grande | `feat: modo texto grande para accesibilidad` |

Reparto sugerido: Estudiante 1 → formularios, validadores, persistencia. Estudiante 2 → navegación, catálogo,
cámara/galería. Ambos → animaciones, README y pruebas. Hagan *pull requests* y revísense mutuamente.

## Tablero Trello (tarjetas mínimas)
Por hacer / En curso / Finalizado, con tarjetas: Estructura MVVM · Tema y navegación · Login y registro ·
Validadores + tests · Persistencia DataStore · Catálogo adaptable · Detalle y favoritos · Perfil con cámara y galería ·
Animaciones · Accesibilidad · README · Entrega AVA. Cada tarjeta con responsable, checklist y captura de evidencia.

## Guion breve para la defensa
1. Problema del caso y usuarios (mayores de 50, baja alfabetización digital).
2. Flujo: registro → catálogo → favoritos → perfil con foto.
3. Arquitectura: dónde está la validación (`Validadores`) y cómo la UI solo observa `StateFlow`.
4. Persistencia: cerrar y reabrir la app y mostrar que se conserva sesión, favoritos, intereses y foto.
5. Recursos nativos: cámara (permiso + FileProvider) y galería; cancelar sin crashear.
6. Adaptabilidad: mostrar emulador compacto vs tablet.
