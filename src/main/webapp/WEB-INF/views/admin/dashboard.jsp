<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel Administrador - Ruwana</title>
    <jsp:include page="/WEB-INF/fragments/admin-head.jsp" />
</head>
<body>
    <jsp:include page="/WEB-INF/fragments/admin-navbar.jsp">
        <jsp:param name="page" value="dashboard" />
    </jsp:include>

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
                                <i class="bi bi-buildings" style="font-size: 2rem; color: #2196F3;"></i>
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
                        <i class="bi bi-buildings-exclamation"></i> Organizaciones Pendientes
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
                                                        <i class="bi bi-buildings"></i> ${evento.organizacion.nombreOrganizacion}
                                                    </p>
                                                    <p class="mb-0 small text-muted">
                                                        <i class="bi bi-calendar"></i> ${evento.fechaInicio} - ${evento.fechaFin}
                                                    </p>
                                                </div>
                                                <div class="btn-group">
                                                    <form method="post" action="${pageContext.request.contextPath}/app/admin/evento/aprobar" style="display: inline;">
                                                        <input type="hidden" name="eventoId" value="${evento.id}">
                                                        <input type="hidden" name="accion" value="aprobar">
                                                        <button type="submit" class="btn btn-sm btn-success" title="Aprobar">
                                                            <i class="bi bi-check-circle"></i>
                                                        </button>
                                                    </form>
                                                    <button type="button" class="btn btn-sm btn-danger" 
                                                            onclick="rechazarEvento(${evento.id}, '${evento.nombre}')" 
                                                            title="Rechazar">
                                                        <i class="bi bi-x-circle"></i>
                                                    </button>
                                                    <a href="${pageContext.request.contextPath}/eventos/${evento.id}" 
                                                       class="btn btn-sm btn-outline-primary" 
                                                       target="_blank"
                                                       title="Ver detalle">
                                                        <i class="bi bi-eye"></i>
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
                                <i class="bi bi-buildings"></i> Ver Organizaciones
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

    <!-- Hidden form for rejecting events -->
    <form id="rechazarEventoForm" method="post" action="${pageContext.request.contextPath}/app/admin/evento/aprobar">
        <input type="hidden" name="eventoId" id="rechazarEventoId">
        <input type="hidden" name="accion" value="rechazar">
    </form>

    <jsp:include page="/WEB-INF/fragments/admin-scripts.jsp" />
    <script>
        function rechazarEvento(eventoId, eventoNombre) {
            if (confirm('¿Estás seguro de rechazar el evento "' + eventoNombre + '"?')) {
                document.getElementById('rechazarEventoId').value = eventoId;
                document.getElementById('rechazarEventoForm').submit();
            }
        }
    </script>
</body>
</html>
