
package Controladores;

import Modelo.Controller;
import Modelo.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "svEditarUsuario", urlPatterns = {"/svEditarUsuario"})
public class svEditarUsuario extends HttpServlet {

    Controller control = new Controller();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id_editar = Integer.parseInt(request.getParameter("id_edit"));
        Usuario usu = control.traerUsuario(id_editar);

        HttpSession misesion = request.getSession();
        misesion.setAttribute("usuEditar", usu);

        response.sendRedirect("Usuario_modificar.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String idIngreso = request.getParameter("idIngreso");
        String contrasena = request.getParameter("contrasena");
        String tipo = request.getParameter("tipo");
        String estado=request.getParameter("estado");
        Usuario usu = (Usuario)request.getSession().getAttribute("usuEditar");
        
        usu.setNombre(nombre);
        usu.setIdIngreso(idIngreso);
        usu.setContrasena(contrasena);
        usu.setTipo(tipo);
        usu.setEstado(estado);
        
        control.editarUsuario(usu);
        response.sendRedirect("admin.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
