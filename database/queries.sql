-- ========================================
-- Consultas Útiles - Ruwana
-- ========================================
USE ruwana_db;

-- ========================================
-- CONSULTAS PARA VOLUNTARIOS
-- ========================================

-- Listar todos los eventos públicos disponibles (incluye eventos sin límite)
SELECT 
    e.id,
    e.nombre,
    e.descripcion,
    e.fecha_inicio,
    e.fecha_fin,
    e.lugar,
    e.cupos_maximos,
    e.cupos_disponibles,
    CASE 
        WHEN e.cupos_maximos IS NULL OR e.cupos_maximos = 0 THEN 'Sin límite'
        ELSE CONCAT(e.cupos_disponibles, ' de ', e.cupos_maximos)
    END as cupos_info,
    o.nombre_organizacion,
    CASE 
        WHEN e.cupos_maximos IS NULL OR e.cupos_maximos = 0 
        THEN (SELECT COUNT(*) FROM inscripciones WHERE evento_id = e.id AND estado != 'CANCELADO')
        ELSE (e.cupos_maximos - e.cupos_disponibles)
    END as inscritos
FROM eventos e
INNER JOIN organizaciones o ON e.organizacion_id = o.id
WHERE e.estado = 'APROBADO'
  AND e.fecha_inicio >= CURDATE()
  AND (e.cupos_maximos IS NULL OR e.cupos_maximos = 0 OR e.cupos_disponibles > 0)  -- Incluye sin límite O con cupos
ORDER BY e.fecha_inicio ASC;

-- Ver eventos en los que está inscrito un voluntario específico
SELECT 
    e.id,
    e.nombre,
    e.fecha_inicio,
    e.fecha_fin,
    e.lugar,
    i.estado as estado_inscripcion,
    i.fecha_inscripcion,
    o.nombre_organizacion
FROM inscripciones i
INNER JOIN eventos e ON i.evento_id = e.id
INNER JOIN organizaciones o ON e.organizacion_id = o.id
WHERE i.voluntario_id = 1  -- Cambiar por ID del voluntario
ORDER BY e.fecha_inicio DESC;

-- Historial de participación de un voluntario con certificados
SELECT 
    e.nombre as evento,
    e.fecha_inicio,
    i.estado,
    c.codigo_verificacion,
    c.horas_voluntariado,
    c.fecha_emision
FROM inscripciones i
INNER JOIN eventos e ON i.evento_id = e.id
LEFT JOIN certificaciones c ON i.id = c.inscripcion_id
WHERE i.voluntario_id = 1  -- Cambiar por ID del voluntario
  AND i.estado = 'ASISTIO'
ORDER BY e.fecha_inicio DESC;

-- Total de horas de voluntariado de un voluntario
SELECT 
    v.id,
    u.nombres,
    u.apellido_paterno,
    COUNT(i.id) as total_eventos,
    SUM(CASE WHEN c.horas_voluntariado IS NOT NULL THEN c.horas_voluntariado ELSE 0 END) as total_horas
FROM voluntarios v
INNER JOIN usuarios u ON v.usuario_id = u.id
LEFT JOIN inscripciones i ON v.id = i.voluntario_id AND i.estado = 'ASISTIO'
LEFT JOIN certificaciones c ON i.id = c.inscripcion_id
WHERE v.id = 1  -- Cambiar por ID del voluntario
GROUP BY v.id;

-- ========================================
-- CONSULTAS PARA ORGANIZACIONES
-- ========================================

-- Ver eventos de una organización con estadísticas
SELECT 
    e.id,
    e.nombre,
    e.fecha_inicio,
    e.estado,
    e.cupos_maximos,
    e.cupos_disponibles,
    (e.cupos_maximos - e.cupos_disponibles) as inscritos,
    COUNT(DISTINCT i.id) as total_inscripciones,
    COUNT(DISTINCT CASE WHEN i.estado = 'ASISTIO' THEN i.id END) as asistencias_confirmadas
FROM eventos e
LEFT JOIN inscripciones i ON e.id = i.evento_id
WHERE e.organizacion_id = 1  -- Cambiar por ID de organización
GROUP BY e.id
ORDER BY e.fecha_inicio DESC;

-- Lista de voluntarios inscritos en un evento específico
SELECT 
    u.nombres,
    u.apellido_paterno,
    u.email,
    v.numero_documento,
    i.fecha_inscripcion,
    i.estado
FROM inscripciones i
INNER JOIN voluntarios v ON i.voluntario_id = v.id
INNER JOIN usuarios u ON v.usuario_id = u.id
WHERE i.evento_id = 1  -- Cambiar por ID del evento
ORDER BY i.fecha_inscripcion DESC;

-- Donaciones recibidas por evento
SELECT 
    e.nombre as evento,
    COUNT(d.id) as total_donaciones,
    SUM(d.monto) as monto_total,
    d.moneda
FROM eventos e
LEFT JOIN donaciones d ON e.id = d.evento_id AND d.estado = 'CONFIRMADA'
WHERE e.organizacion_id = 1  -- Cambiar por ID de organización
GROUP BY e.id, d.moneda
ORDER BY e.fecha_inicio DESC;

-- Eventos pendientes de aprobación de una organización
SELECT 
    e.id,
    e.nombre,
    e.fecha_inicio,
    e.estado,
    e.fecha_creacion,
    DATEDIFF(CURDATE(), DATE(e.fecha_creacion)) as dias_pendiente
FROM eventos e
WHERE e.organizacion_id = 1  -- Cambiar por ID de organización
  AND e.estado = 'PENDIENTE_APROBACION'
ORDER BY e.fecha_creacion DESC;

-- ========================================
-- CONSULTAS PARA ADMINISTRADORES
-- ========================================

-- Dashboard: Resumen general del sistema
SELECT 
    (SELECT COUNT(*) FROM usuarios WHERE rol = 'VOLUNTARIO' AND estado_activo = TRUE) as total_voluntarios,
    (SELECT COUNT(*) FROM organizaciones WHERE estado_aprobacion = 'APROBADO') as organizaciones_aprobadas,
    (SELECT COUNT(*) FROM organizaciones WHERE estado_aprobacion = 'PENDIENTE') as organizaciones_pendientes,
    (SELECT COUNT(*) FROM eventos WHERE estado = 'APROBADO') as eventos_aprobados,
    (SELECT COUNT(*) FROM eventos WHERE estado = 'PENDIENTE_APROBACION') as eventos_pendientes,
    (SELECT COUNT(*) FROM inscripciones) as total_inscripciones,
    (SELECT SUM(monto) FROM donaciones WHERE estado = 'CONFIRMADA') as total_donaciones;

-- Organizaciones pendientes de aprobación
SELECT 
    o.id,
    o.nombre_organizacion,
    u.email,
    u.nombres as contacto,
    o.numero_documento,
    o.razon_social,
    o.fecha_registro,
    DATEDIFF(CURDATE(), DATE(o.fecha_registro)) as dias_pendiente
FROM organizaciones o
INNER JOIN usuarios u ON o.usuario_id = u.id
WHERE o.estado_aprobacion = 'PENDIENTE'
ORDER BY o.fecha_registro ASC;

-- Eventos pendientes de aprobación con información de organización
SELECT 
    e.id,
    e.nombre as evento,
    e.fecha_inicio,
    e.cupos_maximos,
    o.nombre_organizacion,
    u.email as org_email,
    e.fecha_creacion,
    DATEDIFF(CURDATE(), DATE(e.fecha_creacion)) as dias_pendiente
FROM eventos e
INNER JOIN organizaciones o ON e.organizacion_id = o.id
INNER JOIN usuarios u ON o.usuario_id = u.id
WHERE e.estado = 'PENDIENTE_APROBACION'
ORDER BY e.fecha_creacion ASC;

-- Reporte: Eventos más populares (por inscripciones)
SELECT 
    e.id,
    e.nombre,
    o.nombre_organizacion,
    e.fecha_inicio,
    COUNT(i.id) as total_inscritos,
    e.cupos_maximos,
    CASE 
        WHEN e.cupos_maximos IS NULL OR e.cupos_maximos = 0 THEN 'Sin límite'
        ELSE CONCAT(ROUND((COUNT(i.id) / e.cupos_maximos) * 100, 2), '%')
    END as porcentaje_ocupacion
FROM eventos e
INNER JOIN organizaciones o ON e.organizacion_id = o.id
LEFT JOIN inscripciones i ON e.id = i.evento_id AND i.estado != 'CANCELADO'
WHERE e.estado = 'APROBADO'
GROUP BY e.id
ORDER BY total_inscritos DESC
LIMIT 10;

-- Reporte: Voluntarios más activos
SELECT 
    v.id,
    u.nombres,
    u.apellido_paterno,
    u.email,
    COUNT(i.id) as eventos_inscritos,
    COUNT(CASE WHEN i.estado = 'ASISTIO' THEN 1 END) as eventos_asistidos,
    SUM(CASE WHEN c.horas_voluntariado IS NOT NULL THEN c.horas_voluntariado ELSE 0 END) as total_horas
FROM voluntarios v
INNER JOIN usuarios u ON v.usuario_id = u.id
LEFT JOIN inscripciones i ON v.id = i.voluntario_id
LEFT JOIN certificaciones c ON i.id = c.inscripcion_id
WHERE u.estado_activo = TRUE
GROUP BY v.id
HAVING eventos_inscritos > 0
ORDER BY total_horas DESC, eventos_asistidos DESC
LIMIT 20;

-- Reporte: Organizaciones más activas
SELECT 
    o.id,
    o.nombre_organizacion,
    u.email,
    COUNT(DISTINCT e.id) as total_eventos,
    COUNT(DISTINCT i.id) as total_voluntarios_captados,
    SUM(d.monto) as total_donaciones_recibidas
FROM organizaciones o
INNER JOIN usuarios u ON o.usuario_id = u.id
LEFT JOIN eventos e ON o.id = e.organizacion_id
LEFT JOIN inscripciones i ON e.id = i.evento_id
LEFT JOIN donaciones d ON o.id = d.organizacion_id AND d.estado = 'CONFIRMADA'
WHERE o.estado_aprobacion = 'APROBADO'
GROUP BY o.id
ORDER BY total_eventos DESC, total_voluntarios_captados DESC
LIMIT 10;

-- Reporte: Eventos por mes
SELECT 
    YEAR(e.fecha_inicio) as anio,
    MONTH(e.fecha_inicio) as mes,
    COUNT(e.id) as total_eventos,
    SUM(e.cupos_maximos) as total_cupos,
    COUNT(DISTINCT i.voluntario_id) as voluntarios_unicos
FROM eventos e
LEFT JOIN inscripciones i ON e.id = i.evento_id
WHERE e.estado = 'APROBADO'
GROUP BY YEAR(e.fecha_inicio), MONTH(e.fecha_inicio)
ORDER BY anio DESC, mes DESC;

-- ========================================
-- CONSULTAS DE VALIDACIÓN Y MANTENIMIENTO
-- ========================================

-- Verificar integridad: Cupos disponibles vs inscripciones (solo eventos con límite)
SELECT 
    e.id,
    e.nombre,
    e.cupos_maximos,
    e.cupos_disponibles,
    COUNT(i.id) as inscritos_reales,
    (e.cupos_maximos - COUNT(i.id)) as cupos_esperados,
    CASE 
        WHEN e.cupos_disponibles != (e.cupos_maximos - COUNT(i.id)) 
        THEN 'INCONSISTENTE' 
        ELSE 'OK' 
    END as estado_validacion
FROM eventos e
LEFT JOIN inscripciones i ON e.id = i.evento_id AND i.estado != 'CANCELADO'
WHERE e.cupos_maximos IS NOT NULL AND e.cupos_maximos > 0  -- Solo verificar eventos con límite
GROUP BY e.id
HAVING estado_validacion = 'INCONSISTENTE';

-- Eventos que ya pasaron pero aún están como "APROBADO"
SELECT 
    e.id,
    e.nombre,
    e.fecha_fin,
    e.estado,
    DATEDIFF(CURDATE(), e.fecha_fin) as dias_vencido
FROM eventos e
WHERE e.estado = 'APROBADO'
  AND e.fecha_fin < CURDATE();

-- Usuarios inactivos sin actividad reciente
SELECT 
    u.id,
    u.email,
    u.nombres,
    u.rol,
    u.ultimo_login,
    DATEDIFF(CURDATE(), u.ultimo_login) as dias_sin_actividad
FROM usuarios u
WHERE u.estado_activo = TRUE
  AND u.ultimo_login IS NOT NULL
  AND u.ultimo_login < DATE_SUB(CURDATE(), INTERVAL 180 DAY)
ORDER BY u.ultimo_login ASC;

-- ========================================
-- BÚSQUEDAS Y FILTROS
-- ========================================

-- Búsqueda de eventos por múltiples criterios
SELECT 
    e.*,
    o.nombre_organizacion
FROM eventos e
INNER JOIN organizaciones o ON e.organizacion_id = o.id
WHERE e.estado = 'APROBADO'
  AND e.fecha_inicio >= CURDATE()
  AND (
    e.nombre LIKE '%limpieza%'  -- Término de búsqueda
    OR e.descripcion LIKE '%limpieza%'
  )
ORDER BY e.fecha_inicio ASC;

-- Filtrar eventos por rango de fechas
SELECT 
    e.id,
    e.nombre,
    e.fecha_inicio,
    e.fecha_fin,
    o.nombre_organizacion
FROM eventos e
INNER JOIN organizaciones o ON e.organizacion_id = o.id
WHERE e.estado = 'APROBADO'
  AND e.fecha_inicio BETWEEN '2025-01-01' AND '2025-12-31'
ORDER BY e.fecha_inicio ASC;

-- Eventos disponibles en una ubicación (incluye sin límite)
SELECT 
    e.id,
    e.nombre,
    e.lugar,
    e.fecha_inicio,
    CASE 
        WHEN e.cupos_maximos IS NULL OR e.cupos_maximos = 0 THEN 'Sin límite'
        ELSE CONCAT(e.cupos_disponibles, ' cupos disponibles')
    END as disponibilidad,
    o.nombre_organizacion
FROM eventos e
INNER JOIN organizaciones o ON e.organizacion_id = o.id
WHERE e.estado = 'APROBADO'
  AND e.fecha_inicio >= CURDATE()
  AND (e.cupos_maximos IS NULL OR e.cupos_maximos = 0 OR e.cupos_disponibles > 0)
  AND e.lugar LIKE '%Lima%'  -- Cambiar ubicación
ORDER BY e.fecha_inicio ASC;

-- ========================================
-- CONSULTAS ESPECÍFICAS PARA SISTEMA DE CUPOS OPCIONAL
-- ========================================

-- Listar solo eventos SIN límite de cupos
SELECT 
    e.id,
    e.nombre,
    e.fecha_inicio,
    e.estado,
    COUNT(i.id) as total_inscritos,
    o.nombre_organizacion
FROM eventos e
INNER JOIN organizaciones o ON e.organizacion_id = o.id
LEFT JOIN inscripciones i ON e.id = i.evento_id AND i.estado != 'CANCELADO'
WHERE (e.cupos_maximos IS NULL OR e.cupos_maximos = 0)
  AND e.estado = 'APROBADO'
GROUP BY e.id
ORDER BY e.fecha_inicio DESC;

-- Listar solo eventos CON límite de cupos
SELECT 
    e.id,
    e.nombre,
    e.fecha_inicio,
    e.cupos_maximos,
    e.cupos_disponibles,
    (e.cupos_maximos - e.cupos_disponibles) as inscritos,
    ROUND(((e.cupos_maximos - e.cupos_disponibles) / e.cupos_maximos) * 100, 2) as porcentaje_ocupado,
    o.nombre_organizacion
FROM eventos e
INNER JOIN organizaciones o ON e.organizacion_id = o.id
WHERE e.cupos_maximos IS NOT NULL 
  AND e.cupos_maximos > 0
  AND e.estado = 'APROBADO'
ORDER BY porcentaje_ocupado DESC;

-- Eventos próximos a llenarse (>80% ocupación)
SELECT 
    e.id,
    e.nombre,
    e.fecha_inicio,
    e.cupos_maximos,
    e.cupos_disponibles,
    ROUND(((e.cupos_maximos - e.cupos_disponibles) / e.cupos_maximos) * 100, 2) as porcentaje_ocupado
FROM eventos e
WHERE e.cupos_maximos IS NOT NULL 
  AND e.cupos_maximos > 0
  AND e.estado = 'APROBADO'
  AND e.fecha_inicio >= CURDATE()
  AND ((e.cupos_maximos - e.cupos_disponibles) / e.cupos_maximos) >= 0.80
ORDER BY porcentaje_ocupado DESC;

-- Comparativa: Eventos con límite vs sin límite
SELECT 
    CASE 
        WHEN e.cupos_maximos IS NULL OR e.cupos_maximos = 0 THEN 'Sin límite'
        ELSE 'Con límite'
    END as tipo_evento,
    COUNT(DISTINCT e.id) as total_eventos,
    COUNT(DISTINCT i.id) as total_inscripciones,
    ROUND(AVG(inscritos_por_evento), 2) as promedio_inscritos
FROM eventos e
LEFT JOIN inscripciones i ON e.id = i.evento_id AND i.estado != 'CANCELADO'
LEFT JOIN (
    SELECT evento_id, COUNT(*) as inscritos_por_evento
    FROM inscripciones
    WHERE estado != 'CANCELADO'
    GROUP BY evento_id
) sub ON e.id = sub.evento_id
WHERE e.estado = 'APROBADO'
GROUP BY tipo_evento;

-- ========================================
-- Fin de consultas
-- ========================================
