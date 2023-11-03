<%@page import="java.util.ArrayList"%>
<%@page import="Modelo.Proveedor"%>
<%@page import="Modelo.Controller"%>
<%@page import="Modelo.Producto"%>
<%
String userType = (String) session.getAttribute("tipo"); 
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

<div class="container-fluid">
    
    <% Producto prod = (Producto) request.getSession().getAttribute("prodEditar");
    Controller control = new Controller();
    %>
    <!-- Page Heading -->
    <h1 class="mt-5" style="text-align: center">Modificar Productos</h1>
    <div class="container p-5">
        <div class="row">
            <div class="col">
            </div>
            <form action="svEditarProducto" method="POST">
                <div class="input-group mb-3">

                    <span class="input-group-text">Proveedor</span>
                    <select type="text" class="form-select" aria-label="Default select example" name="ProveedorProd" required>
                        <option selected><% if (prod.getProv() != null) {%>
                            <%= prod.getProv().getNombre()%>
                            <% } else { %>
                            null
                            <% } %></option>
                            <%
                                List<Proveedor> listaProveedores = new ArrayList<>();
                                listaProveedores = control.traerProveedores();

                                for (Proveedor prove : listaProveedores) {
                                    if (prove.getEstado().equals("Activo")) {
                            %>
                        <option value="<%= prove.getNombre()%>"><%= prove.getNombre()%></option>
                        <%
                                }
                            }
                        %>
                    </select>
                </div>
                <div class="col">
                    <div class="input-group mb-3">
                        <span class="input-group-text" id="basic-addon1">Nombre</span>
                        <input type="text" class="form-control" placeholder="" name="NombreProd" value ="<%=prod.getNombre()%>" required>
                    </div>

                    <div class="input-group mb-3">
                        <span class="input-group-text">s/</span>
                        <input type="text" class="form-control" placeholder="Precio" name="PrecioProd" value ="<%=prod.getPrecio()%>" required>

                    </div>

                    <div class="input-group mb-3">
                        <span class="input-group-text">Stock</span>
                        <input type="text" class="form-control" placeholder="" name="StockProd" value ="<%=prod.getCantidad()%>" required>  
                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text">imagen</span>
                        <input type="text" class="form-control" placeholder="Ingresa url" name="ImagenProd" value ="<%=prod.getImagen()%>" required>

                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text">Estado</span>
                        <select type="text" class="form-select" aria-label="Default select example" name="estado" required>
                            <option selected><%=prod.getEstado()%></option>
                            <option value="Activo">Activo</option>
                            <option value="Desactivado">Desactivado</option>

                        </select>
                    </div>
                    <div class="mb-3">
                        <textarea class="form-control" name="DescripcionProd" rows="3" placeholder="Descripción" value ="<%=prod.getDescripcion()%>" required></textarea>
                    </div>

                    <div class="btn btn-dark text-white w-100 mt-4 fw-semibold">
                        <input type="submit" value="Modificar" class="btn btn-dark"/>
                    </div>
                </div>
            </form>
            <div class="col">
            </div>
        </div>
    </div> 
</div>

<%@ include file="/include/admin_footer.jsp" %>
