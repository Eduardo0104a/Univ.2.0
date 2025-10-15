<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Ruwana - Conectando voluntarios con organizaciones sociales para hacer la diferencia">
    <meta name="keywords" content="voluntariado, donaciones, eventos sociales, organizaciones, ayuda social">
    <title>Ruwana - Plataforma de Gestión de Voluntariado</title>
    <jsp:include page="/WEB-INF/fragments/common-head.jsp" />
    <style>
        .hero-landing {
            background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-dark) 100%);
            color: white;
            padding: 100px 0;
            position: relative;
            overflow: hidden;
        }
        
        .hero-landing::before {
            content: '';
            position: absolute;
            top: -50%;
            right: -10%;
            width: 500px;
            height: 500px;
            background: rgba(255, 255, 255, 0.1);
            border-radius: 50%;
        }
        
        .hero-landing::after {
            content: '';
            position: absolute;
            bottom: -30%;
            left: -5%;
            width: 400px;
            height: 400px;
            background: rgba(255, 255, 255, 0.05);
            border-radius: 50%;
        }
        
        .feature-card {
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            height: 100%;
        }
        
        .feature-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 10px 30px rgba(185, 28, 80, 0.2);
        }
        
        .feature-icon {
            width: 80px;
            height: 80px;
            background: linear-gradient(135deg, var(--primary-light) 0%, var(--primary-color) 100%);
            border-radius: 20px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 2rem;
            color: white;
            margin: 0 auto 1.5rem;
        }
        
        .stats-section {
            background: var(--background-cream);
            padding: 60px 0;
        }
        
        .stat-card {
            text-align: center;
            padding: 2rem;
        }
        
        .stat-number {
            font-size: 3rem;
            font-weight: 700;
            color: var(--primary-color);
            margin-bottom: 0.5rem;
        }
        
        .cta-section {
            background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-dark) 100%);
            color: white;
            padding: 80px 0;
            margin-top: 60px;
        }
        
        .btn-cta {
            padding: 15px 40px;
            font-size: 1.1rem;
            font-weight: 600;
            border-radius: 50px;
            transition: all 0.3s ease;
        }
        
        .btn-cta:hover {
            transform: scale(1.05);
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/fragments/public-navbar.jsp">
        <jsp:param name="page" value="inicio" />
    </jsp:include>

    <!-- Hero Section -->
    <section class="hero-landing">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-lg-6" style="position: relative; z-index: 1;">
                    <h1 class="display-3 fw-bold mb-4">
                        Conectamos Corazones que Quieren Ayudar
                    </h1>
                    <p class="lead mb-4">
                        Únete a la red de voluntariado más grande y ayuda a transformar vidas. 
                        Encuentra eventos, dona y haz la diferencia en tu comunidad.
                    </p>
                    <div class="d-flex gap-3 flex-wrap">
                        <a href="${pageContext.request.contextPath}/registro/voluntario" 
                           class="btn btn-light btn-cta">
                            <i class="bi bi-person-plus"></i> Ser Voluntario
                        </a>
                        <a href="${pageContext.request.contextPath}/registro/organizacion" 
                           class="btn btn-outline-light btn-cta">
                            <i class="bi bi-buildings"></i> Registrar Organización
                        </a>
                    </div>
                </div>
                <div class="col-lg-6 text-center mt-5 mt-lg-0" style="position: relative; z-index: 1;">
                    <img src="${pageContext.request.contextPath}/images/logos/ruwana.svg" 
                         alt="Ruwana" 
                         style="max-width: 400px; width: 100%; filter: drop-shadow(0 20px 40px rgba(0,0,0,0.3));">
                </div>
            </div>
        </div>
    </section>

    <!-- Stats Section -->
    <section class="stats-section">
        <div class="container">
            <div class="row">
                <div class="col-md-4">
                    <div class="stat-card">
                        <div class="stat-number">
                            <i class="bi bi-people-fill"></i>
                        </div>
                        <h3 class="mb-2">Voluntarios</h3>
                        <p class="text-muted">Personas comprometidas con el cambio social</p>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="stat-card">
                        <div class="stat-number">
                           <i class="bi bi-buildings"></i>
                        </div>
                        <h3 class="mb-2">Organizaciones</h3>
                        <p class="text-muted">ONGs y fundaciones activas en la plataforma</p>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="stat-card">
                        <div class="stat-number">
                            <i class="bi bi-calendar-check"></i>
                        </div>
                        <h3 class="mb-2">Eventos</h3>
                        <p class="text-muted">Oportunidades para hacer la diferencia</p>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Features Section -->
    <section class="py-5">
        <div class="container">
            <div class="text-center mb-5">
                <h2 class="display-5 fw-bold mb-3">¿Por Qué Ruwana?</h2>
                <p class="lead text-muted">La plataforma integral para gestionar voluntariado</p>
            </div>
            
            <div class="row g-4">
                <div class="col-md-4">
                    <div class="card feature-card border-0 shadow-sm p-4">
                        <div class="feature-icon">
                            <i class="bi bi-calendar-event"></i>
                        </div>
                        <h4 class="text-center mb-3">Eventos Sociales</h4>
                        <p class="text-muted text-center">
                            Descubre y participa en eventos de voluntariado organizados por ONGs verificadas.
                        </p>
                    </div>
                </div>
                
                <div class="col-md-4">
                    <div class="card feature-card border-0 shadow-sm p-4">
                        <div class="feature-icon">
                            <i class="bi bi-award"></i>
                        </div>
                        <h4 class="text-center mb-3">Certificados</h4>
                        <p class="text-muted text-center">
                            Obtén certificados digitales por tu participación en actividades de voluntariado.
                        </p>
                    </div>
                </div>
                
                <div class="col-md-4">
                    <div class="card feature-card border-0 shadow-sm p-4">
                        <div class="feature-icon">
                            <i class="bi bi-gift"></i>
                        </div>
                        <h4 class="text-center mb-3">Donaciones</h4>
                        <p class="text-muted text-center">
                            Apoya económicamente a las organizaciones y proyectos que más te interesan.
                        </p>
                    </div>
                </div>
                
                <div class="col-md-4">
                    <div class="card feature-card border-0 shadow-sm p-4">
                        <div class="feature-icon">
                            <i class="bi bi-shield-check"></i>
                        </div>
                        <h4 class="text-center mb-3">Organizaciones Verificadas</h4>
                        <p class="text-muted text-center">
                            Todas las organizaciones son revisadas y aprobadas por nuestro equipo.
                        </p>
                    </div>
                </div>
                
                <div class="col-md-4">
                    <div class="card feature-card border-0 shadow-sm p-4">
                        <div class="feature-icon">
                            <i class="bi bi-graph-up"></i>
                        </div>
                        <h4 class="text-center mb-3">Seguimiento</h4>
                        <p class="text-muted text-center">
                            Gestiona tus participaciones, donaciones y certificados en un solo lugar.
                        </p>
                    </div>
                </div>
                
                <div class="col-md-4">
                    <div class="card feature-card border-0 shadow-sm p-4">
                        <div class="feature-icon">
                            <i class="bi bi-bell"></i>
                        </div>
                        <h4 class="text-center mb-3">Notificaciones</h4>
                        <p class="text-muted text-center">
                            Recibe alertas sobre nuevos eventos y actualizaciones importantes.
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- How It Works Section -->
    <section class="py-5" style="background: var(--background-light);">
        <div class="container">
            <div class="text-center mb-5">
                <h2 class="display-5 fw-bold mb-3">¿Cómo Funciona?</h2>
                <p class="lead text-muted">Comienza a hacer la diferencia en 3 simples pasos</p>
            </div>
            
            <div class="row g-4">
                <div class="col-md-4 text-center">
                    <div class="mb-4">
                        <div class="d-inline-flex align-items-center justify-content-center" 
                             style="width: 80px; height: 80px; background: var(--primary-color); border-radius: 50%; color: white; font-size: 2rem; font-weight: bold;">
                            1
                        </div>
                    </div>
                    <h4>Regístrate Gratis</h4>
                    <p class="text-muted">Crea tu cuenta como voluntario u organización en menos de 2 minutos.</p>
                </div>
                
                <div class="col-md-4 text-center">
                    <div class="mb-4">
                        <div class="d-inline-flex align-items-center justify-content-center" 
                             style="width: 80px; height: 80px; background: var(--primary-color); border-radius: 50%; color: white; font-size: 2rem; font-weight: bold;">
                            2
                        </div>
                    </div>
                    <h4>Encuentra Eventos</h4>
                    <p class="text-muted">Explora eventos de voluntariado que se alineen con tus intereses.</p>
                </div>
                
                <div class="col-md-4 text-center">
                    <div class="mb-4">
                        <div class="d-inline-flex align-items-center justify-content-center" 
                             style="width: 80px; height: 80px; background: var(--primary-color); border-radius: 50%; color: white; font-size: 2rem; font-weight: bold;">
                            3
                        </div>
                    </div>
                    <h4>Haz la Diferencia</h4>
                    <p class="text-muted">Participa, dona y obtén certificados por tu impacto social.</p>
                </div>
            </div>
        </div>
    </section>

    <!-- CTA Section -->
    <section class="cta-section">
        <div class="container text-center">
            <h2 class="display-4 fw-bold mb-4">¿Listo para Comenzar?</h2>
            <p class="lead mb-5">Únete a nuestra comunidad y transforma vidas hoy mismo</p>
            <div class="d-flex gap-3 justify-content-center flex-wrap">
                <a href="${pageContext.request.contextPath}/eventos" 
                   class="btn btn-light btn-lg btn-cta">
                    <i class="bi bi-calendar-event"></i> Explorar Eventos
                </a>
                <a href="${pageContext.request.contextPath}/login" 
                   class="btn btn-outline-light btn-lg btn-cta">
                    <i class="bi bi-box-arrow-in-right"></i> Iniciar Sesión
                </a>
            </div>
        </div>
    </section>

    <!-- Footer -->
    <footer class="bg-dark text-white py-4">
        <div class="container">
            <div class="row">
                <div class="col-md-6 text-center text-md-start mb-3 mb-md-0">
                    <p class="mb-0">
                        <img src="${pageContext.request.contextPath}/images/logos/ruwana.svg" 
                             alt="Ruwana" 
                             style="height: 30px; vertical-align: middle; margin-right: 10px;">
                        &copy; 2025 Ruwana. Todos los derechos reservados.
                    </p>
                </div>
                <div class="col-md-6 text-center text-md-end">
                    <a href="#" class="text-white text-decoration-none me-3">Términos y Condiciones</a>
                    <a href="#" class="text-white text-decoration-none">Política de Privacidad</a>
                </div>
            </div>
        </div>
    </footer>

    <jsp:include page="/WEB-INF/fragments/common-scripts.jsp" />
</body>
</html>