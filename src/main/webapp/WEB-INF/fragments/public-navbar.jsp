<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-light">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">
            <img src="${pageContext.request.contextPath}/images/logos/ruwana.svg" alt="Ruwana" class="navbar-logo">
            Ruwana
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link ${param.page == 'inicio' ? 'active' : ''}" 
                       href="${pageContext.request.contextPath}/">
                        Inicio
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${param.page == 'eventos' ? 'active' : ''}" 
                       href="${pageContext.request.contextPath}/eventos">
                        Eventos
                    </a>
                </li>
                <c:choose>
                    <c:when test="${not empty sessionScope.usuario}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/app/${sessionScope.usuario.rol.name().toLowerCase()}/dashboard">
                                <i class="bi bi-person-circle"></i> Mi Panel
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/logout">
                                Cerrar Sesión
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/login">
                                Iniciar Sesión
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link btn btn-primary-ruwana text-white ms-2" 
                               href="${pageContext.request.contextPath}/registro/voluntario">
                                Registrarse
                            </a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>
