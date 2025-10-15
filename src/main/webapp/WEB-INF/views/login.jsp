<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión - Ruwana</title>
    
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
                        <a class="nav-link" href="${pageContext.request.contextPath}/">Inicio</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/eventos-publicos">Eventos</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/sobre-nosotros">Sobre Nosotros</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Login Section -->
    <div class="container py-5">
        <div class="row align-items-center">
            <!-- Left Side - Image/Hero -->
            <div class="col-lg-6 d-none d-lg-block">
                <div class="text-center">
                    <img src="https://images.unsplash.com/photo-1559027615-cd4628902d4a?w=500&h=500&fit=crop" 
                         alt="Voluntariado" class="circle-image">
                    <h3 class="mt-4" style="color: var(--primary-color); font-weight: 700;">
                        ¡Te damos la bienvenida!
                    </h3>
                    <p class="text-muted">Encuentra tu forma de ayudar</p>
                </div>
            </div>

            <!-- Right Side - Login Form -->
            <div class="col-lg-6">
                <div class="form-container">
                    <h2>Iniciar Sesión</h2>
                    <p class="subtitle">Ingresa a tu cuenta para continuar</p>

                    <!-- Success Message -->
                    <c:if test="${param.registro == 'success'}">
                        <div class="alert alert-success alert-custom">
                            <i class="bi bi-check-circle-fill"></i>
                            ¡Registro exitoso! Por favor inicia sesión.
                        </div>
                    </c:if>

                    <c:if test="${param.logout == 'success'}">
                        <div class="alert alert-info alert-custom">
                            <i class="bi bi-info-circle-fill"></i>
                            Has cerrado sesión correctamente.
                        </div>
                    </c:if>

                    <!-- Error Message -->
                    <c:if test="${not empty error}">
                        <div class="alert alert-error alert-custom">
                            <i class="bi bi-exclamation-triangle-fill"></i>
                            ${error}
                        </div>
                    </c:if>

                    <!-- Login Form -->
                    <form action="${pageContext.request.contextPath}/login" method="post">
                        <div class="mb-3">
                            <label for="email" class="form-label">
                                <i class="bi bi-envelope"></i> Correo electrónico*
                            </label>
                            <input type="email" 
                                   class="form-control" 
                                   id="email" 
                                   name="email" 
                                   placeholder="tu@email.com"
                                   value="${email}"
                                   required>
                        </div>

                        <div class="mb-3">
                            <label for="password" class="form-label">
                                <i class="bi bi-lock"></i> Contraseña*
                            </label>
                            <div class="input-group">
                                <input type="password" 
                                       class="form-control" 
                                       id="password" 
                                       name="password" 
                                       placeholder="••••••••"
                                       required>
                                <button class="btn btn-outline-secondary" type="button" id="togglePassword">
                                    <i class="bi bi-eye" id="eyeIcon"></i>
                                </button>
                            </div>
                        </div>

                        <div class="mb-3 form-check">
                            <input type="checkbox" class="form-check-input" id="remember">
                            <label class="form-check-label" for="remember">
                                Recordarme
                            </label>
                        </div>

                        <input type="hidden" name="redirect" value="${param.redirect}">

                        <button type="submit" class="btn btn-primary-ruwana">
                            <i class="bi bi-box-arrow-in-right"></i> Ingresar
                        </button>
                    </form>

                    <div class="text-center mt-4">
                        <a href="#" class="link-ruwana">¿Olvidaste tu contraseña?</a>
                    </div>

                    <hr class="my-4">

                    <div class="text-center">
                        <p class="mb-2">¿No tienes cuenta?</p>
                        <a href="${pageContext.request.contextPath}/registro/voluntario" 
                           class="btn btn-outline-ruwana me-2 mb-2">
                            <i class="bi bi-person-plus"></i> Soy Voluntario
                        </a>
                        <a href="${pageContext.request.contextPath}/registro/organizacion" 
                           class="btn btn-outline-ruwana mb-2">
                            <i class="bi bi-buildings"></i> Soy Organización
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap 5 JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- Custom JS -->
    <script>
        // Toggle password visibility
        document.getElementById('togglePassword').addEventListener('click', function() {
            const password = document.getElementById('password');
            const eyeIcon = document.getElementById('eyeIcon');
            
            if (password.type === 'password') {
                password.type = 'text';
                eyeIcon.classList.remove('bi-eye');
                eyeIcon.classList.add('bi-eye-slash');
            } else {
                password.type = 'password';
                eyeIcon.classList.remove('bi-eye-slash');
                eyeIcon.classList.add('bi-eye');
            }
        });
    </script>
</body>
</html>
