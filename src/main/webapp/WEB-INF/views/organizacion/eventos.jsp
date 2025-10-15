<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mis Eventos - Ruwana</title>
    <jsp:include page="/WEB-INF/fragments/common-head.jsp" />
</head>
<body>
    <jsp:include page="/WEB-INF/fragments/organizacion-navbar.jsp">
        <jsp:param name="page" value="eventos" />
    </jsp:include>

    <!-- Main Content -->
    <div class="container-fluid py-4">
        <!-- Page Header -->
        <div class="row mb-4">
            <div class="col-12">
                <div class="card-ruwana p-4" style="background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-dark) 100%); color: white;">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h2 class="mb-2">
                                <i class="bi bi-calendar-check"></i> Mis Eventos
                            </h2>
                            <p class="mb-0 opacity-75">${organizacion.nombreOrganizacion}</p>
                        </div>
                        <a href="${pageContext.request.contextPath}/app/organizacion/evento/nuevo" class="btn btn-light btn-lg">
                            <i class="bi bi-plus-circle"></i> Crear Nuevo Evento
                        </a>
                    </div>
                </div>
            </div>
        </div>
        <!-- Success/Error Messages -->
        <c:if test="${not empty sessionScope.success}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="bi bi-check-circle"></i> ${sessionScope.success}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <c:remove var="success" scope="session"/>
        </c:if>
        
        <c:if test="${not empty sessionScope.error}">
            <div class="alert alert-error alert-custom alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle"></i> ${sessionScope.error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <c:remove var="error" scope="session"/>
        </c:if>

        <!-- Pending Events -->
        <c:if test="${not empty eventosPendientes}">
            <div class="mb-5">
                <h3 style="color: var(--primary-color);" class="mb-4">
                    <i class="bi bi-hourglass-split"></i> Eventos Pendientes de Aprobación
                </h3>
                <div class="row g-4">
                    <c:forEach items="${eventosPendientes}" var="evento">
                        <div class="col-md-6 col-lg-4">
                            <div class="card-ruwana h-100">
                                <div class="p-3">
                                    <div class="d-flex justify-content-between align-items-start mb-3">
                                        <h5 class="mb-0" style="color: var(--primary-color);">${evento.nombre}</h5>
                                        <span class="badge bg-warning">
                                            <i class="bi bi-clock"></i> Pendiente
                                        </span>
                                    </div>
                                    <p class="mb-2">
                                        <i class="bi bi-calendar"></i>
                                        <small>${evento.fechaInicio}</small>
                                    </p>
                                    <p class="mb-3">
                                        <i class="bi bi-geo-alt"></i>
                                        <small>${evento.lugar}</small>
                                    </p>
                                    <div class="d-flex gap-2">
                                        <a href="${pageContext.request.contextPath}/app/organizacion/evento/editar/${evento.id}" 
                                           class="btn btn-outline-ruwana btn-sm">
                                            <i class="bi bi-pencil"></i> Editar
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:if>

        <!-- All Events -->
        <h3 style="color: var(--primary-color);" class="mb-4">
            <i class="bi bi-list-check"></i> Todos los Eventos
        </h3>
        
        <c:choose>
            <c:when test="${not empty eventos}">
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead style="background-color: var(--background-cream);">
                            <tr>
                                <th>Nombre</th>
                                <th>Fecha</th>
                                <th>Lugar</th>
                                <th>Estado</th>
                                <th>Cupos</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${eventos}" var="evento">
                                <tr>
                                    <td>
                                        <strong>${evento.nombre}</strong>
                                    </td>
                                    <td>
                                        <small>${evento.fechaInicio}</small>
                                        <c:if test="${evento.fechaInicio != evento.fechaFin}">
                                            <br><small>a ${evento.fechaFin}</small>
                                        </c:if>
                                    </td>
                                    <td>${evento.lugar}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${evento.estado.name() == 'APROBADO'}">
                                                <span class="badge bg-success">
                                                    <i class="bi bi-check-circle"></i> Aprobado
                                                </span>
                                            </c:when>
                                            <c:when test="${evento.estado.name() == 'PENDIENTE_APROBACION'}">
                                                <span class="badge bg-warning">
                                                    <i class="bi bi-clock"></i> Pendiente
                                                </span>
                                            </c:when>
                                            <c:when test="${evento.estado.name() == 'RECHAZADO'}">
                                                <span class="badge bg-danger">
                                                    <i class="bi bi-x-circle"></i> Rechazado
                                                </span>
                                            </c:when>
                                            <c:when test="${evento.estado.name() == 'EN_CURSO'}">
                                                <span class="badge bg-info">
                                                    <i class="bi bi-play-circle"></i> En Curso
                                                </span>
                                            </c:when>
                                            <c:when test="${evento.estado.name() == 'FINALIZADO'}">
                                                <span class="badge bg-secondary">
                                                    <i class="bi bi-check"></i> Finalizado
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary">${evento.estado.name()}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${evento.cuposMaximos != null && evento.cuposMaximos > 0}">
                                                ${evento.cuposDisponibles}/${evento.cuposMaximos}
                                            </c:when>
                                            <c:otherwise>
                                                <small class="text-muted">Ilimitados</small>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <div class="btn-group btn-group-sm" role="group">
                                            <a href="${pageContext.request.contextPath}/eventos/${evento.id}" 
                                               class="btn btn-outline-secondary" 
                                               target="_blank"
                                               title="Ver público">
                                                <i class="bi bi-eye"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/app/organizacion/evento/editar/${evento.id}" 
                                               class="btn btn-outline-primary"
                                               title="Editar">
                                                <i class="bi bi-pencil"></i>
                                            </a>
                                            <c:if test="${evento.estado.name() == 'PENDIENTE_APROBACION' || evento.estado.name() == 'BORRADOR'}">
                                                <button type="button" 
                                                        class="btn btn-outline-danger"
                                                        onclick="eliminarEvento(${evento.id}, '${evento.nombre}')"
                                                        title="Eliminar">
                                                    <i class="bi bi-trash"></i>
                                                </button>
                                            </c:if>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>
            <c:otherwise>
                <div class="text-center py-5">
                    <i class="bi bi-calendar-x" style="font-size: 4rem; color: var(--text-light);"></i>
                    <h4 class="mt-3" style="color: var(--text-light);">No has creado eventos aún</h4>
                    <p class="text-muted">Crea tu primer evento para empezar a conectar con voluntarios</p>
                    <a href="${pageContext.request.contextPath}/app/organizacion/evento/nuevo" class="btn btn-primary-ruwana mt-3">
                        <i class="bi bi-plus-circle"></i> Crear Primer Evento
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- Delete Confirmation Modal -->
    <form id="deleteForm" method="post" action="${pageContext.request.contextPath}/app/organizacion/evento/eliminar">
        <input type="hidden" name="eventoId" id="deleteEventoId">
    </form>

    <jsp:include page="/WEB-INF/fragments/common-scripts.jsp" />
    <script>
        function eliminarEvento(eventoId, eventoNombre) {
            if (confirm('¿Estás seguro de eliminar el evento "' + eventoNombre + '"?\n\nEsta acción no se puede deshacer.')) {
                document.getElementById('deleteEventoId').value = eventoId;
                document.getElementById('deleteForm').submit();
            }
        }
    </script>
</body>
</html>
