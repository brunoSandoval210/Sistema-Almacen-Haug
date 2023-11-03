package Controladores;

import Modelo.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "svAgregarOrden", urlPatterns = {"/svAgregarOrden"})
public class svAgregarOrden extends HttpServlet {
    Controller control=new Controller();
    List<DetalleOrden> listaorden = new ArrayList<>();
    int item;
    double totalPagar = 0.0;
    DetalleOrden ord;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String accion = request.getParameter("accion");
        List<DetalleOrden> listaProductosEnCarrito = new ArrayList<>();
        switch (accion) {
            case "Comprar":
                // Inicializar el total a pagar y obtener el ID del producto
                totalPagar = 0.0;
                int idp = Integer.parseInt(request.getParameter("id"));
                // Obtener información del producto desde el controlador
                Producto prod = control.traerProducto(idp);
                // Incrementar el número de ítems en el carrito
                item = item + 1;
                // Crear un nuevo detalle de orden
                ord = new DetalleOrden();
                ord.setItem(item);

                // Crear una lista de productos para la orden y agregar el producto actual
                List<Producto> productosDeLaOrden = new ArrayList<>();
                productosDeLaOrden.add(prod);
                ord.setListaProducto(productosDeLaOrden);

                // Configurar información del producto en el detalle de orden
                ord.setNombreOrd(prod.getNombre());
                ord.setDescripcion(prod.getDescripcion());
                ord.setPrecioOrden(prod.getPrecio());
                int cantidad = 1;
                ord.setCantidad(cantidad);
                ord.setSubtotal(cantidad * prod.getPrecio());

                // Agregar el detalle de orden al carrito
                listaorden.add(ord);

                // Calcular el nuevo total a pagar sumando los subtotales de los elementos en el carrito
                for (int i = 0; i < listaorden.size(); i++) {
                    totalPagar = totalPagar + listaorden.get(i).getSubtotal();
                }

                // Configurar atributos de la solicitud para mostrar en la vista
                request.setAttribute("contador", listaorden.size());
                request.setAttribute("totalPagar", totalPagar);

                // Almacenar la lista actualizada de productos en el carrito en la sesión de usuario
                session = request.getSession();
                session.setAttribute("listaProductos1", listaorden);

                // Redirigir a la página "Orden_agregar.jsp" para mostrar el carrito de compras
                request.getRequestDispatcher("Orden_agregar.jsp").forward(request, response);
                break;

            case "AgregarCarrito":
                // Inicialización de variables
                int pos = 0; 
                cantidad = 1; 
                idp = Integer.parseInt(request.getParameter("id")); 
                prod = control.traerProducto(idp); // 

                if (listaorden.size() > 0) {
                    // Verificar si el carrito de compras ya contiene productos
                    for (int i = 0; i < listaorden.size(); i++) {
                        // Iterar a través de los elementos en el carrito
                        if (idp == listaorden.get(i).getListaProducto().get(0).getId()) {
                            // Comprobar si el producto ya está en el carrito comparando IDs
                            pos = i; // Almacenar la posición del producto en el carrito si se encuentra
                        }
                    }

                    if (idp == listaorden.get(pos).getListaProducto().get(0).getId()) {
                        // Si el producto ya está en el carrito
                        cantidad = listaorden.get(pos).getCantidad() + cantidad; // Actualizar la cantidad
                        double subtotal = listaorden.get(pos).getPrecioOrden() * cantidad; // Calcular el subtotal
                        listaorden.get(pos).setCantidad(cantidad); // Actualizar la cantidad en el carrito
                        listaorden.get(pos).setSubtotal(subtotal); // Actualizar el subtotal en el carrito
                    } else {
                        // Si el producto no está en el carrito
                        item = item + 1; // Incrementar el número de ítems en el carrito
                        ord = new DetalleOrden(); // Crear un nuevo detalle de orden
                        ord.setItem(item); // Establecer el número de ítem del detalle

                        productosDeLaOrden = new ArrayList<>();
                        productosDeLaOrden.add(prod);

                        ord.setListaProducto(productosDeLaOrden); // Establecer la lista de productos en el detalle

                        ord.setNombreOrd(prod.getNombre()); // Establecer el nombre del producto
                        ord.setDescripcion(prod.getDescripcion()); // Establecer la descripción del producto
                        ord.setPrecioOrden(prod.getPrecio()); // Establecer el precio del producto
                        ord.setCantidad(cantidad); // Establecer la cantidad
                        ord.setSubtotal(cantidad * prod.getPrecio()); // Calcular y establecer el subtotal
                        listaorden.add(ord); // Agregar el detalle de orden al carrito
                    }
                } else {
                    // Si el carrito de compras está vacío
                    item = item + 1; // Incrementar el número de ítems en el carrito
                    ord = new DetalleOrden(); // Crear un nuevo detalle de orden
                    ord.setItem(item); // Establecer el número de ítem del detalle

                    productosDeLaOrden = new ArrayList<>();
                    productosDeLaOrden.add(prod);

                    ord.setListaProducto(productosDeLaOrden); // Establecer la lista de productos en el detalle

                    ord.setNombreOrd(prod.getNombre()); // Establecer el nombre del producto
                    ord.setDescripcion(prod.getDescripcion()); // Establecer la descripción del producto
                    ord.setPrecioOrden(prod.getPrecio()); // Establecer el precio del producto
                    ord.setCantidad(cantidad); // Establecer la cantidad
                    ord.setSubtotal(cantidad * prod.getPrecio()); // Calcular y establecer el subtotal
                    listaorden.add(ord); // Agregar el detalle de orden al carrito
                }

                // Actualizar el contador de productos en el carrito en la solicitud
                request.setAttribute("contador", listaorden.size());

                // Obtener la sesión de usuario
                session = request.getSession();

                // Almacenar la lista actualizada de productos en el carrito en la sesión
                session.setAttribute("listaProductos1", listaorden);

                // Redirigir la solicitud a la acción "admin" del servlet "svAgregarOrden"
                request.getRequestDispatcher("svAgregarOrden?accion=admin").forward(request, response);
                break;

            case "Delete":
                // Obtener el ID del producto a eliminar de los parámetros de la solicitud
                int idproducto = Integer.parseInt(request.getParameter("idp"));

                // Iterar a través de los elementos en el carrito
                for (int i = 0; i < listaorden.size(); i++) {
                    DetalleOrden ordenActual = listaorden.get(i);
                    // Verificar si el ID del producto en el carrito coincide con el ID a eliminar
                    if (ordenActual.getListaProducto().get(0).getId() == idproducto) {
                        // Si hay coincidencia, eliminar el detalle de orden del carrito
                        listaorden.remove(i);
                        break; // Salir del bucle una vez que se haya eliminado el producto
                    }
                }
                break;

            case "ActualizarCantidad":
                // Obtener el ID del producto y la nueva cantidad desde los parámetros de la solicitud
                String idpParam = request.getParameter("idp");
                String cantidadParam = request.getParameter("Cantidad");

                // Verificar si los parámetros no son nulos ni están vacíos
                //isEmpty() se utiliza para comprobar si una cadena no contiene ningún carácter.
                if (idpParam != null && !idpParam.isEmpty() && cantidadParam != null && !cantidadParam.isEmpty()) {
                    int idpro = Integer.parseInt(idpParam);
                    int cant = Integer.parseInt(cantidadParam);
                    // Iterar a través de los elementos en el carrito
                    for (int i = 0; i < listaorden.size(); i++) {
                        DetalleOrden ordenActual = listaorden.get(i);
                        // Verificar si el ID del producto en el carrito coincide con el ID a actualizar
                        if (ordenActual.getListaProducto().get(0).getId() == idpro) {
                            // Actualizar la cantidad en el detalle de orden
                            listaorden.get(i).setCantidad(cant);
                            // Calcular el nuevo subtotal basado en la cantidad actualizada
                            double st = listaorden.get(i).getPrecioOrden() * cant;
                            // Actualizar el subtotal en el detalle de orden
                            listaorden.get(i).setSubtotal(st);
                        }
                    }
                } else {
                    // Si los parámetros están incompletos o ausentes, redirigir a la acción "Carrito"
                    request.getRequestDispatcher("svAgregarOrden?accion=Carrito").forward(request, response);
                }
                break;

            case "AgregarCliente":
                // Obtener el ID del cliente desde los parámetros de la solicitud
                int idCli = Integer.parseInt(request.getParameter("idCli"));

                // Obtener información del cliente utilizando el controlador
                Cliente cliente = control.traerCliente(idCli);

                if (cliente != null) {
                    // Si se encontró el cliente
                    // Crear un objeto de orden y establecer el cliente en la orden
                    Orden orden = new Orden();
                    orden.setCli(cliente);

                    // Obtener la sesión de usuario
                    session = request.getSession();

                    // Almacenar el cliente seleccionado y la orden en la sesión
                    session.setAttribute("clienteSeleccionado", cliente);
                    session.setAttribute("orden", orden);

                    // Redirigir a la acción "Carrito"
                    request.getRequestDispatcher("svAgregarOrden?accion=Carrito").forward(request, response);
                } else {
                    // Si no se encontró el cliente, redirigir a la acción "Carrito"
                    request.getRequestDispatcher("svAgregarOrden?accion=Carrito").forward(request, response);
                }
                break;

            case "ActualizarEstado":
                String estado = request.getParameter("estado");

                if (estado != null) {
                    Orden orden = new Orden();

                    orden.setEstado(estado);

                    session = request.getSession();
                    session.setAttribute("estado", estado);
                    session.setAttribute("orden", orden);

                    request.getRequestDispatcher("svAgregarOrden?accion=Carrito").forward(request, response);
                } else {
                    request.getRequestDispatcher("svAgregarOrden?accion=Carrito").forward(request, response);
                }
                break;

            case "Carrito":
                // Inicialización de la variable totalPagar a 0.0
                totalPagar = 0.0;

                // Obtener la sesión de usuario donde se almacenan los productos en el carrito
                HttpSession carritoSession = request.getSession();

                // Obtener la lista de productos en el carrito desde la sesión
                listaProductosEnCarrito = (List<DetalleOrden>) carritoSession.getAttribute("listaProductos1");

                if (listaProductosEnCarrito != null) {
                    // Si la lista de productos en el carrito no es nula (es decir, hay productos en el carrito)

                    // Establecer los productos en la solicitud para mostrarlos en la página
                    request.setAttribute("orden", listaProductosEnCarrito);

                    // Calcular el total a pagar sumando los subtotales de los productos en el carrito
                    for (int i = 0; i < listaProductosEnCarrito.size(); i++) {
                        totalPagar = totalPagar + listaProductosEnCarrito.get(i).getSubtotal();
                    }

                    // Establecer el total a pagar en la solicitud para mostrarlo en la página
                    request.setAttribute("totalPagar", totalPagar);

                    // Redirigir la solicitud a la página "Orden_agregar.jsp" para mostrar el contenido del carrito
                    request.getRequestDispatcher("Orden_agregar.jsp").forward(request, response);
                } else {
                    // Si la lista de productos en el carrito es nula (es decir, el carrito está vacío)

                    // Redirigir la solicitud directamente a la página "Orden_agregar.jsp"
                    // Esto puede ocurrir si el usuario visita la página del carrito sin agregar productos previamente
                    request.getRequestDispatcher("Orden_agregar.jsp").forward(request, response);
                }
                break;

            case "GenerarCompra":
                session = request.getSession();
                listaProductosEnCarrito = (List<DetalleOrden>) session.getAttribute("listaProductos1");

                // Crear detalles de la orden
                List<DetalleOrden> detallesOrden = new ArrayList<>();
                double totalMonto = 0.0;

                for (DetalleOrden detalle : listaProductosEnCarrito) {
                    totalMonto += detalle.getSubtotal();

                    // Configura la relación entre el detalle y la orden
                    detalle.setOrden(null); // Para evitar una referencia circular
                    detallesOrden.add(detalle);
                    control.crearDetalleOrden(detalle);
                    // Después de crear la orden y detalles de la orden en la base de datos
                    // Actualizar el stock de los productos comprados
                    for (DetalleOrden detalles : detallesOrden) {
                        int idProducto = detalles.getListaProducto().get(0).getId();
                        int cantidadComprada = detalles.getCantidad();
                        control.actualizarStock(idProducto, cantidadComprada);
                    }

                }

                // Crea la orden
                Orden orden = new Orden();
                Cliente clienteSeleccionado = (Cliente) session.getAttribute("clienteSeleccionado");
                estado = (String) session.getAttribute("estado");
                orden.setCli(clienteSeleccionado);
                orden.setDetallesOrden(detallesOrden);
                orden.setMonto(totalMonto);
                orden.setEstado(estado);

                // Limpia la lista de productos en el carrito
                listaProductosEnCarrito.clear();
                session.setAttribute("listaProductos1", listaProductosEnCarrito);

                // Persiste la orden
                control.crearOrden(orden);

                request.getRequestDispatcher("mensaje.jsp").forward(request, response);
                break;
            default:
                // Obtener la lista de productos
                List<Producto> productos = control.traerProductos();
                request.setAttribute("productos", productos);
                request.getRequestDispatcher("admin.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
   
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
