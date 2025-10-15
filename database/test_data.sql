-- ========================================
-- Datos de Prueba - Ruwana
-- ========================================
USE ruwana_db;

-- ========================================
-- Voluntarios de prueba
-- ========================================

-- Usuario voluntario 1
INSERT INTO usuarios (email, password, rol, nombres, apellido_paterno, apellido_materno, estado_activo, estado_verificado, fecha_verificacion)
VALUES ('juan.perez@gmail.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'VOLUNTARIO', 'Juan', 'Pérez', 'García', TRUE, TRUE, NOW());

INSERT INTO voluntarios (usuario_id, fecha_nacimiento, estado_civil, genero, nacionalidad, tipo_documento, numero_documento, grado_instruccion, centro_estudios, nombre_centro_estudios, carrera)
VALUES (LAST_INSERT_ID(), '1995-05-15', 'SOLTERO', 'MASCULINO', 'Peruana', 'DNI', '72345678', 'UNIVERSITARIO', 'UNIVERSIDAD', 'Universidad Tecnológica del Perú', 'Ingeniería de Sistemas');

-- Usuario voluntario 2
INSERT INTO usuarios (email, password, rol, nombres, apellido_paterno, apellido_materno, estado_activo, estado_verificado, fecha_verificacion)
VALUES ('maria.lopez@gmail.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'VOLUNTARIO', 'María', 'López', 'Sánchez', TRUE, TRUE, NOW());

INSERT INTO voluntarios (usuario_id, fecha_nacimiento, estado_civil, genero, nacionalidad, tipo_documento, numero_documento, grado_instruccion, centro_estudios, nombre_centro_estudios, carrera)
VALUES (LAST_INSERT_ID(), '1998-08-22', 'SOLTERA', 'FEMENINO', 'Peruana', 'DNI', '73456789', 'UNIVERSITARIO', 'UNIVERSIDAD', 'Universidad Nacional Mayor de San Marcos', 'Administración');

-- Usuario voluntario 3
INSERT INTO usuarios (email, password, rol, nombres, apellido_paterno, apellido_materno, estado_activo, estado_verificado, fecha_verificacion)
VALUES ('carlos.ramirez@outlook.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'VOLUNTARIO', 'Carlos', 'Ramírez', 'Torres', TRUE, TRUE, NOW());

INSERT INTO voluntarios (usuario_id, fecha_nacimiento, estado_civil, genero, nacionalidad, tipo_documento, numero_documento, grado_instruccion, centro_estudios, nombre_centro_estudios, carrera)
VALUES (LAST_INSERT_ID(), '2000-03-10', 'SOLTERO', 'MASCULINO', 'Peruana', 'DNI', '74567890', 'TECNICO', 'INSTITUTO', 'SENATI', 'Computación e Informática');

-- ========================================
-- Organizaciones de prueba
-- ========================================

-- Organización 1 - Aprobada
INSERT INTO usuarios (email, password, rol, nombres, apellido_paterno, apellido_materno, estado_activo, estado_verificado, fecha_verificacion)
VALUES ('contacto@ayudaperu.org', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ORGANIZACION', 'Jorge', 'Mendoza', 'Silva', TRUE, TRUE, NOW());

INSERT INTO organizaciones (usuario_id, nombre_organizacion, pais, direccion, contacto_principal, telefono, celular, tipo_documento, numero_documento, constituida_legalmente, razon_social, motivo_registro, descripcion_beneficiarios, estado_aprobacion, fecha_aprobacion, admin_aprobador_id)
VALUES (LAST_INSERT_ID(), 'Ayuda Perú', 'Perú', 'Av. Arequipa 1234, Lima', 'Jorge Mendoza', '01-4567890', '987654321', 'RUC', '20123456789', TRUE, 'ASOCIACION AYUDA PERU', 'Queremos ampliar nuestro alcance social', 'Niños y adolescentes en situación de vulnerabilidad', 'APROBADO', NOW(), 1);

-- Organización 2 - Aprobada
INSERT INTO usuarios (email, password, rol, nombres, apellido_paterno, apellido_materno, estado_activo, estado_verificado, fecha_verificacion)
VALUES ('info@voluntariosunidos.org', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ORGANIZACION', 'Ana', 'Flores', 'Medina', TRUE, TRUE, NOW());

INSERT INTO organizaciones (usuario_id, nombre_organizacion, pais, direccion, contacto_principal, telefono, celular, tipo_documento, numero_documento, constituida_legalmente, razon_social, motivo_registro, descripcion_beneficiarios, estado_aprobacion, fecha_aprobacion, admin_aprobador_id)
VALUES (LAST_INSERT_ID(), 'Voluntarios Unidos', 'Perú', 'Jr. Lampa 567, Lima Centro', 'Ana Flores', '01-3456789', '965432187', 'RUC', '20234567890', TRUE, 'VOLUNTARIOS UNIDOS SAC', 'Buscamos más voluntarios comprometidos', 'Adultos mayores y comunidades rurales', 'APROBADO', NOW(), 1);

-- Organización 3 - Pendiente de aprobación
INSERT INTO usuarios (email, password, rol, nombres, apellido_paterno, apellido_materno, estado_activo, estado_verificado)
VALUES ('contacto@nuevaesperanza.org', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ORGANIZACION', 'Luis', 'Castro', 'Rojas', TRUE, FALSE);

INSERT INTO organizaciones (usuario_id, nombre_organizacion, pais, direccion, contacto_principal, celular, tipo_documento, numero_documento, constituida_legalmente, razon_social, motivo_registro, descripcion_beneficiarios, estado_aprobacion)
VALUES (LAST_INSERT_ID(), 'Nueva Esperanza', 'Perú', 'Av. Universitaria 890, San Miguel', 'Luis Castro', '912345678', 'RUC', '20345678901', TRUE, 'FUNDACION NUEVA ESPERANZA', 'Queremos ayudar a la comunidad', 'Familias de escasos recursos', 'PENDIENTE');

-- ========================================
-- Eventos de prueba
-- ========================================

-- Evento 1 - Aprobado y próximo (SIN LÍMITE DE CUPOS)
INSERT INTO eventos (organizacion_id, nombre, descripcion, descripcion_html, informacion_programa, fecha_inicio, fecha_fin, horario, lugar, direccion, telefono_contacto, email_contacto, cupos_maximos, cupos_disponibles, estado, fecha_aprobacion, admin_aprobador_id)
VALUES (
    1, 
    'Campaña de Limpieza de Playas',
    'Únete a nuestra campaña de limpieza en las playas de Lima. Ayúdanos a mantener nuestras costas limpias. ¡Todos son bienvenidos!',
    '<p>Únete a nuestra campaña de limpieza en las playas de Lima. Ayúdanos a mantener nuestras costas limpias.</p><ul><li>Actividad grupal</li><li>Materiales incluidos</li><li>Refrigerio</li><li>Sin límite de participantes</li></ul>',
    'Recolección de residuos, clasificación y reciclaje',
    DATE_ADD(CURDATE(), INTERVAL 15 DAY),
    DATE_ADD(CURDATE(), INTERVAL 15 DAY),
    '8:00 AM - 1:00 PM',
    'Playa La Herradura',
    'Chorrillos, Lima',
    '987654321',
    'contacto@ayudaperu.org',
    NULL,
    NULL,
    'APROBADO',
    NOW(),
    1
);

-- Evento 2 - Aprobado y próximo
INSERT INTO eventos (organizacion_id, nombre, descripcion, informacion_programa, fecha_inicio, fecha_fin, horario, lugar, direccion, telefono_contacto, cupos_maximos, cupos_disponibles, estado, fecha_aprobacion, admin_aprobador_id)
VALUES (
    2,
    'Apoyo Educativo a Niños',
    'Voluntariado para apoyo escolar en matemáticas y comunicación',
    'Sesiones de refuerzo académico para estudiantes de primaria',
    DATE_ADD(CURDATE(), INTERVAL 7 DAY),
    DATE_ADD(CURDATE(), INTERVAL 7 DAY),
    '3:00 PM - 6:00 PM',
    'Centro Comunitario San Juan',
    'San Juan de Lurigancho, Lima',
    '965432187',
    30,
    30,
    'APROBADO',
    NOW(),
    1
);

-- Evento 3 - Aprobado, ya pasó (SIN LÍMITE - evento masivo)
INSERT INTO eventos (organizacion_id, nombre, descripcion, informacion_programa, fecha_inicio, fecha_fin, horario, lugar, cupos_maximos, cupos_disponibles, estado, fecha_aprobacion, admin_aprobador_id)
VALUES (
    1,
    'Donación de Alimentos',
    'Jornada de recolección y distribución de alimentos no perecibles. Evento abierto al público.',
    'Recolección en supermercados y distribución en zonas vulnerables',
    DATE_SUB(CURDATE(), INTERVAL 10 DAY),
    DATE_SUB(CURDATE(), INTERVAL 10 DAY),
    '9:00 AM - 5:00 PM',
    'Plaza de Armas de Lima',
    NULL,
    NULL,
    'FINALIZADO',
    DATE_SUB(NOW(), INTERVAL 15 DAY),
    1
);

-- Evento 4 - Pendiente de aprobación
INSERT INTO eventos (organizacion_id, nombre, descripcion, fecha_inicio, fecha_fin, horario, lugar, cupos_maximos, cupos_disponibles, estado)
VALUES (
    1,
    'Taller de Reciclaje Creativo',
    'Enseñanza de técnicas de reciclaje y reutilización de materiales',
    DATE_ADD(CURDATE(), INTERVAL 30 DAY),
    DATE_ADD(CURDATE(), INTERVAL 30 DAY),
    '10:00 AM - 2:00 PM',
    'Parque Kennedy, Miraflores',
    25,
    25,
    'PENDIENTE_APROBACION'
);

-- ========================================
-- Inscripciones de prueba
-- ========================================

-- Juan Pérez inscrito en Campaña de Limpieza (sin límite)
INSERT INTO inscripciones (voluntario_id, evento_id, estado)
VALUES (1, 1, 'INSCRITO');

-- María López inscrita en Apoyo Educativo (con límite)
INSERT INTO inscripciones (voluntario_id, evento_id, estado)
VALUES (2, 2, 'INSCRITO');
UPDATE eventos SET cupos_disponibles = cupos_disponibles - 1 WHERE id = 2;

-- Juan y María asistieron al evento pasado (sin límite)
INSERT INTO inscripciones (voluntario_id, evento_id, estado, fecha_asistencia)
VALUES 
    (1, 3, 'ASISTIO', DATE_SUB(NOW(), INTERVAL 10 DAY)),
    (2, 3, 'ASISTIO', DATE_SUB(NOW(), INTERVAL 10 DAY));

-- Carlos inscrito en ambos eventos próximos
INSERT INTO inscripciones (voluntario_id, evento_id, estado)
VALUES 
    (3, 1, 'INSCRITO'),  -- Evento sin límite
    (3, 2, 'INSCRITO');  -- Evento con límite
    
-- Solo actualizar cupos del evento 2 (que tiene límite)
UPDATE eventos SET cupos_disponibles = cupos_disponibles - 1 WHERE id = 2;

-- ========================================
-- Certificaciones de prueba
-- ========================================

INSERT INTO certificaciones (inscripcion_id, codigo_verificacion, horas_voluntariado, descripcion_actividades, estado)
VALUES 
    (3, 'CERT-2025-001-JUAN', 5, 'Participación en recolección y clasificación de alimentos', 'GENERADO'),
    (4, 'CERT-2025-002-MARIA', 5, 'Participación en recolección y clasificación de alimentos', 'GENERADO');

UPDATE inscripciones SET certificado_generado = TRUE, fecha_certificado = NOW() WHERE id IN (3, 4);

-- ========================================
-- Donaciones de prueba
-- ========================================

INSERT INTO donaciones (voluntario_id, evento_id, monto, moneda, tipo_donacion, descripcion, metodo_pago, estado, fecha_confirmacion)
VALUES 
    (1, 1, 50.00, 'PEN', 'MONETARIA', 'Donación para materiales de limpieza', 'YAPE', 'CONFIRMADA', NOW()),
    (2, 2, 100.00, 'PEN', 'MONETARIA', 'Apoyo para material educativo', 'TRANSFERENCIA', 'CONFIRMADA', NOW());

-- ========================================
-- Notificaciones de prueba
-- ========================================

INSERT INTO notificaciones (usuario_id, tipo, titulo, mensaje, canal, enviada, fecha_envio, evento_id)
VALUES 
    (2, 'INSCRIPCION', 'Inscripción Confirmada', 'Te has inscrito exitosamente en el evento "Campaña de Limpieza de Playas"', 'EMAIL', TRUE, NOW(), 1),
    (3, 'INSCRIPCION', 'Inscripción Confirmada', 'Te has inscrito exitosamente en el evento "Apoyo Educativo a Niños"', 'EMAIL', TRUE, NOW(), 2),
    (2, 'CERTIFICADO', 'Certificado Disponible', 'Tu certificado del evento "Donación de Alimentos" está listo para descargar', 'EMAIL', TRUE, NOW(), 3);

-- ========================================
-- Fin de datos de prueba
-- ========================================
