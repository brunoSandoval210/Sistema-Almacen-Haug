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

<div class="card shadow mb-4">
    <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-dark">Proveedores Registrados</h6>
    </div>
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>NOMBRE</th>
                        <th>TELEFONO</th>
                        <th>RUC</th>
                        <th>FECHA DE ALTA</th>
                        <th>ESTADO</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <th>ID</th>
                        <th>NOMBRE</th>
                        <th>TELEFONO</th>
                        <th>RUC</th>
                        <th>FECHA DE ALTA</th>
                        <th>ESTADO</th>
                    </tr>
                </tfoot>
                <tbody>
                    <%  List<Proveedor> listaProveedores = (List) request.getSession().getAttribute("listaProveedores");
                        for (Proveedor prove : listaProveedores) {
                    %>
                    <tr>
                        <th><%= prove.getId()%></th>
                        <td><%= prove.getNombre()%></td>
                        <td><%= prove.getTelefono()%></td>
                        <td><%= prove.getRuc()%></td>
                        <td><%= prove.getCreateAt()%></td>
                        <td><%= prove.getEstado()%></td>
                    </tr>
                    <%}%>
                </tbody>
            </table>
        </div>
    </div>
</div>
<div id="ModificarModal" class="modal fade">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Ingregar datos</h4>
            </div>
            <div class="modal-body">
                <p>Ingrese la id del usuario que quiere editar:</p>
                <form action="svEditarProveedor" method="get">
                    <div class="input-group mb-3">
                        <span class="input-group-text" id="basic-addon1">ID</span>
                        <input type="text" class="form-control" placeholder="" name="id_edit" required>
                    </div>
                    <button type="submit" class="btn btn-dark">Editar</button>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
            </div>
        </div>
    </div>
</div>

<div id="EliminarModal" class="modal fade">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">¿Estás seguro?</h4>
            </div>
            <div class="modal-body">
                <p>Ingrese la id del usuario que quiere Eliminar:</p>
                <form action="svEliminarProveedor" method="post">
                    <div class="input-group mb-3">
                        <span class="input-group-text" id="basic-addon1">ID</span>
                        <input type="text" class="form-control" placeholder="" name="id_eliminar" required>
                    </div>
                    <button type="submit" class="btn btn-dark">Eliminar</button>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
            </div>
        </div>
    </div>
</div>
<a href="#ModificarModal" type="button" class="btn btn-dark" data-toggle="modal">Modificar</a>
<a href="#EliminarModal" type="button" class="btn btn-dark" data-toggle="modal">Eliminar</a>

<%@ include file="/include/admin_footer.jsp" %>

