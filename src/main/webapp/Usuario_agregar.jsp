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
    <h1 class="mt-5" style="text-align: center">Agregar Usuarios</h1>
    <div class="container p-5">
        <div class="row">
            <div class="col">
            </div>
            <form action="svAgregarUsuario" method="POST">
                <div class="col">
                    <div class="input-group mb-3">
                        <span class="input-group-text" id="basic-addon1">Nombre</span>
                        <input type="text" class="form-control" placeholder="" name="nombre" required>
                    </div>
            
                    <div class="input-group mb-3">
                        <span class="input-group-text">Usuario de ingreso</span>
                        <input type="text" class="form-control" placeholder="" name="idIngreso" required>

                    </div>

                    <div class="input-group mb-3">
                        <span class="input-group-text">Contraseña</span>
                        <input type="password" class="form-control" placeholder="*******" name="contrasena" required>  
                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text">tipo</span>
                        <select type="text" class="form-select" aria-label="Default select example" name="tipo" required>
                            <option selected>Seleccionar</option>
                            <option value="Administrador">Administrador</option>
                            <option value="Bodeguero">Bodeguero</option>
                            <option value="Personal Inventario">Personal Inventario</option>
                        </select>
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
