<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro de Voluntario - Ruwana</title>
    
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-light">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">
                <i class="bi bi-heart-fill"></i> Ruwana
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/login">Iniciar Sesión</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Hero Section -->
    <div class="hero-section">
        <div class="container text-center">
            <h1><i class="bi bi-person-hearts"></i> Regístrate como Voluntario</h1>
            <p>Encuentra el voluntariado ideal para ti</p>
        </div>
    </div>

    <!-- Registration Form -->
    <div class="container py-5">
        <div class="form-container form-container-wide">
            <!-- Error Message -->
            <c:if test="${not empty error}">
                <div class="alert alert-error alert-custom">
                    <i class="bi bi-exclamation-triangle-fill"></i>
                    ${error}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/registro/voluntario" method="post" id="registroForm">
                <!-- Datos de Usuario -->
                <h4 class="mb-4" style="color: var(--primary-color);">
                    <i class="bi bi-person-circle"></i> Datos de Cuenta
                </h4>
                
                <div class="row">
                    <div class="col-md-4 mb-3">
                        <label for="nombres" class="form-label">Nombres*</label>
                        <input type="text" class="form-control" id="nombres" name="nombres" 
                               placeholder="Juan" required>
                    </div>
                    <div class="col-md-4 mb-3">
                        <label for="apellidoPaterno" class="form-label">Apellido Paterno*</label>
                        <input type="text" class="form-control" id="apellidoPaterno" name="apellidoPaterno" 
                               placeholder="Pérez" required>
                    </div>
                    <div class="col-md-4 mb-3">
                        <label for="apellidoMaterno" class="form-label">Apellido Materno</label>
                        <input type="text" class="form-control" id="apellidoMaterno" name="apellidoMaterno" 
                               placeholder="García">
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="email" class="form-label">
                            <i class="bi bi-envelope"></i> Correo Electrónico*
                        </label>
                        <input type="email" class="form-control" id="email" name="email" 
                               placeholder="tu@email.com" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="fechaNacimiento" class="form-label">
                            <i class="bi bi-calendar"></i> Fecha de Nacimiento
                        </label>
                        <input type="date" class="form-control" id="fechaNacimiento" name="fechaNacimiento">
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="password" class="form-label">
                            <i class="bi bi-lock"></i> Contraseña*
                        </label>
                        <input type="password" class="form-control" id="password" name="password" 
                               placeholder="Mínimo 8 caracteres" minlength="8" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="confirmPassword" class="form-label">
                            <i class="bi bi-lock-fill"></i> Confirmar Contraseña*
                        </label>
                        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" 
                               placeholder="Repite tu contraseña" required>
                    </div>
                </div>

                <hr class="my-4">

                <!-- Datos Personales -->
                <h4 class="mb-4" style="color: var(--primary-color);">
                    <i class="bi bi-person-vcard"></i> Datos Personales
                </h4>

                <div class="row">
                    <div class="col-md-4 mb-3">
                        <label for="generoId" class="form-label">Género</label>
                        <select class="form-select" id="generoId" name="generoId">
                            <option value="">Seleccionar...</option>
                            <c:forEach items="${generos}" var="genero">
                                <option value="${genero.id}">${genero.nombre}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-4 mb-3">
                        <label for="estadoCivilId" class="form-label">Estado Civil</label>
                        <select class="form-select" id="estadoCivilId" name="estadoCivilId">
                            <option value="">Seleccionar...</option>
                            <c:forEach items="${estadosCiviles}" var="estadoCivil">
                                <option value="${estadoCivil.id}">${estadoCivil.nombre}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-4 mb-3">
                        <label for="nacionalidad" class="form-label">Nacionalidad</label>
                        <input type="text" class="form-control" id="nacionalidad" name="nacionalidad" 
                               placeholder="Peruana">
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="tipoDocumentoId" class="form-label">Tipo de Documento</label>
                        <select class="form-select" id="tipoDocumentoId" name="tipoDocumentoId">
                            <option value="">Seleccionar...</option>
                            <c:forEach items="${tiposDocumento}" var="tipoDoc">
                                <option value="${tipoDoc.id}">${tipoDoc.nombre}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="numeroDocumento" class="form-label">Número de Documento</label>
                        <input type="text" class="form-control" id="numeroDocumento" name="numeroDocumento" 
                               placeholder="12345678">
                    </div>
                </div>

                <hr class="my-4">

                <!-- Datos Educativos -->
                <h4 class="mb-4" style="color: var(--primary-color);">
                    <i class="bi bi-mortarboard"></i> Datos Educativos
                </h4>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="gradoInstruccionId" class="form-label">Grado de Instrucción</label>
                        <select class="form-select" id="gradoInstruccionId" name="gradoInstruccionId">
                            <option value="">Seleccionar...</option>
                            <c:forEach items="${gradosInstruccion}" var="grado">
                                <option value="${grado.id}">${grado.nombre}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="centroEstudiosId" class="form-label">Tipo de Centro de Estudios</label>
                        <select class="form-select" id="centroEstudiosId" name="centroEstudiosId">
                            <option value="">Seleccionar...</option>
                            <c:forEach items="${centrosEstudios}" var="centro">
                                <option value="${centro.id}">${centro.nombre}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="nombreCentroEstudios" class="form-label">Nombre del Centro de Estudios</label>
                        <input type="text" class="form-control" id="nombreCentroEstudios" name="nombreCentroEstudios" 
                               placeholder="Universidad Nacional...">
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="correoInstitucional" class="form-label">Correo Institucional</label>
                        <input type="email" class="form-control" id="correoInstitucional" name="correoInstitucional" 
                               placeholder="estudiante@universidad.edu.pe">
                    </div>
                </div>

                <div class="mb-3">
                    <label for="carrera" class="form-label">Carrera / Especialidad</label>
                    <input type="text" class="form-control" id="carrera" name="carrera" 
                           placeholder="Ingeniería de Sistemas">
                </div>

                <hr class="my-4">

                <!-- Terms and Conditions -->
                <div class="mb-3 form-check">
                    <input type="checkbox" class="form-check-input" id="terms" required>
                    <label class="form-check-label" for="terms">
                        Acepto los <a href="#" class="link-ruwana">términos y condiciones</a> y la 
                        <a href="#" class="link-ruwana">política de privacidad</a>
                    </label>
                </div>

                <!-- Submit Button -->
                <button type="submit" class="btn btn-primary-ruwana">
                    <i class="bi bi-check-circle"></i> Registrarme
                </button>

                <div class="text-center mt-3">
                    <p>¿Ya tienes cuenta? 
                        <a href="${pageContext.request.contextPath}/login" class="link-ruwana">Iniciar Sesión</a>
                    </p>
                </div>
            </form>
        </div>
    </div>

    <!-- Bootstrap 5 JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- Custom JS -->
    <script>
        // Password validation
        document.getElementById('registroForm').addEventListener('submit', function(e) {
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            
            if (password !== confirmPassword) {
                e.preventDefault();
                alert('Las contraseñas no coinciden');
                return false;
            }
            
            if (password.length < 8) {
                e.preventDefault();
                alert('La contraseña debe tener al menos 8 caracteres');
                return false;
            }
        });
    </script>
</body>
</html>
