-- ========================================
-- Base de Datos: Ruwana
-- Sistema de Registro de Eventos de Voluntariado
-- MySQL 8.0+
-- ========================================

DROP DATABASE IF EXISTS ruwana_db;
CREATE DATABASE ruwana_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ruwana_db;

-- ========================================
-- TABLAS DE CATÁLOGO (Datos de Configuración)
-- ========================================

-- Tabla: cat_genero
CREATE TABLE cat_genero (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(30) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    activo BOOLEAN DEFAULT TRUE,
    orden INT DEFAULT 0,
    INDEX idx_activo (activo)
) ENGINE=InnoDB;

-- Tabla: cat_estado_civil
CREATE TABLE cat_estado_civil (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(30) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    activo BOOLEAN DEFAULT TRUE,
    orden INT DEFAULT 0,
    INDEX idx_activo (activo)
) ENGINE=InnoDB;

-- Tabla: cat_tipo_documento
CREATE TABLE cat_tipo_documento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(30) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    pais VARCHAR(100),
    activo BOOLEAN DEFAULT TRUE,
    orden INT DEFAULT 0,
    INDEX idx_activo (activo),
    INDEX idx_pais (pais)
) ENGINE=InnoDB;

-- Tabla: cat_grado_instruccion
CREATE TABLE cat_grado_instruccion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(30) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    activo BOOLEAN DEFAULT TRUE,
    orden INT DEFAULT 0,
    INDEX idx_activo (activo)
) ENGINE=InnoDB;

-- Tabla: cat_centro_estudios
CREATE TABLE cat_centro_estudios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(30) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    activo BOOLEAN DEFAULT TRUE,
    orden INT DEFAULT 0,
    INDEX idx_activo (activo)
) ENGINE=InnoDB;

-- ========================================
-- Tabla: usuarios (base para todos los tipos de usuario)
-- ========================================
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol ENUM('ADMINISTRADOR', 'ORGANIZACION', 'VOLUNTARIO') NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    apellido_paterno VARCHAR(100) NOT NULL,
    apellido_materno VARCHAR(100),
    estado_activo BOOLEAN DEFAULT TRUE,
    estado_verificado BOOLEAN DEFAULT FALSE,
    fecha_verificacion DATETIME,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    ultimo_login DATETIME,
    INDEX idx_email (email),
    INDEX idx_rol (rol),
    INDEX idx_estado (estado_activo, estado_verificado)
) ENGINE=InnoDB;

-- ========================================
-- Tabla: voluntarios (datos específicos de voluntarios)
-- ========================================
CREATE TABLE voluntarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL UNIQUE,
    fecha_nacimiento DATE,
    estado_civil_id INT,
    genero_id INT,
    nacionalidad VARCHAR(100),
    tipo_documento_id INT,
    numero_documento VARCHAR(50),
    
    -- Datos educativos
    grado_instruccion_id INT,
    centro_estudios_id INT,
    nombre_centro_estudios VARCHAR(255),
    correo_institucional VARCHAR(255),
    carrera VARCHAR(255),
    
    -- Metadatos
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    ultima_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (estado_civil_id) REFERENCES cat_estado_civil(id) ON DELETE SET NULL,
    FOREIGN KEY (genero_id) REFERENCES cat_genero(id) ON DELETE SET NULL,
    FOREIGN KEY (tipo_documento_id) REFERENCES cat_tipo_documento(id) ON DELETE SET NULL,
    FOREIGN KEY (grado_instruccion_id) REFERENCES cat_grado_instruccion(id) ON DELETE SET NULL,
    FOREIGN KEY (centro_estudios_id) REFERENCES cat_centro_estudios(id) ON DELETE SET NULL,
    INDEX idx_estado_civil (estado_civil_id),
    INDEX idx_genero (genero_id),
    INDEX idx_tipo_documento (tipo_documento_id),
    INDEX idx_documento (numero_documento)
) ENGINE=InnoDB;

-- ========================================
-- Tabla: organizaciones (datos específicos de organizaciones)
-- ========================================
CREATE TABLE organizaciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL UNIQUE,
    nombre_organizacion VARCHAR(255) NOT NULL,
    pais VARCHAR(100) DEFAULT 'Perú',
    direccion TEXT,
    contacto_principal VARCHAR(255),
    telefono VARCHAR(20),
    celular VARCHAR(20),
    
    -- Datos legales
    tipo_documento_id INT,
    numero_documento VARCHAR(50) UNIQUE,
    constituida_legalmente BOOLEAN DEFAULT FALSE,
    razon_social VARCHAR(255),
    
    -- Información adicional
    motivo_registro TEXT,
    descripcion_beneficiarios TEXT,
    
    -- Estado de aprobación
    estado_aprobacion ENUM('PENDIENTE', 'APROBADO', 'RECHAZADO') DEFAULT 'PENDIENTE',
    fecha_aprobacion DATETIME,
    admin_aprobador_id BIGINT,
    motivo_rechazo TEXT,
    
    -- Metadatos
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    ultima_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (admin_aprobador_id) REFERENCES usuarios(id) ON DELETE SET NULL,
    FOREIGN KEY (tipo_documento_id) REFERENCES cat_tipo_documento(id) ON DELETE SET NULL,
    INDEX idx_estado_aprobacion (estado_aprobacion),
    INDEX idx_tipo_documento (tipo_documento_id),
    INDEX idx_documento (numero_documento)
) ENGINE=InnoDB;

-- ========================================
-- Tabla: eventos
-- ========================================
CREATE TABLE eventos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    organizacion_id BIGINT NOT NULL,
    
    -- Información básica
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    descripcion_html MEDIUMTEXT,
    informacion_programa TEXT,
    
    -- Fechas y horarios
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    horario VARCHAR(255),
    
    -- Ubicación
    lugar VARCHAR(255),
    direccion TEXT,
    
    -- Contacto
    telefono_contacto VARCHAR(20),
    email_contacto VARCHAR(255),
    
    -- Capacidad (NULL o 0 = sin límite de cupos)
    cupos_maximos INT DEFAULT NULL,
    cupos_disponibles INT DEFAULT NULL,
    
    -- Multimedia
    imagen_portada VARCHAR(500),
    
    -- Estado y aprobación
    estado ENUM('BORRADOR', 'PENDIENTE_APROBACION', 'APROBADO', 'RECHAZADO', 'EN_CURSO', 'FINALIZADO', 'CANCELADO') DEFAULT 'PENDIENTE_APROBACION',
    fecha_aprobacion DATETIME,
    admin_aprobador_id BIGINT,
    motivo_rechazo TEXT,
    
    -- Metadatos
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    ultima_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (organizacion_id) REFERENCES organizaciones(id) ON DELETE CASCADE,
    FOREIGN KEY (admin_aprobador_id) REFERENCES usuarios(id) ON DELETE SET NULL,
    INDEX idx_organizacion (organizacion_id),
    INDEX idx_estado (estado),
    INDEX idx_fechas (fecha_inicio, fecha_fin),
    INDEX idx_fecha_creacion (fecha_creacion)
) ENGINE=InnoDB;

-- ========================================
-- Tabla: inscripciones (relación N:M entre voluntarios y eventos)
-- ========================================
CREATE TABLE inscripciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    voluntario_id BIGINT NOT NULL,
    evento_id BIGINT NOT NULL,
    
    -- Estado de la inscripción
    estado ENUM('INSCRITO', 'CONFIRMADO', 'ASISTIO', 'NO_ASISTIO', 'CANCELADO') DEFAULT 'INSCRITO',
    
    -- Datos de confirmación
    fecha_inscripcion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_confirmacion DATETIME,
    fecha_asistencia DATETIME,
    
    -- Certificación
    certificado_generado BOOLEAN DEFAULT FALSE,
    fecha_certificado DATETIME,
    
    -- Notas
    observaciones TEXT,
    
    -- Evitar duplicados
    UNIQUE KEY unique_inscripcion (voluntario_id, evento_id),
    
    FOREIGN KEY (voluntario_id) REFERENCES voluntarios(id) ON DELETE CASCADE,
    FOREIGN KEY (evento_id) REFERENCES eventos(id) ON DELETE CASCADE,
    INDEX idx_voluntario (voluntario_id),
    INDEX idx_evento (evento_id),
    INDEX idx_estado (estado),
    INDEX idx_fecha_inscripcion (fecha_inscripcion)
) ENGINE=InnoDB;

-- ========================================
-- Tabla: donaciones
-- ========================================
CREATE TABLE donaciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    voluntario_id BIGINT NOT NULL,
    evento_id BIGINT,
    organizacion_id BIGINT,
    
    -- Datos de la donación
    monto DECIMAL(10, 2) NOT NULL,
    moneda VARCHAR(3) DEFAULT 'PEN',
    tipo_donacion ENUM('MONETARIA', 'ESPECIE', 'TIEMPO') DEFAULT 'MONETARIA',
    descripcion TEXT,
    
    -- Método de pago (si aplica)
    metodo_pago ENUM('EFECTIVO', 'TRANSFERENCIA', 'TARJETA', 'YAPE', 'PLIN', 'OTRO'),
    numero_transaccion VARCHAR(100),
    
    -- Estado
    estado ENUM('PENDIENTE', 'CONFIRMADA', 'RECHAZADA') DEFAULT 'PENDIENTE',
    
    -- Comprobante
    comprobante_url VARCHAR(500),
    
    -- Metadatos
    fecha_donacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_confirmacion DATETIME,
    
    FOREIGN KEY (voluntario_id) REFERENCES voluntarios(id) ON DELETE CASCADE,
    FOREIGN KEY (evento_id) REFERENCES eventos(id) ON DELETE SET NULL,
    FOREIGN KEY (organizacion_id) REFERENCES organizaciones(id) ON DELETE SET NULL,
    INDEX idx_voluntario (voluntario_id),
    INDEX idx_evento (evento_id),
    INDEX idx_organizacion (organizacion_id),
    INDEX idx_fecha (fecha_donacion),
    INDEX idx_estado (estado)
) ENGINE=InnoDB;

-- ========================================
-- Tabla: certificaciones
-- ========================================
CREATE TABLE certificaciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inscripcion_id BIGINT NOT NULL UNIQUE,
    
    -- Datos del certificado
    codigo_verificacion VARCHAR(100) UNIQUE NOT NULL,
    horas_voluntariado INT DEFAULT 0,
    descripcion_actividades TEXT,
    
    -- Archivo
    archivo_url VARCHAR(500),
    formato ENUM('PDF', 'PNG', 'JPG') DEFAULT 'PDF',
    
    -- Emisión
    fecha_emision DATETIME DEFAULT CURRENT_TIMESTAMP,
    emitido_por BIGINT,
    
    -- Estado
    estado ENUM('GENERADO', 'ENVIADO', 'DESCARGADO') DEFAULT 'GENERADO',
    
    FOREIGN KEY (inscripcion_id) REFERENCES inscripciones(id) ON DELETE CASCADE,
    FOREIGN KEY (emitido_por) REFERENCES usuarios(id) ON DELETE SET NULL,
    INDEX idx_codigo (codigo_verificacion),
    INDEX idx_fecha_emision (fecha_emision)
) ENGINE=InnoDB;

-- ========================================
-- Tabla: notificaciones
-- ========================================
CREATE TABLE notificaciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    
    -- Contenido
    tipo ENUM('REGISTRO', 'APROBACION', 'RECHAZO', 'INSCRIPCION', 'RECORDATORIO', 'CERTIFICADO', 'GENERAL') NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    mensaje TEXT NOT NULL,
    
    -- Canal
    canal ENUM('EMAIL', 'SISTEMA', 'SMS') DEFAULT 'EMAIL',
    
    -- Estado
    enviada BOOLEAN DEFAULT FALSE,
    leida BOOLEAN DEFAULT FALSE,
    fecha_envio DATETIME,
    fecha_lectura DATETIME,
    
    -- Relacionado con
    evento_id BIGINT,
    
    -- Metadatos
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (evento_id) REFERENCES eventos(id) ON DELETE SET NULL,
    INDEX idx_usuario (usuario_id),
    INDEX idx_tipo (tipo),
    INDEX idx_leida (leida),
    INDEX idx_fecha_creacion (fecha_creacion)
) ENGINE=InnoDB;

-- ========================================
-- Tabla: auditoria (opcional - para tracking de cambios)
-- ========================================
CREATE TABLE auditoria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tabla VARCHAR(50) NOT NULL,
    registro_id BIGINT NOT NULL,
    usuario_id BIGINT,
    accion ENUM('INSERT', 'UPDATE', 'DELETE') NOT NULL,
    datos_anteriores JSON,
    datos_nuevos JSON,
    fecha_accion DATETIME DEFAULT CURRENT_TIMESTAMP,
    ip_address VARCHAR(45),
    
    INDEX idx_tabla_registro (tabla, registro_id),
    INDEX idx_usuario (usuario_id),
    INDEX idx_fecha (fecha_accion)
) ENGINE=InnoDB;

-- ========================================
-- Datos Iniciales - Catálogos
-- ========================================

-- cat_genero
INSERT INTO cat_genero (codigo, nombre, descripcion, orden) VALUES
('MASCULINO', 'Masculino', 'Género masculino', 1),
('FEMENINO', 'Femenino', 'Género femenino', 2),
('OTRO', 'Otro', 'Otro género', 3),
('PREFIERO_NO_DECIR', 'Prefiero no decir', 'Prefiere no especificar', 4);

-- cat_estado_civil
INSERT INTO cat_estado_civil (codigo, nombre, descripcion, orden) VALUES
('SOLTERO', 'Soltero/a', 'Estado civil soltero', 1),
('CASADO', 'Casado/a', 'Estado civil casado', 2),
('DIVORCIADO', 'Divorciado/a', 'Estado civil divorciado', 3),
('VIUDO', 'Viudo/a', 'Estado civil viudo', 4),
('OTRO', 'Otro', 'Otro estado civil', 5);

-- cat_tipo_documento
INSERT INTO cat_tipo_documento (codigo, nombre, descripcion, pais, orden) VALUES
('DNI', 'DNI', 'Documento Nacional de Identidad', 'Perú', 1),
('CEDULA', 'Cédula', 'Cédula de Ciudadanía', 'Colombia', 2),
('RUC', 'RUC', 'Registro Único de Contribuyentes', 'Perú', 3),
('RUT', 'RUT', 'Rol Único Tributario', 'Chile', 4),
('NIT', 'NIT', 'Número de Identificación Tributaria', 'Colombia', 5),
('PASAPORTE', 'Pasaporte', 'Pasaporte Internacional', NULL, 6),
('OTRO', 'Otro', 'Otro tipo de documento', NULL, 7);

-- cat_grado_instruccion
INSERT INTO cat_grado_instruccion (codigo, nombre, descripcion, orden) VALUES
('SIN_NIVEL', 'Sin nivel', 'Sin educación formal', 1),
('PRIMARIA', 'Primaria', 'Educación primaria', 2),
('SECUNDARIA', 'Secundaria', 'Educación secundaria', 3),
('TECNICO', 'Técnico', 'Educación técnica', 4),
('UNIVERSITARIO', 'Universitario', 'Educación universitaria', 5),
('POSTGRADO', 'Postgrado', 'Educación de postgrado', 6);

-- cat_centro_estudios
INSERT INTO cat_centro_estudios (codigo, nombre, descripcion, orden) VALUES
('COLEGIO', 'Colegio', 'Institución de educación básica', 1),
('INSTITUTO', 'Instituto', 'Instituto técnico o tecnológico', 2),
('UNIVERSIDAD', 'Universidad', 'Universidad', 3),
('OTRO', 'Otro', 'Otro tipo de centro educativo', 4);

-- ========================================
-- Datos iniciales - Usuario Administrador
-- ========================================
-- Password: admin123 (debe ser hasheado en producción)
INSERT INTO usuarios (email, password, rol, nombres, apellido_paterno, apellido_materno, estado_activo, estado_verificado, fecha_verificacion)
VALUES ('admin@ruwana.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMINISTRADOR', 'Admin', 'Sistema', 'Ruwana', TRUE, TRUE, NOW());

-- ========================================
-- Vistas útiles
-- ========================================

-- Vista: Eventos con información de organización
CREATE VIEW vista_eventos_completos AS
SELECT 
    e.*,
    o.nombre_organizacion,
    u.email as organizacion_email,
    u.nombres as organizacion_contacto,
    CASE 
        WHEN e.cupos_maximos IS NULL OR e.cupos_maximos = 0 THEN (SELECT COUNT(*) FROM inscripciones WHERE evento_id = e.id AND estado != 'CANCELADO')
        ELSE (e.cupos_maximos - e.cupos_disponibles)
    END as inscritos_count,
    CASE 
        WHEN e.cupos_maximos IS NULL OR e.cupos_maximos = 0 THEN TRUE
        ELSE FALSE
    END as sin_limite_cupos
FROM eventos e
INNER JOIN organizaciones o ON e.organizacion_id = o.id
INNER JOIN usuarios u ON o.usuario_id = u.id;

-- Vista: Voluntarios con estadísticas
CREATE VIEW vista_voluntarios_stats AS
SELECT 
    v.*,
    u.email,
    u.nombres,
    u.apellido_paterno,
    u.apellido_materno,
    u.estado_activo,
    COUNT(DISTINCT i.id) as eventos_inscritos,
    COUNT(DISTINCT CASE WHEN i.estado = 'ASISTIO' THEN i.id END) as eventos_asistidos,
    SUM(CASE WHEN c.id IS NOT NULL THEN c.horas_voluntariado ELSE 0 END) as total_horas_voluntariado
FROM voluntarios v
INNER JOIN usuarios u ON v.usuario_id = u.id
LEFT JOIN inscripciones i ON v.id = i.voluntario_id
LEFT JOIN certificaciones c ON i.id = c.inscripcion_id
GROUP BY v.id;

-- Vista: Organizaciones con estadísticas
CREATE VIEW vista_organizaciones_stats AS
SELECT 
    o.*,
    u.email,
    u.estado_activo,
    COUNT(DISTINCT e.id) as total_eventos,
    COUNT(DISTINCT CASE WHEN e.estado = 'APROBADO' THEN e.id END) as eventos_aprobados,
    COUNT(DISTINCT i.id) as total_voluntarios_inscritos
FROM organizaciones o
INNER JOIN usuarios u ON o.usuario_id = u.id
LEFT JOIN eventos e ON o.id = e.organizacion_id
LEFT JOIN inscripciones i ON e.id = i.evento_id
GROUP BY o.id;

-- ========================================
-- Procedimientos almacenados útiles
-- ========================================

DELIMITER //

-- Procedimiento: Inscribir voluntario a evento
CREATE PROCEDURE sp_inscribir_voluntario(
    IN p_voluntario_id BIGINT,
    IN p_evento_id BIGINT,
    OUT p_resultado VARCHAR(100)
)
BEGIN
    DECLARE v_cupos_disponibles INT;
    DECLARE v_cupos_maximos INT;
    DECLARE v_ya_inscrito INT;
    
    -- Verificar si ya está inscrito
    SELECT COUNT(*) INTO v_ya_inscrito
    FROM inscripciones
    WHERE voluntario_id = p_voluntario_id AND evento_id = p_evento_id;
    
    IF v_ya_inscrito > 0 THEN
        SET p_resultado = 'ERROR: Ya está inscrito en este evento';
    ELSE
        -- Obtener información de cupos
        SELECT cupos_disponibles, cupos_maximos INTO v_cupos_disponibles, v_cupos_maximos
        FROM eventos
        WHERE id = p_evento_id;
        
        -- Si cupos_maximos es NULL o 0, el evento no tiene límite
        IF v_cupos_maximos IS NULL OR v_cupos_maximos = 0 THEN
            -- Evento sin límite de cupos
            INSERT INTO inscripciones (voluntario_id, evento_id, estado)
            VALUES (p_voluntario_id, p_evento_id, 'INSCRITO');
            
            SET p_resultado = 'SUCCESS: Inscripción exitosa (evento sin límite)';
        ELSE
            -- Evento con límite de cupos
            IF v_cupos_disponibles > 0 THEN
                -- Insertar inscripción
                INSERT INTO inscripciones (voluntario_id, evento_id, estado)
                VALUES (p_voluntario_id, p_evento_id, 'INSCRITO');
                
                -- Actualizar cupos
                UPDATE eventos
                SET cupos_disponibles = cupos_disponibles - 1
                WHERE id = p_evento_id;
                
                SET p_resultado = 'SUCCESS: Inscripción exitosa';
            ELSE
                SET p_resultado = 'ERROR: No hay cupos disponibles';
            END IF;
        END IF;
    END IF;
END //

-- Procedimiento: Cancelar inscripción
CREATE PROCEDURE sp_cancelar_inscripcion(
    IN p_inscripcion_id BIGINT,
    OUT p_resultado VARCHAR(100)
)
BEGIN
    DECLARE v_evento_id BIGINT;
    DECLARE v_cupos_maximos INT;
    
    -- Obtener evento_id
    SELECT evento_id INTO v_evento_id
    FROM inscripciones
    WHERE id = p_inscripcion_id;
    
    -- Obtener información de cupos
    SELECT cupos_maximos INTO v_cupos_maximos
    FROM eventos
    WHERE id = v_evento_id;
    
    -- Actualizar estado
    UPDATE inscripciones
    SET estado = 'CANCELADO'
    WHERE id = p_inscripcion_id;
    
    -- Liberar cupo solo si el evento tiene límite
    IF v_cupos_maximos IS NOT NULL AND v_cupos_maximos > 0 THEN
        UPDATE eventos
        SET cupos_disponibles = cupos_disponibles + 1
        WHERE id = v_evento_id;
    END IF;
    
    SET p_resultado = 'SUCCESS: Inscripción cancelada';
END //

DELIMITER ;

-- ========================================
-- Fin del script
-- ========================================
