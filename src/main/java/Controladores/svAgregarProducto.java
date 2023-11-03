
package Controladores;

import Modelo.Controller;
import Modelo.Producto;
import Modelo.Proveedor;
import Persistencia.ProductoJpaController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "svAgregarProducto", urlPatterns = {"/svAgregarProducto"})
public class svAgregarProducto extends HttpServlet {

    Controller control = new Controller();


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        List<Producto> listaProductos = control.encontrarTodosProductosConProveedor(); 
        HttpSession misesion = request.getSession();
        misesion.setAttribute("listaProductos", listaProductos);
        response.sendRedirect("tablaProductos.jsp");
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

        Producto prod = new Producto();
        Proveedor proveedorObj = control.buscarProveedorPorNombre(proveedor);
        
        
        
        prod.setProv(proveedorObj);
        prod.setNombre(nombre);
        prod.setPrecio(precio);
        prod.setCantidad(stock);
        prod.setImagen(imagen);
        prod.setDescripcion(descripcion);
        prod.setEstado(estado);
        
        control.crearProducto(prod);
        response.sendRedirect("admin.jsp");
      
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
