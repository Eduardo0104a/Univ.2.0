<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel Administrador - Ruwana</title>
    
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
                <i class="bi bi-heart-fill"></i> Ruwana Admin
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/app/admin/dashboard">
                            <i class="bi bi-speedometer2"></i> Dashboard
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/app/admin/organizaciones">
                            <i class="bi bi-building"></i> Organizaciones
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/app/admin/eventos">
                            <i class="bi bi-calendar-event"></i> Eventos
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/app/admin/voluntarios">
                            <i class="bi bi-people"></i> Voluntarios
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/app/admin/reportes">
                            <i class="bi bi-graph-up"></i> Reportes
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
                        <i class="bi bi-shield-check"></i> Panel de Administración
                    </h2>
                    <p class="mb-0 opacity-75">Gestiona organizaciones, eventos y usuarios del sistema</p>
                </div>
            </div>
        </div>

        <!-- Statistics Cards -->
        <div class="row g-3 mb-4">
            <div class="col-md-3">
                <div class="card-ruwana p-3">
                    <div class="d-flex align-items-center">
                        <div class="flex-shrink-0">
                            <div class="rounded-circle p-3" style="background-color: #E3F2FD;">
                                <i class="bi bi-building" style="font-size: 2rem; color: #2196F3;"></i>
                            </div>
                        </div>
                        <div class="flex-grow-1 ms-3">
                            <h6 class="text-muted mb-1">Organizaciones</h6>
                            <h3 class="mb-0" style="color: #2196F3;">${totalOrganizaciones}</h3>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-3">
                <div class="card-ruwana p-3">
                    <div class="d-flex align-items-center">
                        <div class="flex-shrink-0">
                            <div class="rounded-circle p-3" style="background-color: #E8F5E9;">
                                <i class="bi bi-people" style="font-size: 2rem; color: #4CAF50;"></i>
                            </div>
                        </div>
                        <div class="flex-grow-1 ms-3">
                            <h6 class="text-muted mb-1">Voluntarios</h6>
                            <h3 class="mb-0" style="color: #4CAF50;">${totalVoluntarios}</h3>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-3">
                <div class="card-ruwana p-3">
                    <div class="d-flex align-items-center">
                        <div class="flex-shrink-0">
                            <div class="rounded-circle p-3" style="background-color: #FFE5E9;">
                                <i class="bi bi-calendar-check" style="font-size: 2rem; color: var(--primary-color);"></i>
                            </div>
                        </div>
                        <div class="flex-grow-1 ms-3">
                            <h6 class="text-muted mb-1">Eventos Aprobados</h6>
                            <h3 class="mb-0" style="color: var(--primary-color);">${totalEventos}</h3>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-3">
                <div class="card-ruwana p-3">
                    <div class="d-flex align-items-center">
                        <div class="flex-shrink-0">
                            <div class="rounded-circle p-3" style="background-color: #FFF3E0;">
                                <i class="bi bi-hourglass-split" style="font-size: 2rem; color: #FF9800;"></i>
                            </div>
                        </div>
                        <div class="flex-grow-1 ms-3">
                            <h6 class="text-muted mb-1">Pendientes</h6>
                            <h3 class="mb-0" style="color: #FF9800;">
                                ${organizacionesPendientes.size() + eventosPendientes.size()}
                            </h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Main Content Row -->
        <div class="row">
            <!-- Left Column - Pending Organizations -->
            <div class="col-lg-6">
                <div class="card-ruwana p-4 mb-4">
                    <h4 class="mb-4" style="color: var(--primary-color);">
                        <i class="bi bi-building-exclamation"></i> Organizaciones Pendientes
                        <span class="badge bg-warning ms-2">${organizacionesPendientes.size()}</span>
                    </h4>
                    
                    <c:choose>
                        <c:when test="${not empty organizacionesPendientes}">
                            <div class="list-group">
                                <c:forEach items="${organizacionesPendientes}" var="org" varStatus="status">
                                    <c:if test="${status.index < 5}">
                                        <div class="list-group-item">
                                            <div class="d-flex justify-content-between align-items-start">
                                                <div class="flex-grow-1">
                                                    <h6 class="mb-1">${org.nombreOrganizacion}</h6>
                                                    <p class="mb-1 small text-muted">
                                                        <i class="bi bi-envelope"></i> ${org.usuario.email}
                                                    </p>
                                                    <p class="mb-0 small text-muted">
                                                        <i class="bi bi-calendar"></i> ${org.fechaRegistro}
                                                    </p>
                                                </div>
                                                <div>
                                                    <a href="${pageContext.request.contextPath}/app/admin/organizaciones/${org.id}" 
                                                       class="btn btn-sm btn-primary-ruwana">
                                                        Revisar
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                            
                            <div class="text-center mt-3">
                                <a href="${pageContext.request.contextPath}/app/admin/organizaciones?estado=PENDIENTE" 
                                   class="link-ruwana">
                                    Ver todas <i class="bi bi-arrow-right"></i>
                                </a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="text-center py-5">
                                <i class="bi bi-check-circle" style="font-size: 4rem; color: var(--success-color);"></i>
                                <p class="text-muted mt-3">No hay organizaciones pendientes de aprobación</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <!-- Right Column - Pending Events -->
            <div class="col-lg-6">
                <div class="card-ruwana p-4 mb-4">
                    <h4 class="mb-4" style="color: var(--primary-color);">
                        <i class="bi bi-calendar-event"></i> Eventos Pendientes
                        <span class="badge bg-warning ms-2">${eventosPendientes.size()}</span>
                    </h4>
                    
                    <c:choose>
                        <c:when test="${not empty eventosPendientes}">
                            <div class="list-group">
                                <c:forEach items="${eventosPendientes}" var="evento" varStatus="status">
                                    <c:if test="${status.index < 5}">
                                        <div class="list-group-item">
                                            <div class="d-flex justify-content-between align-items-start">
                                                <div class="flex-grow-1">
                                                    <h6 class="mb-1">${evento.nombre}</h6>
                                                    <p class="mb-1 small text-muted">
                                                        <i class="bi bi-building"></i> ${evento.organizacion.nombreOrganizacion}
                                                    </p>
                                                    <p class="mb-0 small text-muted">
                                                        <i class="bi bi-calendar"></i> ${evento.fechaInicio} - ${evento.fechaFin}
                                                    </p>
                                                </div>
                                                <div>
                                                    <a href="${pageContext.request.contextPath}/app/admin/eventos/${evento.id}" 
                                                       class="btn btn-sm btn-primary-ruwana">
                                                        Revisar
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                            
                            <div class="text-center mt-3">
                                <a href="${pageContext.request.contextPath}/app/admin/eventos?estado=PENDIENTE" 
                                   class="link-ruwana">
                                    Ver todos <i class="bi bi-arrow-right"></i>
                                </a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="text-center py-5">
                                <i class="bi bi-check-circle" style="font-size: 4rem; color: var(--success-color);"></i>
                                <p class="text-muted mt-3">No hay eventos pendientes de aprobación</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <!-- Quick Actions -->
        <div class="row">
            <div class="col-12">
                <div class="card-ruwana p-4">
                    <h5 class="mb-3" style="color: var(--primary-color);">
                        <i class="bi bi-lightning"></i> Acciones Rápidas
                    </h5>
                    <div class="row g-3">
                        <div class="col-md-3">
                            <a href="${pageContext.request.contextPath}/app/admin/organizaciones" 
                               class="btn btn-outline-ruwana w-100">
                                <i class="bi bi-building"></i> Ver Organizaciones
                            </a>
                        </div>
                        <div class="col-md-3">
                            <a href="${pageContext.request.contextPath}/app/admin/eventos" 
                               class="btn btn-outline-ruwana w-100">
                                <i class="bi bi-calendar-event"></i> Ver Eventos
                            </a>
                        </div>
                        <div class="col-md-3">
                            <a href="${pageContext.request.contextPath}/app/admin/voluntarios" 
                               class="btn btn-outline-ruwana w-100">
                                <i class="bi bi-people"></i> Ver Voluntarios
                            </a>
                        </div>
                        <div class="col-md-3">
                            <a href="${pageContext.request.contextPath}/app/admin/reportes" 
                               class="btn btn-outline-ruwana w-100">
                                <i class="bi bi-graph-up"></i> Generar Reportes
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap 5 JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
