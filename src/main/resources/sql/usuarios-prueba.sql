-- ===============================================
-- RUWANA - DATOS DE PRUEBA
-- ===============================================
-- Passwords (todas son: 'password123'):
-- BCrypt hash: $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy
-- ===============================================

USE ruwana_db;

-- ===============================================
-- 1. USUARIO ADMINISTRADOR
-- ===============================================
INSERT INTO usuarios (
    nombres, 
    apellido_paterno, 
    apellido_materno, 
    email, 
    password, 
    rol, 
    estado_activo, 
    estado_verificado, 
    fecha_verificacion
) VALUES (
    'Admin',
    'Sistema',
    'Ruwana',
    'admin@ruwana.org',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
    'ADMINISTRADOR',
    TRUE,
    TRUE,
    NOW()
);

-- ===============================================
-- 2. USUARIO ORGANIZACIÓN (Aprobada)
-- ===============================================
-- Insertar usuario
INSERT INTO usuarios (
    nombres, 
    apellido_paterno, 
    apellido_materno, 
    email, 
    password, 
    rol, 
    estado_activo, 
    estado_verificado, 
    fecha_verificacion
) VALUES (
    'María',
    'González',
    'Pérez',
    'contacto@fundacionayuda.org',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
    'ORGANIZACION',
    TRUE,
    TRUE,
    NOW()
);

-- Obtener el ID del usuario de organización
SET @org_usuario_id = LAST_INSERT_ID();

-- Insertar datos de organización
INSERT INTO organizaciones (
    usuario_id,
    nombre_organizacion,
    pais,
    direccion,
    contacto_principal,
    telefono,
    celular,
    tipo_documento_id,
    numero_documento,
    razon_social,
    constituida_legalmente,
    motivo_registro,
    descripcion_beneficiarios,
    estado_aprobacion,
    fecha_aprobacion
) VALUES (
    @org_usuario_id,
    'Fundación Ayuda Perú',
    'Perú',
    'Av. Arequipa 1234, Miraflores, Lima',
    'María González',
    '(01) 234-5678',
    '987 654 321',
    (SELECT id FROM cat_tipo_documento WHERE codigo = 'RUC' LIMIT 1),
    '20123456789',
    'Fundación Ayuda Perú S.A.C.',
    TRUE,
    'Queremos conectar con voluntarios comprometidos para ampliar nuestro impacto social en comunidades vulnerables.',
    'Trabajamos con niños y adolescentes de zonas rurales, brindándoles educación y alimentación.',
    'APROBADO',
    NOW()
);

-- ===============================================
-- 3. USUARIO VOLUNTARIO
-- ===============================================
-- Insertar usuario
INSERT INTO usuarios (
    nombres, 
    apellido_paterno, 
    apellido_materno, 
    email, 
    password, 
    rol, 
    estado_activo, 
    estado_verificado, 
    fecha_verificacion
) VALUES (
    'Carlos',
    'Mendoza',
    'Torres',
    'carlos.mendoza@gmail.com',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
    'VOLUNTARIO',
    TRUE,
    TRUE,
    NOW()
);

-- Obtener el ID del usuario voluntario
SET @vol_usuario_id = LAST_INSERT_ID();

-- Insertar datos de voluntario
INSERT INTO voluntarios (
    usuario_id,
    fecha_nacimiento,
    genero_id,
    estado_civil_id,
    nacionalidad,
    tipo_documento_id,
    numero_documento,
    grado_instruccion_id,
    centro_estudios_id,
    nombre_centro_estudios,
    correo_institucional,
    carrera
) VALUES (
    @vol_usuario_id,
    '1995-05-15',
    (SELECT id FROM cat_genero WHERE codigo = 'M' LIMIT 1),
    (SELECT id FROM cat_estado_civil WHERE codigo = 'SOLTERO' LIMIT 1),
    'Peruana',
    (SELECT id FROM cat_tipo_documento WHERE codigo = 'DNI' LIMIT 1),
    '72345678',
    (SELECT id FROM cat_grado_instruccion WHERE codigo = 'UNIVERSITARIO' LIMIT 1),
    (SELECT id FROM cat_centro_estudios WHERE codigo = 'UNIVERSIDAD' LIMIT 1),
    'Universidad Nacional Mayor de San Marcos',
    'carlos.mendoza@unmsm.edu.pe',
    'Ingeniería de Sistemas'
);

-- ===============================================
-- 4. EVENTO DE PRUEBA (Aprobado)
-- ===============================================
INSERT INTO eventos (
    organizacion_id,
    nombre,
    descripcion,
    informacion_programa,
    fecha_inicio,
    fecha_fin,
    horario,
    lugar,
    direccion,
    telefono_contacto,
    cupos_maximos,
    cupos_disponibles,
    estado,
    fecha_aprobacion
) VALUES (
    (SELECT id FROM organizaciones WHERE numero_documento = '20123456789'),
    'Campaña de Limpieza Playas de Lima',
    '<p>Únete a nuestra campaña de limpieza de playas en la Costa Verde. Ayúdanos a mantener nuestras playas limpias y proteger el medio ambiente.</p>
    <p><strong>Incluye:</strong></p>
    <ul>
        <li>Materiales de limpieza</li>
        <li>Refrigerio</li>
        <li>Certificado de participación</li>
    </ul>',
    'Actividad de voluntariado ambiental para la preservación de nuestras playas',
    '2025-11-15',
    '2025-11-15',
    '8:00 AM - 1:00 PM',
    'Costa Verde - Miraflores',
    'Playa Redondo, Costa Verde, Miraflores, Lima',
    '987 654 321',
    50,
    50,
    'APROBADO',
    NOW()
);

-- ===============================================
-- 5. OTRO EVENTO DE PRUEBA (Pendiente)
-- ===============================================
INSERT INTO eventos (
    organizacion_id,
    nombre,
    descripcion,
    informacion_programa,
    fecha_inicio,
    fecha_fin,
    horario,
    lugar,
    direccion,
    telefono_contacto,
    cupos_maximos,
    cupos_disponibles,
    estado
) VALUES (
    (SELECT id FROM organizaciones WHERE numero_documento = '20123456789'),
    'Jornada de Alfabetización en San Juan de Lurigancho',
    '<p>Buscamos voluntarios para enseñar lectura y escritura básica a adultos mayores en comunidades vulnerables.</p>
    <p><strong>Requisitos:</strong></p>
    <ul>
        <li>Paciencia y empatía</li>
        <li>Disponibilidad los sábados</li>
        <li>Compromiso de 2 meses</li>
    </ul>',
    'Programa de alfabetización para adultos mayores',
    '2025-12-01',
    '2026-01-31',
    'Sábados de 9:00 AM - 12:00 PM',
    'Centro Comunitario San Juan',
    'Jr. Los Pinos 456, San Juan de Lurigancho, Lima',
    '987 654 321',
    20,
    20,
    'PENDIENTE_APROBACION'
);

-- ===============================================
-- RESUMEN DE USUARIOS CREADOS
-- ===============================================
-- Email: admin@ruwana.org
-- Password: password123
-- Rol: ADMINISTRADOR
--
-- Email: contacto@fundacionayuda.org
-- Password: password123
-- Rol: ORGANIZACION (Aprobada)
--
-- Email: carlos.mendoza@gmail.com
-- Password: password123
-- Rol: VOLUNTARIO
-- ===============================================
