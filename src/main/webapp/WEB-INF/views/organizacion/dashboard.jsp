<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel Organización - Ruwana</title>
    
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
                <i class="bi bi-heart-fill"></i> Ruwana
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/app/organizacion/dashboard">
                            <i class="bi bi-speedometer2"></i> Dashboard
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/app/organizacion/eventos">
                            <i class="bi bi-calendar-event"></i> Mis Eventos
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/app/organizacion/eventos/crear">
                            <i class="bi bi-plus-circle"></i> Crear Evento
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/app/organizacion/donaciones">
                            <i class="bi bi-currency-dollar"></i> Donaciones
                        </a>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" 
                           data-bs-toggle="dropdown">
                            <i class="bi bi-building-fill-gear"></i> ${organizacion.nombreOrganizacion}
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li>
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/app/organizacion/perfil">
                                    <i class="bi bi-building"></i> Perfil de Organización
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
        <!-- Status Alert (if organization is pending) -->
        <c:if test="${organizacion.estadoAprobacion == 'PENDIENTE'}">
            <div class="alert alert-custom" style="background-color: #FFF3CD; color: #856404;">
                <i class="bi bi-hourglass-split"></i>
                <strong>Cuenta Pendiente de Aprobación</strong>
                <p class="mb-0">Tu organización está siendo revisada por nuestro equipo. Te notificaremos por correo cuando sea aprobada.</p>
            </div>
        </c:if>

        <c:if test="${organizacion.estadoAprobacion == 'RECHAZADO'}">
            <div class="alert alert-error alert-custom">
                <i class="bi bi-exclamation-triangle-fill"></i>
                <strong>Cuenta Rechazada</strong>
                <p class="mb-0">Motivo: ${organizacion.motivoRechazo}</p>
            </div>
        </c:if>

        <!-- Welcome Header -->
        <div class="row mb-4">
            <div class="col-12">
                <div class="card-ruwana p-4" style="background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-dark) 100%); color: white;">
                    <h2 class="mb-2">
                        <i class="bi bi-building"></i> ${organizacion.nombreOrganizacion}
                    </h2>
                    <p class="mb-0 opacity-75">Gestiona tus eventos y conecta con voluntarios</p>
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
                                <i class="bi bi-calendar-check" style="font-size: 2rem; color: #2196F3;"></i>
                            </div>
                        </div>
                        <div class="flex-grow-1 ms-3">
                            <h6 class="text-muted mb-1">Eventos Totales</h6>
                            <h3 class="mb-0" style="color: #2196F3;">${eventos.size()}</h3>
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
                            <h3 class="mb-0" style="color: #FF9800;">${eventosPendientes}</h3>
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
                            <h3 class="mb-0" style="color: #4CAF50;">0</h3>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-3">
                <div class="card-ruwana p-3">
                    <div class="d-flex align-items-center">
                        <div class="flex-shrink-0">
                            <div class="rounded-circle p-3" style="background-color: #FFE5E9;">
                                <i class="bi bi-currency-dollar" style="font-size: 2rem; color: var(--primary-color);"></i>
                            </div>
                        </div>
                        <div class="flex-grow-1 ms-3">
                            <h6 class="text-muted mb-1">Donaciones</h6>
                            <h3 class="mb-0" style="color: var(--primary-color);">S/ 0</h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Main Content Row -->
        <div class="row">
            <!-- Left Column - Events -->
            <div class="col-lg-8">
                <div class="card-ruwana p-4 mb-4">
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <h4 style="color: var(--primary-color); margin: 0;">
                            <i class="bi bi-calendar-event"></i> Mis Eventos
                        </h4>
                        <div class="btn-group">
                            <a href="${pageContext.request.contextPath}/app/organizacion/eventos" 
                               class="btn btn-outline-ruwana">
                                <i class="bi bi-list"></i> Ver Todos
                            </a>
                            <a href="${pageContext.request.contextPath}/app/organizacion/evento/nuevo" 
                               class="btn btn-primary-ruwana">
                                <i class="bi bi-plus-circle"></i> Crear Evento
                            </a>
                        </div>
                    </div>
                    
                    <c:choose>
                        <c:when test="${not empty eventos}">
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th>Evento</th>
                                            <th>Fecha</th>
                                            <th>Estado</th>
                                            <th>Acciones</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${eventos}" var="evento" varStatus="status">
                                            <c:if test="${status.index < 5}">
                                                <tr>
                                                    <td>
                                                        <strong>${evento.nombre}</strong>
                                                        <br>
                                                        <small class="text-muted">${evento.lugar}</small>
                                                    </td>
                                                    <td>${evento.fechaInicio}</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${evento.estado.name() == 'APROBADO'}">
                                                                <span class="badge bg-success">Aprobado</span>
                                                            </c:when>
                                                            <c:when test="${evento.estado.name() == 'PENDIENTE_APROBACION'}">
                                                                <span class="badge bg-warning">Pendiente</span>
                                                            </c:when>
                                                            <c:when test="${evento.estado.name() == 'RECHAZADO'}">
                                                                <span class="badge bg-danger">Rechazado</span>
                                                            </c:when>
                                                            <c:when test="${evento.estado.name() == 'EN_CURSO'}">
                                                                <span class="badge bg-info">En Curso</span>
                                                            </c:when>
                                                            <c:when test="${evento.estado.name() == 'FINALIZADO'}">
                                                                <span class="badge bg-secondary">Finalizado</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="badge bg-secondary">${evento.estado.name()}</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <a href="${pageContext.request.contextPath}/app/organizacion/eventos/${evento.id}" 
                                                           class="btn btn-sm btn-outline-primary">
                                                            <i class="bi bi-eye"></i>
                                                        </a>
                                                    </td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            
                            <div class="text-center mt-3">
                                <a href="${pageContext.request.contextPath}/app/organizacion/eventos" 
                                   class="link-ruwana">
                                    Ver todos los eventos <i class="bi bi-arrow-right"></i>
                                </a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="text-center py-5">
                                <i class="bi bi-calendar-x" style="font-size: 4rem; color: var(--text-light);"></i>
                                <p class="text-muted mt-3 mb-3">Aún no has creado ningún evento</p>
                                <a href="${pageContext.request.contextPath}/app/organizacion/eventos/crear" 
                                   class="btn btn-primary-ruwana">
                                    <i class="bi bi-plus-circle"></i> Crear Mi Primer Evento
                                </a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <!-- Right Column - Quick Actions -->
            <div class="col-lg-4">
                <!-- Quick Actions -->
                <div class="card-ruwana p-4 mb-4">
                    <h5 class="mb-3" style="color: var(--primary-color);">
                        <i class="bi bi-lightning"></i> Acciones Rápidas
                    </h5>
                    <div class="d-grid gap-2">
                        <a href="${pageContext.request.contextPath}/app/organizacion/eventos/crear" 
                           class="btn btn-primary-ruwana">
                            <i class="bi bi-plus-circle"></i> Crear Evento
                        </a>
                        <a href="${pageContext.request.contextPath}/app/organizacion/eventos" 
                           class="btn btn-outline-ruwana">
                            <i class="bi bi-calendar-event"></i> Ver Mis Eventos
                        </a>
                        <a href="${pageContext.request.contextPath}/app/organizacion/perfil" 
                           class="btn btn-outline-ruwana">
                            <i class="bi bi-building"></i> Editar Perfil
                        </a>
                    </div>
                </div>

                <!-- Organization Info -->
                <div class="card-ruwana p-4 mb-4">
                    <h6 class="mb-3" style="color: var(--primary-color);">Información de Organización</h6>
                    <div class="small">
                        <p class="mb-2">
                            <i class="bi bi-envelope"></i>
                            <strong>Email:</strong> ${organizacion.usuario.email}
                        </p>
                        <p class="mb-2">
                            <i class="bi bi-phone"></i>
                            <strong>Teléfono:</strong> ${organizacion.telefono}
                        </p>
                        <p class="mb-2">
                            <i class="bi bi-geo-alt"></i>
                            <strong>País:</strong> ${organizacion.pais}
                        </p>
                        <p class="mb-0">
                            <i class="bi bi-shield-check"></i>
                            <strong>Estado:</strong> 
                            <c:choose>
                                <c:when test="${organizacion.estadoAprobacion == 'APROBADO'}">
                                    <span class="text-success">Aprobada</span>
                                </c:when>
                                <c:when test="${organizacion.estadoAprobacion == 'PENDIENTE'}">
                                    <span class="text-warning">Pendiente</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="text-danger">Rechazada</span>
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </div>
                </div>

                <!-- Help -->
                <div class="card-ruwana p-4" style="background-color: var(--background-cream);">
                    <h6 class="mb-3" style="color: var(--primary-color);">
                        <i class="bi bi-question-circle"></i> ¿Necesitas Ayuda?
                    </h6>
                    <p class="small mb-0">
                        Si tienes dudas sobre cómo crear eventos o gestionar voluntarios, contáctanos a 
                        <a href="mailto:soporte@ruwana.org" class="link-ruwana">soporte@ruwana.org</a>
                    </p>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap 5 JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
