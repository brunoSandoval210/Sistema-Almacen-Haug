
package Controladores;

import Modelo.Controller;
import Modelo.Producto;
import Modelo.Proveedor;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "svEditarProducto", urlPatterns = {"/svEditarProducto"})
public class svEditarProducto extends HttpServlet {

    Controller control = new Controller();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int id_editar=Integer.parseInt(request.getParameter("id_edit"));
        Producto prod=control.traerProducto(id_editar);
        
        HttpSession misesion=request.getSession();
        misesion.setAttribute("prodEditar", prod);
        
        response.sendRedirect("Producto_modificar.jsp");
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String proveedor = request.getParameter("ProveedorProd");
        String nombre = request.getParameter("NombreProd");
        double precio = Double.parseDouble(request.getParameter("PrecioProd"));
        int stock = Integer.parseInt(request.getParameter("StockProd"));
        String imagen = request.getParameter("ImagenProd");
        String descripcion = request.getParameter("DescripcionProd");
        String estado = request.getParameter("estado");
        
        Producto prod = (Producto)request.getSession().getAttribute("prodEditar");
        Proveedor proveedorObj = control.buscarProveedorPorNombre(proveedor);
        prod.setProv(proveedorObj);
        prod.setNombre(nombre);
        prod.setPrecio(precio);
        prod.setCantidad(stock);
        prod.setImagen(imagen);
        prod.setDescripcion(descripcion);
        prod.setEstado(estado);
        
        control.editarProducto(prod);
        response.sendRedirect("admin.jsp");
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
