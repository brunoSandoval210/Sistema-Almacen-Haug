<%@page import="java.util.ArrayList"%>
<%@page import="Modelo.Controller"%>
<%@page import="Modelo.DetalleOrden"%>
<%@page import="Modelo.Producto"%>
<%@page import="Modelo.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
String userType = (String) session.getAttribute("tipo"); // Obtiene el tipo de usuario de la sesión
if (userType != null) {
    
    if (userType.equals("Bodeguero")) {%>
    <%@ include file="/include/admin_Bodeguero.jsp" %>
    <%} else if (userType.equals("Personal de Inventario")) {%>
    <%@ include file="/include/admin_PersonalInventario.jsp" %>
   <% } else {%>
        <%@ include file="/include/admin_header.jsp" %>
    <%}
} else {
    
}
%>

<div class="container mt-5">
    <h1>Realizar orden</h1>
    <div class="row ">
        <div class="col-sm-8">
            <table class="table table-hover" >
                <thead>
                    <tr>
                        <th>ORDENAR</th>
                        <th>       </th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Productos</td>
                        <td>
                            <div>
                                <select type="text" class="Productos form-select" aria-label="Default select example" name="Productos" required>
                                    <option selected>Seleccionar</option>
                                    <% Controller control = new Controller();
                                        List<Producto> listaProductos = new ArrayList<>();
                                        listaProductos = control.traerProductos();

                                        for (Producto prod : listaProductos) {
                                        if(prod.getEstado().equals("Activo")){
                                    %>
                                    <option class="idProducto" value="<%= prod.getId()%>"><%= prod.getNombre()%></option>
                                    <%
                                        }}
                                    %>
                                </select>
                            </div> 
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>Cliente</td>
                        <td>
                            <div>
                                <select type="text" class="Clientes form-select" aria-label="Default select example" name="Clientes" required>
                                    <option selected>Seleccionar</option>
                                    <%
                                        List<Cliente> listaClientes = control.traerClientes();

                                        for (Cliente cli : listaClientes) {
                                        if(cli.getEstado().equals("Activo")){
                                    %>
                                    <option class="idCliente" value="<%= cli.getId()%>"><%= cli.getNombre()%></option>
                                    <%
                                        }}
                                    %>
                                </select>
                            </div> 
                        </td>
                    </tr>
                    <tr>
                        <td>Estado</td>
                        <td>
                            <div>
                                <select type="text" class="Estado form-select" aria-label="Default select example" name="Estados" required>
                                    <option selected>Seleccionar</option>
                                    <option value="Cancelado">Cancelado</option>
                                    <option value="Pendiente de pago">Pendiente de pago</option>            
                                </select>
                            </div> 
                        </td>
                    </tr>    
                    
                </tbody>
            </table>
        </div>
        <div class="col-sm-4">
            <table>
                <tr>
                    <td>
                        <% Cliente clienteSeleccionado = (Cliente) request.getSession().getAttribute("clienteSeleccionado");%>
                        <div class="card-body">
                            <div class="form-group">
                                <label for="subtotal">Cliente</label>
                                <% if (clienteSeleccionado != null && clienteSeleccionado.getNombre() != null) {%>
                                <input type="text" value="<%=clienteSeleccionado.getNombre()%>" readonly class="form-control">
                                <% } else { %>
                                <input type="text" value="" readonly class="form-control">
                                <% }%>

                            </div>
                            <% String estado = (String) request.getSession().getAttribute("estado");%>
                            <div class="form-group">
                                <label for="descuento">Estado</label>
                                <% if (estado != null) {%>
                                <input type="text" value="<%=estado%>" readonly class="form-control">
                                <% } else { %>
                                <input type="text" value="" readonly class="form-control">
                                <% }%>               
                            </div>

                            <div class="form-group">
                                <label for="total">Fecha:</label>
                                <input type="text" id="fecha" value="" readonly class="form-control">
                            </div>
                        </div>
                    </td>
                    <td>
                        <div class="card-body">
                            <div class="form-group">
                                <label for="subtotal">Subtotal:</label>
                                <input type="text" value="<%= request.getAttribute("totalPagar")%>" readonly class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="descuento">Descuento:</label>
                                <input type="text" value="0.00" readonly class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="total">Total Pagar:</label>
                                <input type="text" value="<%= request.getAttribute("totalPagar")%>" readonly class="form-control">
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
            <div class="card-footer">
                <a href="svAgregarOrden?accion=GenerarCompra" class="btn btn-black btn-block">Realizar Pedido</a>
 
            </div>
        </div>


        <br>
        <div class="col-md-15">
            <div class="card shadow mb-4">
                <h4>Datos de la orden</h4>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-hover" id="dataTable" cellspacing="0">
                            <thead>
                                <tr>
                                    <th style="width: 5%;">ITEM</th>
                                    <th style="width: 15%;">NOMBRE</th>
                                    <th style="width: 10%;">PRECIO</th>
                                    <th style="width: 10%;">CANTIDAD</th>
                                    <th style="width: 10%;">SUBTOTAL</th>
                                    <th style="width: 10%;">ACCION</th>
                                </tr>
                            </thead>

                            <tbody>
                                <%
                                    List<DetalleOrden> listaProductosEnCarrito = (List<DetalleOrden>) request.getSession().getAttribute("listaProductos1");
                                        if (listaProductosEnCarrito != null) {
                                            for (DetalleOrden ord : listaProductosEnCarrito) {
                                %>
                                <tr>
                                    <td><%= ord.getItem()%></td>
                                    <td><%= ord.getNombreOrd()%></td>
                                    <td><%= ord.getPrecioOrden()%></td>
                                    <td>
                                        <%
                                            int maxCantidad = 0; 
                                            int productId = ord.getListaProducto().get(0).getId(); 
                                            maxCantidad=control.obtenerStockPorId(productId);
                                        %>
                                        <input type="hidden" class="idpro" value="<%=ord.getListaProducto().get(0).getId()%>">
                                        <input type="number"  value="<%= ord.getCantidad()%>" class="Cantidad form-control text-center" min="1" max="<%=maxCantidad%>">


                                    </td>
                                    <td><%= ord.getSubtotal()%></td>
                                    <td>
                                        <input type="hidden" class="idp" value="<%=ord.getListaProducto().get(0).getId()%>">
                                        <a href="#" class="btnDelete">Eliminar</a>
                                    </td>
                                </tr>
                                <%}
                                } else {
                                %>
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <%}%>
                            </tbody>
                        </table>


                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="/include/admin_footer.jsp" %>
