
package Controladores;

import Modelo.Controller;
import Modelo.Producto;
import Modelo.Proveedor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "svAgregarProveedor", urlPatterns = {"/svAgregarProveedor"})
public class svAgregarProveedor extends HttpServlet {

    Controller control = new Controller();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Proveedor> listaProveedores = new ArrayList<>();
        listaProveedores = control.traerProveedores();

        HttpSession misesion = request.getSession();
        misesion.setAttribute("listaProveedores", listaProveedores);
        response.sendRedirect("tablaProveedores.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("NombreProve");
        int telefono = Integer.parseInt(request.getParameter("telefono"));
        int ruc = Integer.parseInt(request.getParameter("rucProve"));
        String estado = request.getParameter("EstadoProve");
        Proveedor prove = new Proveedor();

        prove.setNombre(nombre);
        prove.setTelefono(telefono);
        prove.setRuc(ruc);
        prove.setEstado(estado);

        control.crearProveedor(prove);
        response.sendRedirect("admin.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
