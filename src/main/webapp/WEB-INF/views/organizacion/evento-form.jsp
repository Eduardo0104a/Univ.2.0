<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${empty evento ? 'Nuevo Evento' : 'Editar Evento'} - Ruwana</title>
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
                    <h2 class="mb-2">
                        <i class="bi bi-${empty evento ? 'plus-circle' : 'pencil'}"></i>
                        ${empty evento ? 'Crear Nuevo Evento' : 'Editar Evento'}
                    </h2>
                    <p class="mb-0 opacity-75">${organizacion.nombreOrganizacion}</p>
                </div>
            </div>
        </div>

        <!-- Form -->
        <div class="row justify-content-center">
            <div class="col-lg-10">
                <div class="form-container">
                    <c:choose>
                        <c:when test="${empty evento}">
                            <c:set var="formAction" value="${pageContext.request.contextPath}/app/organizacion/evento/nuevo"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="formAction" value="${pageContext.request.contextPath}/app/organizacion/evento/editar/${evento.id}"/>
                        </c:otherwise>
                    </c:choose>
                    <form method="post" action="${formAction}">
                        <c:if test="${not empty evento}">
                            <input type="hidden" name="eventoId" value="${evento.id}">
                        </c:if>
                        
                        <!-- Basic Information -->
                        <h4 style="color: var(--primary-color); margin-bottom: 1.5rem;">
                            <i class="bi bi-info-circle"></i> Información Básica
                        </h4>
                        
                        <div class="mb-3">
                            <label for="nombre" class="form-label">
                                Nombre del Evento <span class="text-danger">*</span>
                            </label>
                            <input type="text" 
                                   class="form-control" 
                                   id="nombre" 
                                   name="nombre" 
                                   value="${evento.nombre}"
                                   required
                                   maxlength="255">
                        </div>

                        <div class="mb-3">
                            <label for="descripcion" class="form-label">
                                Descripción <span class="text-danger">*</span>
                            </label>
                            <textarea class="form-control" 
                                      id="descripcion" 
                                      name="descripcion" 
                                      rows="5"
                                      required>${evento.descripcion}</textarea>
                            <small class="text-muted">Describe en detalle el evento y sus objetivos</small>
                        </div>

                        <div class="mb-4">
                            <label for="informacionPrograma" class="form-label">
                                Información del Programa
                            </label>
                            <textarea class="form-control" 
                                      id="informacionPrograma" 
                                      name="informacionPrograma" 
                                      rows="3">${evento.informacionPrograma}</textarea>
                            <small class="text-muted">Información adicional sobre el programa o actividades</small>
                        </div>

                        <!-- Date and Time -->
                        <h4 style="color: var(--primary-color); margin-bottom: 1.5rem; margin-top: 2rem;">
                            <i class="bi bi-calendar"></i> Fecha y Horario
                        </h4>

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="fechaInicio" class="form-label">
                                    Fecha de Inicio <span class="text-danger">*</span>
                                </label>
                                <input type="date" 
                                       class="form-control" 
                                       id="fechaInicio" 
                                       name="fechaInicio" 
                                       value="${evento.fechaInicio}"
                                       required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="fechaFin" class="form-label">
                                    Fecha de Fin <span class="text-danger">*</span>
                                </label>
                                <input type="date" 
                                       class="form-control" 
                                       id="fechaFin" 
                                       name="fechaFin" 
                                       value="${evento.fechaFin}"
                                       required>
                            </div>
                        </div>

                        <div class="mb-4">
                            <label for="horario" class="form-label">
                                Horario
                            </label>
                            <input type="text" 
                                   class="form-control" 
                                   id="horario" 
                                   name="horario" 
                                   value="${evento.horario}"
                                   placeholder="Ej: 9:00 AM - 1:00 PM">
                            <small class="text-muted">Horario de las actividades</small>
                        </div>

                        <!-- Location -->
                        <h4 style="color: var(--primary-color); margin-bottom: 1.5rem; margin-top: 2rem;">
                            <i class="bi bi-geo-alt"></i> Ubicación
                        </h4>

                        <div class="mb-3">
                            <label for="lugar" class="form-label">
                                Lugar <span class="text-danger">*</span>
                            </label>
                            <input type="text" 
                                   class="form-control" 
                                   id="lugar" 
                                   name="lugar" 
                                   value="${evento.lugar}"
                                   required
                                   maxlength="255"
                                   placeholder="Ej: Centro Comunitario San Juan">
                        </div>

                        <div class="mb-4">
                            <label for="direccion" class="form-label">
                                Dirección Completa
                            </label>
                            <textarea class="form-control" 
                                      id="direccion" 
                                      name="direccion" 
                                      rows="2"
                                      placeholder="Calle, número, distrito, ciudad">${evento.direccion}</textarea>
                        </div>

                        <!-- Contact and Capacity -->
                        <h4 style="color: var(--primary-color); margin-bottom: 1.5rem; margin-top: 2rem;">
                            <i class="bi bi-people"></i> Contacto y Cupos
                        </h4>

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="telefonoContacto" class="form-label">
                                    Teléfono de Contacto
                                </label>
                                <input type="tel" 
                                       class="form-control" 
                                       id="telefonoContacto" 
                                       name="telefonoContacto" 
                                       value="${evento.telefonoContacto}"
                                       placeholder="Ej: 987 654 321">
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="cuposMaximos" class="form-label">
                                    Cupos Máximos
                                </label>
                                <input type="number" 
                                       class="form-control" 
                                       id="cuposMaximos" 
                                       name="cuposMaximos" 
                                       value="${evento.cuposMaximos}"
                                       min="0"
                                       placeholder="Dejar vacío para ilimitado">
                                <small class="text-muted">Deja vacío o 0 para cupos ilimitados</small>
                            </div>
                        </div>

                        <!-- Status Info (only for editing) -->
                        <c:if test="${not empty evento}">
                            <div class="alert alert-info mt-4">
                                <strong>Estado actual:</strong> 
                                <c:choose>
                                    <c:when test="${evento.estado.name() == 'APROBADO'}">
                                        <span class="badge bg-success">Aprobado</span>
                                    </c:when>
                                    <c:when test="${evento.estado.name() == 'PENDIENTE_APROBACION'}">
                                        <span class="badge bg-warning">Pendiente de Aprobación</span>
                                    </c:when>
                                    <c:when test="${evento.estado.name() == 'RECHAZADO'}">
                                        <span class="badge bg-danger">Rechazado</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-secondary">${evento.estado.name()}</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:if>

                        <!-- Buttons -->
                        <div class="d-flex gap-3 mt-4">
                            <button type="submit" class="btn btn-primary-ruwana">
                                <i class="bi bi-check-circle"></i>
                                ${empty evento ? 'Crear Evento' : 'Guardar Cambios'}
                            </button>
                            <a href="${pageContext.request.contextPath}/app/organizacion/eventos" 
                               class="btn btn-outline-ruwana">
                                <i class="bi bi-x-circle"></i> Cancelar
                            </a>
                        </div>

                        <c:if test="${empty evento}">
                            <div class="alert alert-warning mt-4">
                                <i class="bi bi-info-circle"></i>
                                <strong>Nota:</strong> Los eventos creados deben ser aprobados por un administrador antes de ser visibles públicamente.
                            </div>
                        </c:if>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/fragments/common-scripts.jsp" />
    <script>
        // Validate end date is after start date
        document.getElementById('fechaInicio').addEventListener('change', function() {
            document.getElementById('fechaFin').min = this.value;
        });
    </script>
</body>
</html>
