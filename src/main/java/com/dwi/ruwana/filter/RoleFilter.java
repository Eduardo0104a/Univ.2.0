package com.dwi.ruwana.filter;

import com.dwi.ruwana.dto.UsuarioDTO;
import com.dwi.ruwana.model.enums.RolUsuario;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Role-based access control filter
 * Restricts access to pages based on user role
 */
@WebFilter(filterName = "RoleFilter", urlPatterns = {"/app/*"})
public class RoleFilter implements Filter {
    
    private static final Logger logger = LoggerFactory.getLogger(RoleFilter.class);
    
    // Map paths to required roles
    private static final Map<String, RolUsuario[]> ROLE_PATHS = new HashMap<>();
    
    static {
        // Admin only paths
        ROLE_PATHS.put("/app/admin", new RolUsuario[]{RolUsuario.ADMINISTRADOR});
        
        // Organization only paths
        ROLE_PATHS.put("/app/organizacion", new RolUsuario[]{RolUsuario.ORGANIZACION});
        
        // Volunteer only paths
        ROLE_PATHS.put("/app/voluntario", new RolUsuario[]{RolUsuario.VOLUNTARIO});
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("RoleFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        HttpSession session = httpRequest.getSession(false);
        
        // If no session, AuthFilter will handle it
        if (session == null || session.getAttribute("usuario") == null) {
            chain.doFilter(request, response);
            return;
        }
        
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        
        // Check if path requires specific role
        if (requiresRoleCheck(path)) {
            RolUsuario[] allowedRoles = getAllowedRoles(path);
            
            if (!hasRequiredRole(usuario.getRol(), allowedRoles)) {
                logger.warn("Access denied for user {} (role: {}) to path: {}", 
                           usuario.getEmail(), usuario.getRol(), path);
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                return;
            }
        }
        
        // User has required role, continue
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.info("RoleFilter destroyed");
    }
    
    /**
     * Check if path requires role validation
     */
    private boolean requiresRoleCheck(String path) {
        return ROLE_PATHS.keySet().stream().anyMatch(path::startsWith);
    }
    
    /**
     * Get allowed roles for a path
     */
    private RolUsuario[] getAllowedRoles(String path) {
        for (Map.Entry<String, RolUsuario[]> entry : ROLE_PATHS.entrySet()) {
            if (path.startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        return new RolUsuario[0];
    }
    
    /**
     * Check if user has required role
     */
    private boolean hasRequiredRole(RolUsuario userRole, RolUsuario[] allowedRoles) {
        for (RolUsuario role : allowedRoles) {
            if (role == userRole) {
                return true;
            }
        }
        return false;
    }
}
