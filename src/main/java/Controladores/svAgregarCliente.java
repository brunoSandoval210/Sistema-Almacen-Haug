
package Controladores;

import Modelo.Cliente;
import Modelo.Controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "svAgregarCliente", urlPatterns = {"/svAgregarCliente"})
public class svAgregarCliente extends HttpServlet {

    Controller control = new Controller();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Cliente> listaClientes = new ArrayList<>();
        listaClientes = control.traerClientes();

        HttpSession misesion = request.getSession();
        misesion.setAttribute("listaClientes", listaClientes);
        response.sendRedirect("tablaClientes.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String ruc = request.getParameter("ruc");
        String direccion = request.getParameter("direccion");
        int telefono = Integer.parseInt(request.getParameter("telefono"));
        String estado=request.getParameter("estado");
        Cliente cli = new Cliente();
        

        cli.setNombre(nombre);
        cli.setRuc(ruc);
        cli.setDireccion(direccion);
        cli.setTelefono(telefono);
        cli.setEstado(estado);
        control.crearCliente(cli);
        response.sendRedirect("admin.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
