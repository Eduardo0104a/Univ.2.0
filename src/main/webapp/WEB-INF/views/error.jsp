<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - Ruwana</title>
    
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

    <!-- Error Content -->
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-lg-6 text-center">
                <i class="bi bi-exclamation-triangle" style="font-size: 5rem; color: var(--danger-color);"></i>
                <h1 class="mt-4" style="color: var(--primary-color);">¡Oops! Algo salió mal</h1>
                <p class="text-muted mb-4">
                    Lo sentimos, ocurrió un error inesperado. Por favor, intenta de nuevo más tarde.
                </p>
                
                <% if (request.getAttribute("error") != null) { %>
                    <div class="alert alert-error alert-custom text-start mb-4">
                        <strong>Detalle del error:</strong><br>
                        <%= request.getAttribute("error") %>
                    </div>
                <% } %>
                
                <div class="d-flex gap-3 justify-content-center">
                    <a href="${pageContext.request.contextPath}/" class="btn btn-primary-ruwana">
                        <i class="bi bi-house-door"></i> Ir al Inicio
                    </a>
                    <button onclick="history.back()" class="btn btn-outline-ruwana">
                        <i class="bi bi-arrow-left"></i> Volver Atrás
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap 5 JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
