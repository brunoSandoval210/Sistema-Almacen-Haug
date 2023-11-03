
package Controladores;

import Modelo.Cliente;
import Modelo.Controller;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "svEditarCliente", urlPatterns = {"/svEditarCliente"})
public class svEditarCliente extends HttpServlet {

    Controller control = new Controller();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id_editar = Integer.parseInt(request.getParameter("id_edit"));
        Cliente cli = control.traerCliente(id_editar);

        HttpSession misesion = request.getSession();
        misesion.setAttribute("cliEditar", cli);

        response.sendRedirect("Cliente_modificar.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String ruc = request.getParameter("ruc");
        String direccion = request.getParameter("direccion");
        int telefono = Integer.parseInt(request.getParameter("telefono"));
        String estado=request.getParameter("estado");
        
        Cliente cli = (Cliente)request.getSession().getAttribute("cliEditar");
        
        cli.setNombre(nombre);
        cli.setRuc(ruc);
        cli.setDireccion(direccion);
        cli.setTelefono(telefono);
        cli.setEstado(estado);
        control.editarCliente(cli);
        response.sendRedirect("admin.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
