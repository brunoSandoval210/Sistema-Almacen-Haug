
package Controladores;

import Modelo.Controller;
import Modelo.Proveedor;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "svEditarProveedor", urlPatterns = {"/svEditarProveedor"})
public class svEditarProveedor extends HttpServlet {

    Controller control = new Controller();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int id_editar=Integer.parseInt(request.getParameter("id_edit"));
        Proveedor prove=control.traerProveedor(id_editar);
        
        HttpSession misesion=request.getSession();
        misesion.setAttribute("proveEditar", prove);
        
        response.sendRedirect("Proveedor_modificar.jsp");
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nombre = request.getParameter("NombreProve");
        String estado = request.getParameter("EstadoProve");
        int telefono = Integer.parseInt(request.getParameter("telefono"));
        int ruc = Integer.parseInt(request.getParameter("ruc"));
 
        
        Proveedor prove = (Proveedor)request.getSession().getAttribute("proveEditar");

        prove.setNombre(nombre);
        prove.setTelefono(telefono);
        prove.setRuc(ruc);
        prove.setEstado(estado);
        
        
        control.editarProveedor(prove);
        response.sendRedirect("admin.jsp");
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
