<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mi Panel - Voluntario - Ruwana</title>
    
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
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">
                <img src="${pageContext.request.contextPath}/images/logos/ruwana.svg" alt="Ruwana" class="navbar-logo">
                Ruwana
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/app/voluntario/dashboard">
                            <i class="bi bi-speedometer2"></i> Dashboard
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/app/voluntario/eventos">
                            <i class="bi bi-calendar-event"></i> Eventos
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/app/voluntario/inscripciones">
                            <i class="bi bi-list-check"></i> Mis Inscripciones
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/app/voluntario/certificados">
                            <i class="bi bi-award"></i> Certificados
                        </a>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" 
                           data-bs-toggle="dropdown">
                            <i class="bi bi-person-circle"></i> ${usuario.nombreCompleto}
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li>
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/app/voluntario/perfil">
                                    <i class="bi bi-person"></i> Mi Perfil
                                </a>
                            </li>
                            <li><hr class="dropdown-divider"></li>
                            <li>
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/logout">
                                    <i class="bi bi-box-arrow-right"></i> Cerrar Sesión
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Main Content -->
    <div class="container-fluid py-4">
        <!-- Welcome Header -->
        <div class="row mb-4">
            <div class="col-12">
                <div class="card-ruwana p-4" style="background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-dark) 100%); color: white;">
                    <h2 class="mb-2">
                        <i class="bi bi-hand-wave"></i> ¡Bienvenido, ${usuario.nombres}!
                    </h2>
                    <p class="mb-0 opacity-75">Encuentra tu próximo proyecto de voluntariado</p>
                </div>
            </div>
        </div>

        <!-- Statistics Cards -->
        <div class="row g-3 mb-4">
            <div class="col-md-3">
                <div class="card-ruwana p-3">
                    <div class="d-flex align-items-center">
                        <div class="flex-shrink-0">
                            <div class="rounded-circle p-3" style="background-color: #FFE5E9;">
                                <i class="bi bi-calendar-check" style="font-size: 2rem; color: var(--primary-color);"></i>
                            </div>
                        </div>
                        <div class="flex-grow-1 ms-3">
                            <h6 class="text-muted mb-1">Eventos Inscritos</h6>
                            <h3 class="mb-0" style="color: var(--primary-color);">${stats.totalInscripciones}</h3>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-3">
                <div class="card-ruwana p-3">
                    <div class="d-flex align-items-center">
                        <div class="flex-shrink-0">
                            <div class="rounded-circle p-3" style="background-color: #E8F5E9;">
                                <i class="bi bi-check-circle" style="font-size: 2rem; color: #4CAF50;"></i>
                            </div>
                        </div>
                        <div class="flex-grow-1 ms-3">
                            <h6 class="text-muted mb-1">Asistencias</h6>
                            <h3 class="mb-0" style="color: #4CAF50;">${stats.totalAsistencias}</h3>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-3">
                <div class="card-ruwana p-3">
                    <div class="d-flex align-items-center">
                        <div class="flex-shrink-0">
                            <div class="rounded-circle p-3" style="background-color: #FFF3E0;">
                                <i class="bi bi-award" style="font-size: 2rem; color: #FF9800;"></i>
                            </div>
                        </div>
                        <div class="flex-grow-1 ms-3">
                            <h6 class="text-muted mb-1">Certificados</h6>
                            <h3 class="mb-0" style="color: #FF9800;">0</h3>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-3">
                <div class="card-ruwana p-3">
                    <div class="d-flex align-items-center">
                        <div class="flex-shrink-0">
                            <div class="rounded-circle p-3" style="background-color: #E3F2FD;">
                                <i class="bi bi-graph-up" style="font-size: 2rem; color: #2196F3;"></i>
                            </div>
                        </div>
                        <div class="flex-grow-1 ms-3">
                            <h6 class="text-muted mb-1">Tasa Asistencia</h6>
                            <h3 class="mb-0" style="color: #2196F3;">
                                <c:choose>
                                    <c:when test="${stats.totalInscripciones > 0}">
                                        ${stats.tasaAsistencia}%
                                    </c:when>
                                    <c:otherwise>
                                        0%
                                    </c:otherwise>
                                </c:choose>
                            </h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Main Content Row -->
        <div class="row">
            <!-- Left Column - Available Events -->
            <div class="col-lg-8">
                <div class="card-ruwana p-4 mb-4">
                    <h4 class="mb-4" style="color: var(--primary-color);">
                        <i class="bi bi-calendar-event"></i> Eventos Disponibles
                    </h4>
                    
                    <c:choose>
                        <c:when test="${not empty eventosDisponibles}">
                            <div class="row g-3">
                                <c:forEach items="${eventosDisponibles}" var="evento" varStatus="status">
                                    <c:if test="${status.index < 6}">
                                        <div class="col-md-6">
                                            <div class="card h-100 border-0 shadow-sm">
                                                <div class="card-body">
                                                    <h5 class="card-title">${evento.nombre}</h5>
                                                    <p class="card-text text-muted">
                                                        <small>
                                                            <i class="bi bi-buildings"></i> ${evento.organizacion.nombreOrganizacion}
                                                        </small>
                                                    </p>
                                                    <p class="card-text">
                                                        <i class="bi bi-calendar"></i> ${evento.fechaInicio}
                                                        <br>
                                                        <i class="bi bi-geo-alt"></i> ${evento.lugar}
                                                    </p>
                                                    <a href="${pageContext.request.contextPath}/app/voluntario/eventos/${evento.id}" 
                                                       class="btn btn-outline-ruwana btn-sm">
                                                        Ver Detalles
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                            
                            <div class="text-center mt-4">
                                <a href="${pageContext.request.contextPath}/app/voluntario/eventos" 
                                   class="link-ruwana">
                                    Ver todos los eventos <i class="bi bi-arrow-right"></i>
                                </a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="text-center py-5">
                                <i class="bi bi-calendar-x" style="font-size: 4rem; color: var(--text-light);"></i>
                                <p class="text-muted mt-3">No hay eventos disponibles en este momento</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <!-- Right Column - Quick Actions & Info -->
            <div class="col-lg-4">
                <!-- Quick Actions -->
                <div class="card-ruwana p-4 mb-4">
                    <h5 class="mb-3" style="color: var(--primary-color);">
                        <i class="bi bi-lightning"></i> Acciones Rápidas
                    </h5>
                    <div class="d-grid gap-2">
                        <a href="${pageContext.request.contextPath}/app/voluntario/eventos" 
                           class="btn btn-primary-ruwana">
                            <i class="bi bi-search"></i> Buscar Eventos
                        </a>
                        <a href="${pageContext.request.contextPath}/app/voluntario/inscripciones" 
                           class="btn btn-outline-ruwana">
                            <i class="bi bi-list-check"></i> Mis Inscripciones
                        </a>
                        <a href="${pageContext.request.contextPath}/app/voluntario/perfil" 
                           class="btn btn-outline-ruwana">
                            <i class="bi bi-person"></i> Editar Perfil
                        </a>
                    </div>
                </div>

                <!-- Profile Completion -->
                <div class="card-ruwana p-4 mb-4">
                    <h6 class="mb-3" style="color: var(--primary-color);">Completa tu perfil</h6>
                    <div class="progress mb-2" style="height: 20px;">
                        <div class="progress-bar" role="progressbar" style="width: 75%; background-color: var(--primary-color);">
                            75%
                        </div>
                    </div>
                    <p class="small text-muted mb-3">
                        Completa tu perfil para acceder a más oportunidades
                    </p>
                    <a href="${pageContext.request.contextPath}/app/voluntario/perfil" 
                       class="btn btn-sm btn-outline-ruwana w-100">
                        Completar Ahora
                    </a>
                </div>

                <!-- Tips -->
                <div class="card-ruwana p-4" style="background-color: var(--background-cream);">
                    <h6 class="mb-3" style="color: var(--primary-color);">
                        <i class="bi bi-lightbulb"></i> Tip del día
                    </h6>
                    <p class="small mb-0">
                        Mantén tu perfil actualizado y revisa regularmente los nuevos eventos disponibles para no perderte ninguna oportunidad.
                    </p>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap 5 JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
