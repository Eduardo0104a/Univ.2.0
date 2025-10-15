package com.dwi.ruwana.servlet;

import com.dwi.ruwana.model.Organizacion;
import com.dwi.ruwana.model.Usuario;
import com.dwi.ruwana.model.enums.RolUsuario;
import com.dwi.ruwana.service.CatalogoService;
import com.dwi.ruwana.service.OrganizacionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Servlet for organization registration
 */
@WebServlet(name = "RegistroOrganizacionServlet", urlPatterns = {"/registro/organizacion"})
public class RegistroOrganizacionServlet extends HttpServlet {
    
    private static final Logger logger = LoggerFactory.getLogger(RegistroOrganizacionServlet.class);
    private OrganizacionService organizacionService;
    private CatalogoService catalogoService;

    @Override
    public void init() throws ServletException {
        this.organizacionService = new OrganizacionService();
        this.catalogoService = new CatalogoService();
        logger.info("RegistroOrganizacionServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Load catalogs for form
        request.setAttribute("tiposDocumento", catalogoService.listarTiposDocumentoActivos());
        
        request.getRequestDispatcher("/WEB-INF/views/registro-organizacion.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // Create Usuario
            Usuario usuario = new Usuario();
            usuario.setEmail(request.getParameter("email"));
            usuario.setNombres(request.getParameter("nombres"));
            usuario.setApellidoPaterno(request.getParameter("apellidoPaterno"));
            usuario.setApellidoMaterno(request.getParameter("apellidoMaterno"));
            usuario.setRol(RolUsuario.ORGANIZACION);
            usuario.setEstadoActivo(false); // Requires admin approval
            usuario.setEstadoVerificado(false);
            
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            
            // Validate passwords match
            if (!password.equals(confirmPassword)) {
                request.setAttribute("error", "Las contraseñas no coinciden");
                request.setAttribute("tiposDocumento", catalogoService.listarTiposDocumentoActivos());
                request.getRequestDispatcher("/WEB-INF/views/registro-organizacion.jsp").forward(request, response);
                return;
            }
            
            // Create Organizacion
            Organizacion organizacion = new Organizacion();
            organizacion.setNombreOrganizacion(request.getParameter("nombreOrganizacion"));
            organizacion.setPais(request.getParameter("pais"));
            organizacion.setDireccion(request.getParameter("direccion"));
            organizacion.setContactoPrincipal(request.getParameter("contactoPrincipal"));
            
            String tipoDocId = request.getParameter("tipoDocumentoId");
            if (tipoDocId != null && !tipoDocId.isEmpty()) {
                catalogoService.buscarTipoDocumentoPorId(Integer.parseInt(tipoDocId))
                    .ifPresent(organizacion::setTipoDocumento);
            }
            
            organizacion.setNumeroDocumento(request.getParameter("numeroDocumento"));
            organizacion.setTelefono(request.getParameter("telefono"));
            organizacion.setCelular(request.getParameter("celular"));
            
            // Legal data
            String constituidaLegal = request.getParameter("constituidaLegalmente");
            organizacion.setConstituidaLegalmente("true".equals(constituidaLegal));
            
            organizacion.setRazonSocial(request.getParameter("razonSocial"));
            
            // Motivation
            organizacion.setMotivoRegistro(request.getParameter("motivacion"));
            organizacion.setDescripcionBeneficiarios(request.getParameter("descripcionBeneficiarios"));
            
            // Register organization
            Organizacion registrada = organizacionService.registrar(usuario, password, organizacion);
            
            logger.info("Organization registered successfully: {}", usuario.getEmail());
            
            // Redirect to confirmation page
            response.sendRedirect(request.getContextPath() + "/registro/organizacion/confirmacion");
            
        } catch (IllegalArgumentException e) {
            logger.warn("Registration validation error: {}", e.getMessage());
            request.setAttribute("error", e.getMessage());
            request.setAttribute("tiposDocumento", catalogoService.listarTiposDocumentoActivos());
            request.getRequestDispatcher("/WEB-INF/views/registro-organizacion.jsp").forward(request, response);
            
        } catch (Exception e) {
            logger.error("Error registering organization", e);
            request.setAttribute("error", "Error al registrar organización. Intenta nuevamente.");
            request.setAttribute("tiposDocumento", catalogoService.listarTiposDocumentoActivos());
            request.getRequestDispatcher("/WEB-INF/views/registro-organizacion.jsp").forward(request, response);
        }
    }
}
