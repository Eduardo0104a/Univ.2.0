package com.dwi.ruwana.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Authentication filter to protect secured pages
 * Redirects unauthenticated users to login page
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"/app/*"})
public class AuthFilter implements Filter {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);
    
    // Public paths that don't require authentication
    private static final List<String> PUBLIC_PATHS = Arrays.asList(
        "/login",
        "/registro",
        "/eventos-publicos",
        "/css/",
        "/js/",
        "/images/"
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("AuthFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        
        // Check if path is public
        if (isPublicPath(path)) {
            chain.doFilter(request, response);
            return;
        }
        
        // Check if user is authenticated
        HttpSession session = httpRequest.getSession(false);
        
        if (session == null || session.getAttribute("usuario") == null) {
            logger.warn("Unauthorized access attempt to: {}", path);
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login?redirect=" + path);
            return;
        }
        
        // User is authenticated, continue
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.info("AuthFilter destroyed");
    }
    
    /**
     * Check if path is public (doesn't require authentication)
     */
    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }
}
