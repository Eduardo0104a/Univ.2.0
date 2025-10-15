package com.dwi.ruwana.servlet;

import com.dwi.ruwana.dto.UsuarioDTO;
import com.dwi.ruwana.mapper.UsuarioMapper;
import com.dwi.ruwana.model.Usuario;
import com.dwi.ruwana.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

/**
 * Servlet for user login
 * Handles authentication and session creation
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    
    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    private UsuarioService usuarioService;

    @Override
    public void init() throws ServletException {
        this.usuarioService = new UsuarioService();
        logger.info("LoginServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // If already logged in, redirect to dashboard
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("usuario") != null) {
            UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
            redirectToDashboard(response, usuario, request.getContextPath());
            return;
        }
        
        // Show login page
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String redirect = request.getParameter("redirect");
        
        // Validate inputs
        if (email == null || email.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Email y contraseña son requeridos");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }
        
        try {
            // Authenticate user
            Optional<Usuario> usuarioOpt = usuarioService.autenticar(email.trim(), password);
            
            if (usuarioOpt.isEmpty()) {
                logger.warn("Login failed for email: {}", email);
                request.setAttribute("error", "Email o contraseña incorrectos");
                request.setAttribute("email", email);
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
                return;
            }
            
            Usuario usuario = usuarioOpt.get();
            
            // Check if account is active
            if (!usuario.getEstadoActivo()) {
                logger.warn("Login attempt for inactive account: {}", email);
                request.setAttribute("error", "Tu cuenta está inactiva. Contacta al administrador.");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
                return;
            }
            
            // Create session
            HttpSession session = request.getSession(true);
            session.setMaxInactiveInterval(3600); // 1 hour
            
            // Store user DTO in session (not entity with password)
            UsuarioDTO usuarioDTO = UsuarioMapper.toDTO(usuario);
            session.setAttribute("usuario", usuarioDTO);
            session.setAttribute("rol", usuario.getRol());
            
            logger.info("User logged in successfully: {} (Role: {})", email, usuario.getRol());
            
            // Redirect to appropriate dashboard or requested page
            if (redirect != null && !redirect.isEmpty()) {
                response.sendRedirect(request.getContextPath() + redirect);
            } else {
                redirectToDashboard(response, usuarioDTO, request.getContextPath());
            }
            
        } catch (Exception e) {
            logger.error("Error during login for email: {}", email, e);
            request.setAttribute("error", "Error al procesar el login. Intenta nuevamente.");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
    
    /**
     * Redirect user to appropriate dashboard based on role
     */
    private void redirectToDashboard(HttpServletResponse response, UsuarioDTO usuario, String contextPath)
            throws IOException {
        
        String dashboardUrl;
        switch (usuario.getRol()) {
            case ADMINISTRADOR:
                dashboardUrl = contextPath + "/app/admin/dashboard";
                break;
            case ORGANIZACION:
                dashboardUrl = contextPath + "/app/organizacion/dashboard";
                break;
            case VOLUNTARIO:
                dashboardUrl = contextPath + "/app/voluntario/dashboard";
                break;
            default:
                dashboardUrl = contextPath + "/login";
                break;
        }
        
        response.sendRedirect(dashboardUrl);
    }
}
