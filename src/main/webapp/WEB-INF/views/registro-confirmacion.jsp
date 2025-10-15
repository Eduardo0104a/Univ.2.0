<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro Exitoso - Ruwana</title>
    
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
        </div>
    </nav>

    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-lg-6">
                <div class="form-container text-center">
                    <!-- Success Icon -->
                    <div class="mb-4">
                        <i class="bi bi-check-circle-fill" style="font-size: 5rem; color: var(--success-color);"></i>
                    </div>

                    <h2 style="color: var(--primary-color);">¡Registro Exitoso!</h2>
                    <p class="subtitle mb-4">Tu solicitud ha sido recibida correctamente</p>

                    <div class="alert alert-info alert-custom text-start">
                        <h5><i class="bi bi-info-circle-fill"></i> ¿Qué sigue?</h5>
                        <ul class="mb-0">
                            <li class="mb-2">Tu cuenta será revisada por nuestro equipo</li>
                            <li class="mb-2">Verificaremos la información proporcionada</li>
                            <li class="mb-2">Te notificaremos por correo cuando tu cuenta sea aprobada</li>
                            <li>Este proceso puede tomar entre 24-48 horas hábiles</li>
                        </ul>
                    </div>

                    <div class="mt-4">
                        <a href="${pageContext.request.contextPath}/" class="btn btn-primary-ruwana">
                            <i class="bi bi-house-door"></i> Volver al Inicio
                        </a>
                    </div>

                    <hr class="my-4">

                    <p class="text-muted">
                        <i class="bi bi-envelope"></i> Si tienes dudas, contáctanos a: 
                        <a href="mailto:contacto@ruwana.org" class="link-ruwana">contacto@ruwana.org</a>
                    </p>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap 5 JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
