
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
<%@page import="Modelo.Usuario"%>

<div class="container-fluid">
    <% Usuario usu = (Usuario) request.getSession().getAttribute("usuEditar");%>
    <!-- Page Heading -->
    <h1 class="mt-5" style="text-align: center">Modificar Usuarios</h1>
    <div class="container p-5">
        <div class="row">
            <div class="col">
            </div>
            <form action="svEditarUsuario" method="POST">
                <div class="col">
                    <div class="input-group mb-3">
                        <span class="input-group-text" id="basic-addon1">Nombre</span>
                        <input type="text" class="form-control" placeholder="" name="nombre" value ="<%=usu.getNombre()%>" required>
                    </div>
              
                    <div class="input-group mb-3">
                        <span class="input-group-text">Usuario de ingreso</span>
                        <input type="text" class="form-control" placeholder="" name="idIngreso" value ="<%=usu.getIdIngreso()%>"required>

                    </div>

                    <div class="input-group mb-3">
                        <span class="input-group-text">Contraseña</span>
                        <input type="password" class="form-control" placeholder="*******" name="contrasena" value ="<%=usu.getContrasena()%>" required>  
                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text">tipo</span>
                        <select type="text" class="form-select" aria-label="Default select example" name="tipo" value ="<%=usu.getTipo()%>" required>
                            <option selected>Seleccionar</option>
                            <option value="Administrador">Administrador</option>
                            <option value="Bodeguero">Bodeguero</option>
                            <option value="Personal Inventario">Personal Inventario</option>
                        </select>
                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text">Estado</span>
                        <select type="text" class="form-select" aria-label="Default select example" name="estado" value ="<%=usu.getEstado()%>" required>
                            <option selected>Seleccionar</option>
                            <option value="Activo">Activo</option>
                            <option value="Desactivado">Desactivado</option>
                        </select>
                    </div>
                    <div class="btn btn-dark text-white w-100 mt-4 fw-semibold">
                        <input type="submit"  value="Modificar" class="btn btn-dark"/>
                    </div>
                </div>
            </form>
            <div class="col">
            </div>
        </div>
    </div> 
</div>

<%@ include file="/include/admin_footer.jsp" %>
