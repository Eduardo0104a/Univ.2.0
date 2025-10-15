<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Organizaciones - Ruwana Admin</title>
    <jsp:include page="/WEB-INF/fragments/admin-head.jsp" />
</head>
<body>
    <jsp:include page="/WEB-INF/fragments/admin-navbar.jsp">
        <jsp:param name="page" value="organizaciones" />
    </jsp:include>

    <!-- Main Content -->
    <div class="container-fluid py-4">
        <!-- Page Header -->
        <div class="row mb-4">
            <div class="col-12">
                <div class="card-ruwana p-4" style="background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-dark) 100%); color: white;">
                    <h2 class="mb-2">
                        <i class="bi bi-buildings"></i> Gestión de Organizaciones
                    </h2>
                    <p class="mb-0 opacity-75">Aprobar o rechazar solicitudes de registro</p>
                </div>
            </div>
        </div>

        <!-- Success/Error Messages -->
        <c:if test="${not empty success}">
            <div class="row mb-3">
                <div class="col-12">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <i class="bi bi-check-circle"></i> ${success}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </div>
            </div>
            <c:remove var="success" scope="session"/>
        </c:if>
        
        <c:if test="${not empty error}">
            <div class="row mb-3">
                <div class="col-12">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <i class="bi bi-exclamation-triangle"></i> ${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </div>
            </div>
            <c:remove var="error" scope="session"/>
        </c:if>
        <!-- Pending Organizations Section -->
        <div class="row mb-5">
            <div class="col-12">
                <div class="card shadow-sm">
                    <div class="card-header bg-warning text-dark">
                        <h5 class="mb-0">
                            <i class="bi bi-clock-history"></i> Organizaciones Pendientes de Aprobación 
                            <span class="badge bg-dark">${organizacionesPendientes.size()}</span>
                        </h5>
                    </div>
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${not empty organizacionesPendientes}">
                                <div class="table-responsive">
                                    <table class="table table-hover align-middle">
                                        <thead class="table-light">
                                            <tr>
                                                <th>Organización</th>
                                                <th>País</th>
                                                <th>Correo</th>
                                                <th>Documento</th>
                                                <th>¿Constituida?</th>
                                                <th class="text-center">Acciones</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${organizacionesPendientes}" var="org">
                                                <tr>
                                                    <td>
                                                        <strong>${org.nombreOrganizacion}</strong><br>
                                                        <small class="text-muted">${org.contactoPrincipal}</small>
                                                    </td>
                                                    <td>${org.pais}</td>
                                                    <td>${org.usuario.email}</td>
                                                    <td>
                                                        <c:if test="${not empty org.tipoDocumento}">
                                                            ${org.tipoDocumento.nombre}:
                                                        </c:if>
                                                        ${org.numeroDocumento}
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${org.constituidaLegalmente}">
                                                                <span class="badge bg-success">Sí</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="badge bg-secondary">No</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td class="text-center">
                                                        <div class="btn-group">
                                                            <button type="button" class="btn btn-sm btn-info" 
                                                                    onclick="verDetalle(${org.id})"
                                                                    title="Ver detalles">
                                                                <i class="bi bi-eye"></i>
                                                            </button>
                                                            <form method="post" action="${pageContext.request.contextPath}/app/admin/organizacion/aprobar" style="display: inline;">
                                                                <input type="hidden" name="organizacionId" value="${org.id}">
                                                                <input type="hidden" name="accion" value="aprobar">
                                                                <button type="submit" class="btn btn-sm btn-success" title="Aprobar">
                                                                    <i class="bi bi-check-circle"></i>
                                                                </button>
                                                            </form>
                                                            <button type="button" class="btn btn-sm btn-danger" 
                                                                    onclick="rechazarOrganizacion(${org.id}, '${org.nombreOrganizacion}')"
                                                                    title="Rechazar">
                                                                <i class="bi bi-x-circle"></i>
                                                            </button>
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
                                    <i class="bi bi-check-circle" style="font-size: 4rem; color: var(--success-color);"></i>
                                    <p class="text-muted mt-3">No hay organizaciones pendientes de aprobación</p>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>

        <!-- All Organizations Section -->
        <div class="row">
            <div class="col-12">
                <div class="card shadow-sm">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0">
                            <i class="bi bi-list-ul"></i> Todas las Organizaciones 
                            <span class="badge bg-light text-dark">${todasOrganizaciones.size()}</span>
                        </h5>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-striped table-hover align-middle">
                                <thead class="table-light">
                                    <tr>
                                        <th>ID</th>
                                        <th>Organización</th>
                                        <th>País</th>
                                        <th>Correo</th>
                                        <th>Estado</th>
                                        <th class="text-center">Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${todasOrganizaciones}" var="org">
                                        <tr>
                                            <td>${org.id}</td>
                                            <td>
                                                <strong>${org.nombreOrganizacion}</strong><br>
                                                <small class="text-muted">${org.contactoPrincipal}</small>
                                            </td>
                                            <td>${org.pais}</td>
                                            <td>${org.usuario.email}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${org.estadoAprobacion == 'APROBADO'}">
                                                        <span class="badge bg-success">Aprobada</span>
                                                    </c:when>
                                                    <c:when test="${org.estadoAprobacion == 'PENDIENTE'}">
                                                        <span class="badge bg-warning text-dark">Pendiente</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-danger">Rechazada</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td class="text-center">
                                                <button type="button" class="btn btn-sm btn-info" 
                                                        onclick="verDetalle(${org.id})"
                                                        title="Ver detalles">
                                                    <i class="bi bi-eye"></i>
                                                </button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal for Organization Details -->
    <div class="modal fade" id="detalleModal" tabindex="-1">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title"><i class="bi bi-buildings"></i> Detalles de la Organización</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body" id="detalleContent">
                    <!-- Content will be loaded dynamically -->
                </div>
            </div>
        </div>
    </div>

    <!-- Modal for Rejection -->
    <div class="modal fade" id="rechazarModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header bg-danger text-white">
                    <h5 class="modal-title"><i class="bi bi-x-circle"></i> Rechazar Organización</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <form method="post" action="${pageContext.request.contextPath}/app/admin/organizacion/aprobar">
                    <div class="modal-body">
                        <input type="hidden" name="organizacionId" id="rechazarOrgId">
                        <input type="hidden" name="accion" value="rechazar">
                        <p>¿Estás seguro de rechazar la organización <strong id="rechazarOrgNombre"></strong>?</p>
                        <div class="mb-3">
                            <label for="motivo" class="form-label">Motivo del Rechazo <span class="text-danger">*</span></label>
                            <textarea class="form-control" id="motivo" name="motivo" rows="4" required 
                                      placeholder="Explica el motivo del rechazo..."></textarea>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                        <button type="submit" class="btn btn-danger">
                            <i class="bi bi-x-circle"></i> Confirmar Rechazo
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/fragments/admin-scripts.jsp" />
    <script>
        function rechazarOrganizacion(orgId, orgNombre) {
            document.getElementById('rechazarOrgId').value = orgId;
            document.getElementById('rechazarOrgNombre').textContent = orgNombre;
            new bootstrap.Modal(document.getElementById('rechazarModal')).show();
        }

        function verDetalle(orgId) {
            // TODO: Load organization details via AJAX or redirect
            alert('Función de ver detalle en desarrollo. ID: ' + orgId);
        }
    </script>
</body>
</html>
