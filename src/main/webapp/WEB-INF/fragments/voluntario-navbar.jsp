<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
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
                    <a class="nav-link ${param.page == 'dashboard' ? 'active' : ''}" 
                       href="${pageContext.request.contextPath}/app/voluntario/dashboard">
                        <i class="bi bi-speedometer2"></i> Mi Panel
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${param.page == 'eventos' ? 'active' : ''}" 
                       href="${pageContext.request.contextPath}/eventos">
                        <i class="bi bi-calendar-event"></i> Eventos
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${param.page == 'inscripciones' ? 'active' : ''}" 
                       href="${pageContext.request.contextPath}/app/voluntario/inscripciones">
                        <i class="bi bi-bookmark-check"></i> Mis Inscripciones
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${param.page == 'donaciones' ? 'active' : ''}" 
                       href="${pageContext.request.contextPath}/app/voluntario/donaciones">
                        <i class="bi bi-gift"></i> Mis Donaciones
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${param.page == 'certificados' ? 'active' : ''}" 
                       href="${pageContext.request.contextPath}/app/voluntario/certificados">
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
