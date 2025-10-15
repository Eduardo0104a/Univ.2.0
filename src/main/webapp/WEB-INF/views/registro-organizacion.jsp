<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro de Organización - Ruwana</title>
    
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
                <img src="${pageContext.request.contextPath}/images/logos/ruwana.svg" alt="Ruwana" class="navbar-logo">
                Ruwana
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
            <h1><i class="bi bi-buildings"></i> Regístrate como Organización</h1>
            <p>Conecta con voluntarios comprometidos</p>
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

            <form action="${pageContext.request.contextPath}/registro/organizacion" method="post" id="registroOrgForm">
                <!-- Datos del Responsable -->
                <h4 class="mb-4" style="color: var(--primary-color);">
                    <i class="bi bi-person-badge"></i> Datos del Responsable
                </h4>
                
                <div class="row">
                    <div class="col-md-4 mb-3">
                        <label for="nombres" class="form-label">Nombres*</label>
                        <input type="text" class="form-control" id="nombres" name="nombres" required>
                    </div>
                    <div class="col-md-4 mb-3">
                        <label for="apellidoPaterno" class="form-label">Apellido Paterno*</label>
                        <input type="text" class="form-control" id="apellidoPaterno" name="apellidoPaterno" required>
                    </div>
                    <div class="col-md-4 mb-3">
                        <label for="apellidoMaterno" class="form-label">Apellido Materno</label>
                        <input type="text" class="form-control" id="apellidoMaterno" name="apellidoMaterno">
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="email" class="form-label">
                            <i class="bi bi-envelope"></i> Correo Electrónico*
                        </label>
                        <input type="email" class="form-control" id="email" name="email" 
                               placeholder="contacto@organizacion.org" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="contactoPrincipal" class="form-label">
                            <i class="bi bi-person"></i> Contacto Principal
                        </label>
                        <input type="text" class="form-control" id="contactoPrincipal" name="contactoPrincipal" 
                               placeholder="Nombre del contacto">
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="password" class="form-label">
                            <i class="bi bi-lock"></i> Contraseña*
                        </label>
                        <input type="password" class="form-control" id="password" name="password" 
                               minlength="8" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="confirmPassword" class="form-label">
                            <i class="bi bi-lock-fill"></i> Confirmar Contraseña*
                        </label>
                        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                    </div>
                </div>

                <hr class="my-4">

                <!-- Datos de la Organización -->
                <h4 class="mb-4" style="color: var(--primary-color);">
                    <i class="bi bi-buildings-check"></i> Datos de la Organización
                </h4>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="nombreOrganizacion" class="form-label">Nombre de la Organización*</label>
                        <input type="text" class="form-control" id="nombreOrganizacion" name="nombreOrganizacion" 
                               placeholder="Fundación Ayuda Perú" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="pais" class="form-label">País*</label>
                        <input type="text" class="form-control" id="pais" name="pais" 
                               value="Perú" required>
                    </div>
                </div>

                <div class="mb-3">
                    <label for="direccion" class="form-label">
                        <i class="bi bi-geo-alt"></i> Dirección
                    </label>
                    <textarea class="form-control" id="direccion" name="direccion" rows="2" 
                              placeholder="Dirección completa de la organización"></textarea>
                </div>

                <div class="row">
                    <div class="col-md-4 mb-3">
                        <label for="telefono" class="form-label">
                            <i class="bi bi-telephone"></i> Teléfono
                        </label>
                        <input type="tel" class="form-control" id="telefono" name="telefono" 
                               placeholder="(01) 234-5678">
                    </div>
                    <div class="col-md-4 mb-3">
                        <label for="celular" class="form-label">
                            <i class="bi bi-phone"></i> Celular
                        </label>
                        <input type="tel" class="form-control" id="celular" name="celular" 
                               placeholder="987 654 321">
                    </div>
                    <div class="col-md-4 mb-3">
                        <label for="tipoDocumentoId" class="form-label">Tipo de Documento</label>
                        <select class="form-select" id="tipoDocumentoId" name="tipoDocumentoId">
                            <option value="">Seleccionar...</option>
                            <c:forEach items="${tiposDocumento}" var="tipoDoc">
                                <option value="${tipoDoc.id}">${tipoDoc.nombre}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="numeroDocumento" class="form-label">Número de Documento</label>
                        <input type="text" class="form-control" id="numeroDocumento" name="numeroDocumento" 
                               placeholder="20123456789">
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="razonSocial" class="form-label">Razón Social</label>
                        <input type="text" class="form-control" id="razonSocial" name="razonSocial" 
                               placeholder="Fundación Ayuda Perú S.A.C.">
                    </div>
                </div>

                <hr class="my-4">

                <!-- Información Legal -->
                <h4 class="mb-4" style="color: var(--primary-color);">
                    <i class="bi bi-file-earmark-text"></i> Información Legal
                </h4>

                <div class="mb-3 form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="constituidaLegalmente" 
                           name="constituidaLegalmente" value="true">
                    <label class="form-check-label" for="constituidaLegalmente">
                        <strong>¿Tu organización está constituida legalmente?</strong>
                    </label>
                </div>

                <hr class="my-4">

                <!-- Motivación -->
                <h4 class="mb-4" style="color: var(--primary-color);">
                    <i class="bi bi-chat-heart"></i> Cuéntanos más
                </h4>

                <div class="mb-3">
                    <label for="motivacion" class="form-label">¿Por qué quieres ser parte de Ruwana?</label>
                    <textarea class="form-control" id="motivacion" name="motivacion" rows="4" 
                              placeholder="Comparte tu motivación y objetivos..."></textarea>
                </div>

                <div class="mb-3">
                    <label for="descripcionBeneficiarios" class="form-label">Cuéntanos sobre tus beneficiarios</label>
                    <textarea class="form-control" id="descripcionBeneficiarios" name="descripcionBeneficiarios" rows="4" 
                              placeholder="Describe a quiénes ayuda tu organización..."></textarea>
                </div>

                <hr class="my-4">

                <!-- Terms -->
                <div class="mb-3 form-check">
                    <input type="checkbox" class="form-check-input" id="terms" required>
                    <label class="form-check-label" for="terms">
                        Acepto los <a href="#" class="link-ruwana">términos y condiciones</a> y la 
                        <a href="#" class="link-ruwana">política de privacidad</a>
                    </label>
                </div>

                <!-- Info Box -->
                <div class="alert alert-info alert-custom">
                    <i class="bi bi-info-circle-fill"></i>
                    <strong>Nota:</strong> Tu cuenta será revisada por un administrador antes de ser activada. 
                    Recibirás un correo cuando tu cuenta sea aprobada.
                </div>

                <!-- Submit Button -->
                <button type="submit" class="btn btn-primary-ruwana">
                    <i class="bi bi-check-circle"></i> Registrar Organización
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
        document.getElementById('registroOrgForm').addEventListener('submit', function(e) {
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
