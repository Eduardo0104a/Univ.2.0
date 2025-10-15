# Proyecto Ruwana

Sistema de Registro de Eventos de Voluntariado - Aplicación web Jakarta EE 10 con MySQL.

## 📖 Descripción

Ruwana es una plataforma web para la gestión y registro de eventos de voluntariado que conecta organizaciones sociales con voluntarios comprometidos. El sistema permite:

- ✅ Registro y gestión de organizaciones
- ✅ Creación y aprobación de eventos
- ✅ Inscripción de voluntarios a eventos
- ✅ Gestión de donaciones
- ✅ Generación de certificados
- ✅ Sistema de notificaciones por email

## 🚀 Inicio Rápido

### Requisitos
- Java 11 o superior (instalado: Java 21)
- Maven o Maven Daemon (mvnd)
- Apache Tomcat 10.1+ (instalado en: `C:\apache-tomcat-10.1.12`)
- MySQL 8.0+ (requerido)

### Primera ejecución

1. **Configurar la base de datos:**
   ```bash
   # Instalar MySQL 8.0+
   # Ejecutar script de base de datos
   mysql -u root -p < database/schema.sql
   
   # (Opcional) Cargar datos de prueba
   mysql -u root -p ruwana_db < database/test_data.sql
   ```
   
   Ver documentación completa en: [`database/README.md`](database/README.md)

2. **Compilar el proyecto:**
   ```powershell
   mvnd clean package
   ```

3. **Desplegar en Tomcat:**
   ```powershell
   .\deploy.ps1
   ```

4. **Acceder a la aplicación:**
   - http://localhost:8080/ruwana/
   - API REST: http://localhost:8080/ruwana/resources/jakartaee10

## 📜 Scripts de Despliegue

### `deploy.ps1` - Despliegue Rápido
Compila y copia el WAR a Tomcat. Tomcat detecta el cambio automáticamente.

```powershell
.\deploy.ps1
```

**Cuándo usar:** Para cambios menores (JSP, HTML, CSS). No requiere reiniciar Tomcat.

---

### `redeploy.ps1` - Re-despliegue Completo
Detiene Tomcat, limpia el despliegue anterior, copia el nuevo WAR y reinicia Tomcat.

```powershell
.\redeploy.ps1
```

**Cuándo usar:** 
- Cambios en clases Java
- Cambios en configuración (web.xml, beans.xml)
- Cuando el despliegue automático no funciona
- Errores de despliegue

## 🛠️ Comandos Útiles

### Compilar sin desplegar
```powershell
mvnd clean package
```

### Iniciar Tomcat
```powershell
C:\apache-tomcat-10.1.12\bin\startup.bat
```

### Detener Tomcat
```powershell
C:\apache-tomcat-10.1.12\bin\shutdown.bat
```

### Ver logs de Tomcat
```powershell
Get-Content C:\apache-tomcat-10.1.12\logs\catalina.*.log -Tail 50
```

## 📁 Estructura del Proyecto

```
ruwana/
├── database/                    # Scripts de base de datos
│   ├── schema.sql              # Esquema completo (tablas, vistas, SP)
│   ├── test_data.sql           # Datos de prueba
│   ├── queries.sql             # Consultas útiles
│   └── README.md               # Documentación de BD
├── src/
│   └── main/
│       ├── java/
│       │   └── com/dwi/ruwana/
│       │       ├── JakartaRestConfiguration.java
│       │       └── resources/
│       │           └── JakartaEE10Resource.java
│       ├── resources/
│       │   └── META-INF/
│       │       └── persistence.xml
│       └── webapp/
│           ├── index.jsp
│           ├── META-INF/
│           │   └── context.xml
│           └── WEB-INF/
│               ├── beans.xml
│               └── web.xml
├── pom.xml                      # Configuración Maven
├── deploy.ps1                   # Script de despliegue rápido
├── redeploy.ps1                 # Script de re-despliegue completo
└── README.md                    # Esta documentación
```

## 🗄️ Base de Datos

El proyecto utiliza MySQL 8.0+ con las siguientes tablas principales:

- **usuarios** - Tabla base para todos los tipos de usuario
- **voluntarios** - Datos específicos de voluntarios
- **organizaciones** - Datos de organizaciones sociales
- **eventos** - Eventos de voluntariado
- **inscripciones** - Relación N:M entre voluntarios y eventos
- **donaciones** - Registro de donaciones
- **certificaciones** - Certificados de participación
- **notificaciones** - Sistema de notificaciones

Para más detalles, consultar [`database/README.md`](database/README.md)

## 🔧 Tecnologías

### Backend
- **Jakarta EE 10.0.0** - Plataforma empresarial
- **Java 11** - Versión de compilación (compatible con Java 21)
- **JPA/Hibernate 6.4** - ORM para persistencia
- **MySQL 8.3** - Base de datos relacional
- **Apache Tomcat 10.1** - Servidor de aplicaciones

### Librerías
- **BCrypt** - Hashing de contraseñas
- **JSTL 3.0** - JSP Standard Tag Library
- **Gson** - Procesamiento JSON
- **JavaMail** - Notificaciones por email
- **SLF4J** - Logging

### Herramientas
- **Maven 3.9.11** (Maven Daemon)
- **Git** - Control de versiones

## 📝 Endpoints Disponibles

| Método | URL | Descripción |
|--------|-----|-------------|
| GET | `/` | Página principal (index.jsp) |
| GET | `/resources/jakartaee10` | Endpoint de prueba REST |

## 🐛 Solución de Problemas

### Error: "Failed to start component"
Ejecutar el re-despliegue completo:
```powershell
.\redeploy.ps1
```

### Puerto 8080 ocupado
Verificar si Tomcat ya está corriendo:
```powershell
netstat -ano | findstr :8080
```

### Cambios no se reflejan
1. Forzar limpieza y recompilación:
   ```powershell
   mvnd clean package
   ```
2. Usar `redeploy.ps1` en lugar de `deploy.ps1`
3. Limpiar caché del navegador (Ctrl + Shift + Del)

## 📦 Compilación del WAR

El proyecto se compila como `ruwana.war` (configurado en `pom.xml`).

Ubicación del WAR generado:
```
target/ruwana.war
```

## 🔗 URLs Útiles

- **Aplicación:** http://localhost:8080/ruwana/
- **Tomcat Manager:** http://localhost:8080/manager/
- **Tomcat Host Manager:** http://localhost:8080/host-manager/
