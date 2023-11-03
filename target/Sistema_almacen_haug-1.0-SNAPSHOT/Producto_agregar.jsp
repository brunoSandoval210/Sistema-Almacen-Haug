<%@page import="Modelo.Proveedor"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Modelo.Controller"%>
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
    <!-- Page Heading -->
    <h1 class="mt-5" style="text-align: center">Agregar Productos</h1>
    <div class="container p-5">
        <div class="row">
            <div class="col">
            </div>
            <form action="svAgregarProducto" method="POST">
                <div class="col">
   
                    <div class="input-group mb-3">
                        <span class="input-group-text">Proveedor</span>
                        <select type="text" class="form-select" aria-label="Default select example" name="ProveedorProd" required>
                            <option selected>Seleccionar</option>
                            <% Controller control = new Controller();
                                List<Proveedor> listaProveedores = new ArrayList<>();
                                listaProveedores = control.traerProveedores();

                                for (Proveedor prove : listaProveedores) {
                                if(prove.getEstado().equals("Activo")){
                            %>
                            <option value="<%= prove.getNombre()%>"><%= prove.getNombre()%></option>
                            <%
                                } }
                            %>
                        </select>
                    </div>
                    
                    <div class="input-group mb-3">
                        <span class="input-group-text" id="basic-addon1">Nombre</span>
                        <input type="text" class="form-control" placeholder="" name="NombreProd">
                    </div>

                    <div class="input-group mb-3">
                        <span class="input-group-text">s/</span>
                        <input type="text" class="form-control" placeholder="Precio" name="PrecioProd">

                    </div>

                    <div class="input-group mb-3">
                        <span class="input-group-text">Stock</span>
                        <input type="text" class="form-control" placeholder="" name="StockProd">  
                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text">imagen</span>
                        <input type="file" class="form-control" placeholder="Ingresa url" name="ImagenProd">

                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text">Estado</span>
                        <select type="text" class="form-select" aria-label="Default select example" name="estado" required>
                            <option selected>Seleccionar</option>
                            <option value="Activo">Activo</option>
                            <option value="Desactivado">Desactivado</option>

                        </select>
                    </div>
                    <div class="mb-3">
                        <textarea class="form-control" name="DescripcionProd" rows="3" placeholder="Descripción"></textarea>
                    </div>

                    <div class="btn btn-dark text-white w-100 mt-4 fw-semibold">
                        <input type="submit" name="btnIngresar" value="Agregar" class="btn btn-dark"/>
                    </div>
                </div>
            </form>
            <div class="col">
            </div>
        </div>
    </div> 
</div>

<%@ include file="/include/admin_footer.jsp" %>
