<%@page import="Modelo.Proveedor"%>
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
    
    <% Proveedor prod = (Proveedor) request.getSession().getAttribute("proveEditar");%>
    <!-- Page Heading -->
    <h1 class="mt-5" style="text-align: center">Modificar Proveedores</h1>
    <div class="container p-5">
        <div class="row">
            <div class="col">
            </div>
            <form action="svEditarProveedor" method="POST">
                <div class="col">
                    <div class="input-group mb-3">
                        <span class="input-group-text" id="basic-addon1">Nombre</span>
                        <input type="text" class="form-control" placeholder="" name="NombreProve" value ="<%=prod.getNombre()%>" required>
                    </div>

                    <div class="input-group mb-3">
                        <span class="input-group-text">Telefono</span>
                        <input type="text" class="form-control" placeholder="*****" name="telefono" value ="<%=prod.getTelefono()%>" required maxlength="9">

                    </div>

                    <div class="input-group mb-3">
                        <span class="input-group-text">Numero de Ruc</span>
                        <input type="text" class="form-control" placeholder="" name="ruc" value ="<%=prod.getRuc()%>" required maxlength="11">  
                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text">Estado</span>
                        <select type="text" class="form-select" aria-label="Default select example" name="EstadoProve" required>
                            <option selected><%=prod.getEstado()%></option>
                            <option value="Activo">Activo</option>
                            <option value="Desactivado">Desactivado</option>
                         
                        </select>
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
