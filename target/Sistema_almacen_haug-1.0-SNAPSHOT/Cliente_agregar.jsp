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
    <h1 class="mt-5" style="text-align: center">Agregar Clientes</h1>
    <div class="container p-5">
        <div class="row">
            <div class="col">
            </div>
            <form action="svAgregarCliente" method="POST">
                <div class="col">

                    <div class="input-group mb-3">
                        <span class="input-group-text" id="basic-addon1">Nombre</span>
                        <input type="text" class="form-control" placeholder="" name="nombre">
                    </div>

                    <div class="input-group mb-3">
                        <span class="input-group-text">telefono/</span>
                        <input type="tel" class="form-control" placeholder="xxxxxxx" name="telefono" maxlength="9" >
                    </div>

                    <div class="input-group mb-3">
                        <span class="input-group-text">Numero de ruc</span>
                        <input type="text" class="form-control" placeholder="" name="ruc" maxlength="11">  
                    </div>
                    
                    <div class="input-group mb-3">
                        <span class="input-group-text">Direccion</span>
                        <input type="text" class="form-control" placeholder="" name="direccion">  
                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text">Estado</span>
                        <select type="text" class="form-select" aria-label="Default select example" name="estado" required>
                            <option selected>Seleccionar</option>
                            <option value="Activo">Activo</option>
                            <option value="Desactivado">Desactivado</option>
                         
                        </select>
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
