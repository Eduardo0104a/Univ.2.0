# Changelog - Base de Datos Ruwana

## [15 Oct 2025] - Sistema de Cupos Opcional

### 🎯 Cambio Principal
Modificación del sistema de cupos para que sea **opcional** en los eventos.

### ✅ Razón del Cambio
No todos los eventos de voluntariado requieren límite de participantes. Por ejemplo:
- **Con límite:** Talleres, eventos con transporte, actividades con materiales limitados
- **Sin límite:** Campañas de limpieza, recolección de donaciones, marchas, eventos masivos

### 📝 Cambios Realizados

#### 1. Tabla `eventos`
```sql
-- ANTES
cupos_maximos INT DEFAULT 0,
cupos_disponibles INT DEFAULT 0,

-- DESPUÉS
cupos_maximos INT DEFAULT NULL,      -- NULL o 0 = sin límite
cupos_disponibles INT DEFAULT NULL,  -- NULL si no hay límite
```

#### 2. Procedimiento `sp_inscribir_voluntario`
Actualizado para:
- Verificar si el evento tiene límite de cupos
- Si `cupos_maximos` IS NULL o = 0 → inscripción sin verificar cupos
- Si `cupos_maximos` > 0 → inscripción solo si hay cupos disponibles

```sql
-- Lógica agregada
IF v_cupos_maximos IS NULL OR v_cupos_maximos = 0 THEN
    -- Evento sin límite: inscribir directo
ELSE
    -- Evento con límite: verificar cupos
END IF;
```

#### 3. Procedimiento `sp_cancelar_inscripcion`
Actualizado para:
- Solo liberar cupo si el evento tiene límite
- Si el evento no tiene límite, no actualizar cupos

```sql
-- Liberar cupo solo si hay límite
IF v_cupos_maximos IS NOT NULL AND v_cupos_maximos > 0 THEN
    UPDATE eventos SET cupos_disponibles = cupos_disponibles + 1 ...
END IF;
```

#### 4. Vista `vista_eventos_completos`
Agregados campos calculados:
- `inscritos_count`: Cuenta inscripciones activas (funciona para ambos tipos)
- `sin_limite_cupos`: Boolean que indica si el evento no tiene límite

```sql
CASE 
    WHEN e.cupos_maximos IS NULL OR e.cupos_maximos = 0 
    THEN (SELECT COUNT(*) FROM inscripciones ...)
    ELSE (e.cupos_maximos - e.cupos_disponibles)
END as inscritos_count
```

#### 5. Datos de Prueba
Actualizados con ejemplos de ambos tipos:
- **Evento 1 (Limpieza de Playas):** SIN límite
- **Evento 2 (Apoyo Educativo):** CON límite (30 personas)
- **Evento 3 (Donación de Alimentos):** SIN límite
- **Evento 4 (Taller de Reciclaje):** CON límite (25 personas)

### 🔍 Cómo Identificar Eventos Sin Límite

**Opción 1: Consulta directa**
```sql
SELECT nombre, cupos_maximos,
    CASE 
        WHEN cupos_maximos IS NULL OR cupos_maximos = 0 
        THEN 'Sin límite' 
        ELSE 'Con límite' 
    END as tipo_evento
FROM eventos;
```

**Opción 2: Usar vista**
```sql
SELECT nombre, sin_limite_cupos, inscritos_count
FROM vista_eventos_completos;
```

### 💡 Recomendaciones de Uso

#### Para Organizaciones al Crear Eventos:

**Evento con límite:**
```sql
INSERT INTO eventos (nombre, cupos_maximos, cupos_disponibles, ...)
VALUES ('Taller de Primeros Auxilios', 30, 30, ...);
```

**Evento sin límite:**
```sql
INSERT INTO eventos (nombre, cupos_maximos, cupos_disponibles, ...)
VALUES ('Campaña de Limpieza', NULL, NULL, ...);
-- O también: VALUES ('Campaña de Limpieza', 0, 0, ...);
```

#### Para el Frontend:

Mostrar en la interfaz:
- Si `cupos_maximos IS NULL` o = 0 → "¡Inscripciones abiertas!" o "Sin límite de cupos"
- Si `cupos_maximos > 0` → "Cupos disponibles: X de Y"

### ⚠️ Consideraciones Importantes

1. **Migración de datos existentes:**
   - Eventos antiguos con `cupos_maximos = 0` ahora se consideran "sin límite"
   - Verificar datos antes de migrar a producción

2. **Validación en la aplicación:**
   - Si se establece `cupos_maximos > 0`, asegurar que `cupos_disponibles` también se establezca
   - No permitir `cupos_disponibles > cupos_maximos`

3. **Reportes:**
   - Usar `inscritos_count` de la vista para contar inscritos
   - Funciona correctamente para eventos con y sin límite

### 🧪 Tests Recomendados

Verificar:
- ✅ Inscripción a evento sin límite (debe permitir N inscripciones)
- ✅ Inscripción a evento con límite (debe respetar cupos)
- ✅ Cancelación en evento sin límite (no debe afectar cupos)
- ✅ Cancelación en evento con límite (debe liberar cupo)
- ✅ Vista muestra correctamente inscritos en ambos tipos

### 🔄 Compatibilidad

- ✅ Backwards compatible (eventos existentes con cupos siguen funcionando)
- ✅ No rompe funcionalidad existente
- ✅ Procedimientos almacenados manejan ambos casos

---

**Autor:** Cascade AI  
**Fecha:** 15 de octubre de 2025  
**Versión BD:** 1.1.0
