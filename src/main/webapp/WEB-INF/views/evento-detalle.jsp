<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${evento.nombre} - Ruwana</title>
    
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
                        <a class="nav-link" href="${pageContext.request.contextPath}/eventos">Eventos</a>
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
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Event Detail -->
    <div class="container py-5">
        <div class="row">
            <!-- Main Content -->
            <div class="col-lg-8">
                <!-- Event Header -->
                <div class="card-ruwana mb-4">
                    <div style="height: 300px; background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-light) 100%); position: relative; border-radius: 15px 15px 0 0;">
                        <div style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); text-align: center; color: white;">
                            <i class="bi bi-calendar-event" style="font-size: 6rem; opacity: 0.3;"></i>
                        </div>
                    </div>
                    
                    <div class="p-4">
                        <h1 class="mb-3" style="color: var(--primary-color);">${evento.nombre}</h1>
                        
                        <div class="mb-4">
                            <span class="badge bg-success me-2">
                                <i class="bi bi-check-circle"></i> Evento Aprobado
                            </span>
                            <c:if test="${evento.cuposMaximos != null && evento.cuposMaximos > 0}">
                                <span class="badge" style="background-color: var(--accent-color);">
                                    <i class="bi bi-people"></i> ${evento.cuposDisponibles}/${evento.cuposMaximos} cupos disponibles
                                </span>
                            </c:if>
                        </div>
                        
                        <!-- Event Info -->
                        <div class="row g-3 mb-4">
                            <div class="col-md-6">
                                <div class="d-flex align-items-center">
                                    <i class="bi bi-building" style="font-size: 1.5rem; color: var(--primary-color); margin-right: 10px;"></i>
                                    <div>
                                        <small class="text-muted d-block">Organización</small>
                                        <strong>${evento.organizacion.nombreOrganizacion}</strong>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="d-flex align-items-center">
                                    <i class="bi bi-calendar" style="font-size: 1.5rem; color: var(--primary-color); margin-right: 10px;"></i>
                                    <div>
                                        <small class="text-muted d-block">Fecha</small>
                                        <strong>${evento.fechaInicio}</strong>
                                        <c:if test="${evento.fechaInicio != evento.fechaFin}">
                                            - ${evento.fechaFin}
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="d-flex align-items-center">
                                    <i class="bi bi-clock" style="font-size: 1.5rem; color: var(--primary-color); margin-right: 10px;"></i>
                                    <div>
                                        <small class="text-muted d-block">Horario</small>
                                        <strong>${evento.horario}</strong>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="d-flex align-items-center">
                                    <i class="bi bi-geo-alt" style="font-size: 1.5rem; color: var(--primary-color); margin-right: 10px;"></i>
                                    <div>
                                        <small class="text-muted d-block">Ubicación</small>
                                        <strong>${evento.lugar}</strong>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <hr>
                        
                        <!-- Description -->
                        <h4 style="color: var(--primary-color); margin-top: 2rem;">Descripción</h4>
                        <div class="event-description">
                            ${evento.descripcion}
                        </div>
                        
                        <c:if test="${not empty evento.informacionPrograma}">
                            <h4 style="color: var(--primary-color); margin-top: 2rem;">Información del Programa</h4>
                            <p>${evento.informacionPrograma}</p>
                        </c:if>
                        
                        <c:if test="${not empty evento.direccion}">
                            <h4 style="color: var(--primary-color); margin-top: 2rem;">Dirección</h4>
                            <p><i class="bi bi-map"></i> ${evento.direccion}</p>
                        </c:if>
                        
                        <c:if test="${not empty evento.telefonoContacto}">
                            <h4 style="color: var(--primary-color); margin-top: 2rem;">Contacto</h4>
                            <p><i class="bi bi-telephone"></i> ${evento.telefonoContacto}</p>
                        </c:if>
                    </div>
                </div>
            </div>
            
            <!-- Sidebar -->
            <div class="col-lg-4">
                <div class="card-ruwana sticky-top" style="top: 20px;">
                    <div class="p-4">
                        <h5 class="mb-4" style="color: var(--primary-color);">
                            <i class="bi bi-person-check"></i> Inscripción
                        </h5>
                        
                        <c:choose>
                            <c:when test="${empty sessionScope.usuario}">
                                <p class="text-muted mb-3">Para inscribirte en este evento necesitas tener una cuenta</p>
                                <a href="${pageContext.request.contextPath}/login?redirect=${pageContext.request.contextPath}/eventos/${evento.id}" 
                                   class="btn btn-primary-ruwana w-100 mb-2">
                                    <i class="bi bi-box-arrow-in-right"></i> Iniciar Sesión
                                </a>
                                <a href="${pageContext.request.contextPath}/registro/voluntario" 
                                   class="btn btn-outline-ruwana w-100">
                                    <i class="bi bi-person-plus"></i> Crear Cuenta
                                </a>
                            </c:when>
                            <c:when test="${sessionScope.usuario.rol.name() != 'VOLUNTARIO'}">
                                <div class="alert alert-warning">
                                    <i class="bi bi-info-circle"></i>
                                    Solo los voluntarios pueden inscribirse en eventos.
                                </div>
                            </c:when>
                            <c:when test="${yaInscrito}">
                                <div class="alert alert-success">
                                    <i class="bi bi-check-circle"></i>
                                    <strong>¡Ya estás inscrito en este evento!</strong>
                                </div>
                                <a href="${pageContext.request.contextPath}/app/voluntario/dashboard" 
                                   class="btn btn-outline-ruwana w-100">
                                    <i class="bi bi-house"></i> Ir a Mi Panel
                                </a>
                            </c:when>
                            <c:when test="${!evento.tieneCuposDisponibles()}">
                                <div class="alert alert-error alert-custom">
                                    <i class="bi bi-x-circle"></i>
                                    Este evento ya no tiene cupos disponibles
                                </div>
                            </c:when>
                            <c:otherwise>
                                <p class="text-muted mb-3">
                                    Al inscribirte, te comprometes a participar en este evento de voluntariado.
                                </p>
                                
                                <form method="post" action="${pageContext.request.contextPath}/inscripcion" 
                                      onsubmit="return confirm('¿Confirmas tu inscripción a este evento?');">
                                    <input type="hidden" name="eventoId" value="${evento.id}">
                                    <button type="submit" class="btn btn-primary-ruwana w-100">
                                        <i class="bi bi-check-circle"></i> Inscribirme Ahora
                                    </button>
                                </form>
                                
                                <div class="mt-3">
                                    <small class="text-muted">
                                        <i class="bi bi-info-circle"></i>
                                        Recibirás una confirmación al inscribirte
                                    </small>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Back Button -->
        <div class="mt-4">
            <a href="${pageContext.request.contextPath}/eventos" class="btn btn-outline-ruwana">
                <i class="bi bi-arrow-left"></i> Volver a Eventos
            </a>
        </div>
    </div>

    <!-- Bootstrap 5 JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
