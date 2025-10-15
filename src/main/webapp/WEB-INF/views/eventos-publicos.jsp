<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Eventos de Voluntariado - Ruwana</title>
    
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
                        <a class="nav-link" href="${pageContext.request.contextPath}/">Inicio</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/eventos">Eventos</a>
                    </li>
                    <c:choose>
                        <c:when test="${not empty sessionScope.usuario}">
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/app/${sessionScope.usuario.rol.name().toLowerCase()}/dashboard">
                                    <i class="bi bi-person-circle"></i> Mi Panel
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/logout">Cerrar Sesión</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/login">Iniciar Sesión</a>
                            </li>
                            <li class="nav-item">
                                <a class="btn btn-primary-custom ms-2" href="${pageContext.request.contextPath}/registro/voluntario">
                                    Registrarse
                                </a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Hero Section -->
    <div class="hero-section">
        <div class="container text-center">
            <h1><i class="bi bi-calendar-heart"></i> Eventos de Voluntariado</h1>
            <p>Encuentra oportunidades para hacer la diferencia</p>
        </div>
    </div>

    <!-- Search & Filter Section -->
    <div class="container py-4">
        <div class="card-ruwana p-4 mb-4">
            <form method="get" action="${pageContext.request.contextPath}/eventos" class="row g-3">
                <div class="col-md-5">
                    <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-search"></i></span>
                        <input type="text" 
                               class="form-control" 
                               name="buscar" 
                               placeholder="Buscar por nombre..." 
                               value="${searchQuery}">
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-geo-alt"></i></span>
                        <input type="text" 
                               class="form-control" 
                               name="lugar" 
                               placeholder="Ubicación..." 
                               value="${lugar}">
                    </div>
                </div>
                <div class="col-md-3">
                    <button type="submit" class="btn btn-primary-ruwana w-100">
                        <i class="bi bi-search"></i> Buscar
                    </button>
                </div>
            </form>
        </div>

        <!-- Events Grid -->
        <c:choose>
            <c:when test="${not empty eventos}">
                <div class="row g-4">
                    <c:forEach items="${eventos}" var="evento">
                        <div class="col-lg-4 col-md-6">
                            <div class="card-ruwana h-100" style="overflow: hidden;">
                                <!-- Image Placeholder -->
                                <div style="height: 200px; background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-light) 100%); position: relative;">
                                    <div style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); text-align: center; color: white;">
                                        <i class="bi bi-calendar-event" style="font-size: 4rem; opacity: 0.3;"></i>
                                    </div>
                                    
                                    <!-- Status Badge -->
                                    <c:if test="${evento.cuposMaximos != null}">
                                        <div style="position: absolute; top: 15px; right: 15px;">
                                            <span class="badge" style="background-color: white; color: var(--primary-color); padding: 8px 12px;">
                                                <i class="bi bi-people"></i> ${evento.cuposDisponibles}/${evento.cuposMaximos} cupos
                                            </span>
                                        </div>
                                    </c:if>
                                </div>
                                
                                <div class="p-4">
                                    <h5 class="mb-3" style="color: var(--primary-color);">
                                        ${evento.nombre}
                                    </h5>
                                    
                                    <div class="mb-3">
                                        <p class="mb-2">
                                            <i class="bi bi-building text-muted"></i>
                                            <small class="text-muted">${evento.organizacion.nombreOrganizacion}</small>
                                        </p>
                                        <p class="mb-2">
                                            <i class="bi bi-calendar text-muted"></i>
                                            <small class="text-muted">
                                                ${evento.fechaInicio}
                                                <c:if test="${evento.fechaInicio != evento.fechaFin}">
                                                    - ${evento.fechaFin}
                                                </c:if>
                                            </small>
                                        </p>
                                        <p class="mb-2">
                                            <i class="bi bi-geo-alt text-muted"></i>
                                            <small class="text-muted">${evento.lugar}</small>
                                        </p>
                                        <c:if test="${not empty evento.horario}">
                                            <p class="mb-0">
                                                <i class="bi bi-clock text-muted"></i>
                                                <small class="text-muted">${evento.horario}</small>
                                            </p>
                                        </c:if>
                                    </div>
                                    
                                    <a href="${pageContext.request.contextPath}/eventos/${evento.id}" 
                                       class="btn btn-primary-ruwana w-100">
                                        <i class="bi bi-eye"></i> Ver Detalles
                                    </a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="text-center py-5">
                    <i class="bi bi-calendar-x" style="font-size: 5rem; color: var(--text-light);"></i>
                    <h3 class="mt-4" style="color: var(--text-light);">No hay eventos disponibles</h3>
                    <p class="text-muted">Intenta con otros términos de búsqueda o vuelve más tarde</p>
                    <a href="${pageContext.request.contextPath}/eventos" class="btn btn-outline-ruwana mt-3">
                        <i class="bi bi-arrow-clockwise"></i> Ver Todos los Eventos
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- Call to Action -->
    <c:if test="${empty sessionScope.usuario}">
        <div class="py-5" style="background-color: var(--background-cream);">
            <div class="container text-center">
                <h3 style="color: var(--primary-color); margin-bottom: 1rem;">
                    ¿Quieres participar?
                </h3>
                <p class="text-muted mb-4">Regístrate para inscribirte en eventos y recibir certificados</p>
                <a href="${pageContext.request.contextPath}/registro/voluntario" class="btn btn-primary-ruwana btn-lg">
                    <i class="bi bi-person-plus"></i> Crear Cuenta Gratis
                </a>
            </div>
        </div>
    </c:if>

    <!-- Bootstrap 5 JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
