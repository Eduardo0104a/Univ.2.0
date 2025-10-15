# Base de Datos - Ruwana

Sistema de gestión de base de datos MySQL para la plataforma de registro de eventos de voluntariado.

## 📁 Archivos

| Archivo | Descripción |
|---------|-------------|
| `schema.sql` | Esquema completo de la base de datos (tablas, vistas, procedimientos) |
| `test_data.sql` | Datos de prueba para desarrollo |
| `queries.sql` | Consultas útiles y reportes comunes |

## 🚀 Instalación

### Requisitos Previos
- MySQL 8.0 o superior
- Cliente MySQL (MySQL Workbench, DBeaver, o línea de comandos)

### Paso 1: Instalar MySQL

**Windows:**
```powershell
# Descargar desde: https://dev.mysql.com/downloads/installer/
# O usar Chocolatey:
choco install mysql
```

**Verificar instalación:**
```powershell
mysql --version
```

### Paso 2: Crear la Base de Datos

**Opción A: Desde línea de comandos**

```bash
# Conectar a MySQL
mysql -u root -p

# Ejecutar el script
source C:/Users/user/projects/utp/ruwana/database/schema.sql

# Salir
exit
```

**Opción B: Un solo comando**

```bash
mysql -u root -p < database/schema.sql
```

**Opción C: MySQL Workbench**

1. Abrir MySQL Workbench
2. Conectar al servidor
3. File → Open SQL Script → Seleccionar `schema.sql`
4. Ejecutar (⚡ o Ctrl+Shift+Enter)

### Paso 3: Cargar Datos de Prueba (Opcional)

```bash
mysql -u root -p ruwana_db < database/test_data.sql
```

### Paso 4: Configurar Usuario de Aplicación

```sql
-- Crear usuario específico para la aplicación
CREATE USER 'ruwana_app'@'localhost' IDENTIFIED BY 'ruwana2025';

-- Otorgar permisos
GRANT SELECT, INSERT, UPDATE, DELETE ON ruwana_db.* TO 'ruwana_app'@'localhost';

-- Aplicar cambios
FLUSH PRIVILEGES;
```

## 📊 Estructura de la Base de Datos

### Tablas Principales

```
usuarios (tabla base)
├── voluntarios (1:1 con usuarios)
├── organizaciones (1:1 con usuarios)
└── notificaciones (1:N con usuarios)

eventos (tabla principal de eventos)
├── organizacion_id → organizaciones
├── admin_aprobador_id → usuarios
└── cupos_maximos/disponibles (NULL = sin límite)

inscripciones (N:M entre voluntarios y eventos)
├── voluntario_id → voluntarios
├── evento_id → eventos
└── certificaciones (1:1 con inscripciones)

donaciones
├── voluntario_id → voluntarios
├── evento_id → eventos
└── organizacion_id → organizaciones

auditoria (tabla de trazabilidad)
```

### 📌 Sistema de Cupos (Opcional)

Los eventos pueden tener **cupos limitados** o **sin límite**:

**Eventos CON límite de cupos:**
- `cupos_maximos` > 0
- `cupos_disponibles` se actualiza con cada inscripción
- Útil para: talleres, eventos con transporte, actividades con material limitado

**Eventos SIN límite de cupos:**
- `cupos_maximos` = NULL o 0
- `cupos_disponibles` = NULL
- Útil para: campañas masivas, recolección de donaciones, marchas, limpiezas abiertas

Ejemplos:
```sql
-- Evento con límite (30 personas)
INSERT INTO eventos (nombre, cupos_maximos, cupos_disponibles, ...) 
VALUES ('Taller de Primeros Auxilios', 30, 30, ...);

-- Evento sin límite (abierto a todos)
INSERT INTO eventos (nombre, cupos_maximos, cupos_disponibles, ...) 
VALUES ('Campaña de Limpieza', NULL, NULL, ...);
```

### Diagrama ER Simplificado

```
┌─────────────┐
│  usuarios   │
└──────┬──────┘
       │
   ┌───┴────────────────┬──────────────┐
   │                    │              │
┌──▼────────┐    ┌──────▼─────┐   ┌───▼────────┐
│voluntarios│    │organizacion│   │notificación│
└─────┬─────┘    └──────┬─────┘   └────────────┘
      │                 │
      │                 │
      │          ┌──────▼──────┐
      │          │   eventos   │
      │          └──────┬──────┘
      │                 │
      │    ┌────────────┴────────────┐
      │    │                         │
   ┌──▼────▼────┐              ┌────▼────────┐
   │inscripciones│              │ donaciones  │
   └──────┬──────┘              └─────────────┘
          │
   ┌──────▼────────┐
   │certificaciones│
   └───────────────┘
```

## 🔑 Usuarios de Prueba

### Administrador
- **Email:** admin@ruwana.com
- **Password:** admin123
- **Rol:** ADMINISTRADOR

### Voluntarios
- **Email:** juan.perez@gmail.com | **Password:** admin123
- **Email:** maria.lopez@gmail.com | **Password:** admin123
- **Email:** carlos.ramirez@outlook.com | **Password:** admin123

### Organizaciones
- **Email:** contacto@ayudaperu.org | **Password:** admin123
- **Email:** info@voluntariosunidos.org | **Password:** admin123

> ⚠️ **Nota:** Las contraseñas están hasheadas con BCrypt. En producción, cambiar todas las contraseñas.

## 📈 Vistas Disponibles

### `vista_eventos_completos`
Eventos con información completa de la organización

```sql
SELECT * FROM vista_eventos_completos WHERE estado = 'APROBADO';
```

### `vista_voluntarios_stats`
Voluntarios con estadísticas de participación

```sql
SELECT * FROM vista_voluntarios_stats ORDER BY total_horas_voluntariado DESC;
```

### `vista_organizaciones_stats`
Organizaciones con métricas de eventos

```sql
SELECT * FROM vista_organizaciones_stats WHERE estado_aprobacion = 'APROBADO';
```

## 🔧 Procedimientos Almacenados

### `sp_inscribir_voluntario`
Inscribe un voluntario a un evento verificando cupos

```sql
CALL sp_inscribir_voluntario(1, 1, @resultado);
SELECT @resultado;
```

### `sp_cancelar_inscripcion`
Cancela una inscripción y libera el cupo

```sql
CALL sp_cancelar_inscripcion(1, @resultado);
SELECT @resultado;
```

## 🔍 Consultas Comunes

Ver el archivo `queries.sql` para más ejemplos. Algunas consultas frecuentes:

```sql
-- Eventos disponibles para inscripción (incluye sin límite)
SELECT * FROM vista_eventos_completos 
WHERE estado = 'APROBADO' 
  AND fecha_inicio >= CURDATE()
  AND (cupos_maximos IS NULL OR cupos_maximos = 0 OR cupos_disponibles > 0);

-- Voluntarios inscritos en un evento
SELECT u.nombres, u.apellido_paterno, i.estado
FROM inscripciones i
JOIN voluntarios v ON i.voluntario_id = v.id
JOIN usuarios u ON v.usuario_id = u.id
WHERE i.evento_id = 1;

-- Dashboard de administrador
SELECT 
    (SELECT COUNT(*) FROM usuarios WHERE rol = 'VOLUNTARIO') as voluntarios,
    (SELECT COUNT(*) FROM organizaciones WHERE estado_aprobacion = 'APROBADO') as organizaciones,
    (SELECT COUNT(*) FROM eventos WHERE estado = 'APROBADO') as eventos;

-- Solo eventos sin límite de cupos
SELECT id, nombre, fecha_inicio 
FROM eventos 
WHERE (cupos_maximos IS NULL OR cupos_maximos = 0) 
  AND estado = 'APROBADO';

-- Solo eventos con límite que están por llenarse (>80%)
SELECT nombre, cupos_disponibles, cupos_maximos,
    ROUND(((cupos_maximos - cupos_disponibles) / cupos_maximos) * 100, 2) as ocupacion
FROM eventos 
WHERE cupos_maximos > 0
  AND ((cupos_maximos - cupos_disponibles) / cupos_maximos) >= 0.80;
```

## 🔒 Seguridad

### Buenas Prácticas Implementadas

1. **Contraseñas hasheadas:** BCrypt en `usuarios.password`
2. **Restricciones de integridad:** Foreign keys con CASCADE/SET NULL
3. **Validación de datos:** ENUMs para campos específicos
4. **Índices:** Optimización en campos de búsqueda frecuente
5. **Auditoría:** Tabla de trazabilidad de cambios

### Recomendaciones Adicionales

```sql
-- Habilitar logs de consultas lentas
SET GLOBAL slow_query_log = 'ON';
SET GLOBAL long_query_time = 2;

-- Configurar límites de conexión
SET GLOBAL max_connections = 200;
SET GLOBAL max_user_connections = 50;
```

## 🧹 Mantenimiento

### Backup Manual

```bash
# Backup completo
mysqldump -u root -p ruwana_db > backup_ruwana_$(date +%Y%m%d).sql

# Backup solo estructura
mysqldump -u root -p --no-data ruwana_db > schema_backup.sql

# Backup solo datos
mysqldump -u root -p --no-create-info ruwana_db > data_backup.sql
```

### Restauración

```bash
mysql -u root -p ruwana_db < backup_ruwana_20251015.sql
```

### Limpieza de Datos Antiguos

```sql
-- Eliminar notificaciones antiguas leídas (>6 meses)
DELETE FROM notificaciones 
WHERE leida = TRUE 
  AND fecha_lectura < DATE_SUB(NOW(), INTERVAL 6 MONTH);

-- Archivar eventos finalizados antiguos (>1 año)
UPDATE eventos 
SET estado = 'FINALIZADO' 
WHERE estado = 'APROBADO' 
  AND fecha_fin < DATE_SUB(CURDATE(), INTERVAL 1 YEAR);
```

## 📊 Monitoreo

### Verificar Salud de la Base de Datos

```sql
-- Tamaño de tablas
SELECT 
    table_name,
    ROUND(((data_length + index_length) / 1024 / 1024), 2) AS size_mb
FROM information_schema.TABLES
WHERE table_schema = 'ruwana_db'
ORDER BY (data_length + index_length) DESC;

-- Conteo de registros por tabla
SELECT 
    'usuarios' as tabla, COUNT(*) as registros FROM usuarios
UNION ALL
SELECT 'voluntarios', COUNT(*) FROM voluntarios
UNION ALL
SELECT 'organizaciones', COUNT(*) FROM organizaciones
UNION ALL
SELECT 'eventos', COUNT(*) FROM eventos
UNION ALL
SELECT 'inscripciones', COUNT(*) FROM inscripciones;
```

## 🐛 Solución de Problemas

### Error: "Access denied"

```bash
# Verificar usuario y contraseña
mysql -u root -p

# Restablecer contraseña de root (si es necesario)
# mysqladmin -u root password nuevacontraseña
```

### Error: "Database already exists"

```sql
-- Eliminar base de datos existente
DROP DATABASE IF EXISTS ruwana_db;

-- Volver a ejecutar schema.sql
```

### Verificar Integridad Referencial

```sql
-- Verificar inscripciones sin voluntario
SELECT * FROM inscripciones i
LEFT JOIN voluntarios v ON i.voluntario_id = v.id
WHERE v.id IS NULL;

-- Verificar eventos sin organización
SELECT * FROM eventos e
LEFT JOIN organizaciones o ON e.organizacion_id = o.id
WHERE o.id IS NULL;
```

## 📞 Conexión desde Java

**Dependencia Maven (ya incluida en el proyecto):**

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
```

**String de conexión:**

```
jdbc:mysql://localhost:3306/ruwana_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
```

## 📝 Notas

- El esquema usa `utf8mb4` para soportar emojis y caracteres especiales
- Todas las fechas usan `DATETIME` para precisión
- Los IDs son `BIGINT` para soportar gran volumen de datos
- Las tablas usan `InnoDB` para transacciones ACID

## 🔄 Actualizaciones

Para aplicar cambios al esquema en producción, crear scripts de migración:

```sql
-- Ejemplo: migration_001_add_telefono_voluntarios.sql
ALTER TABLE voluntarios ADD COLUMN telefono VARCHAR(20) AFTER numero_documento;
```

---

**Última actualización:** 15 de octubre de 2025
