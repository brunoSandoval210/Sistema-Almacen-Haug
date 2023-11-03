<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashSet"%>
<%@page import="Modelo.DetalleOrden"%>
<%@page import="Modelo.Orden"%>
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

<div class="card shadow mb-4">
    <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-dark">Ordenes</h6>
    </div>
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>CLIENTE</th>
                        <th>PRODUCTOS</th>
                        <th>FECHA DE LA ORDEN</th>
                        <th>ESTADO</th>
                        <th>MONTO</th>
                        <th>ACCION</th>
                    </tr>
                </thead>      
                <tbody>
                    <%  
                    Controller control = new Controller();
                    List<Orden> listaOrdenes = (List) request.getSession().getAttribute("listaOrdenes");
                    
                    if (listaOrdenes != null && !listaOrdenes.isEmpty()) {
                        Set<Orden> ordenesUnicas = new HashSet<>(listaOrdenes);
                        List<Orden> listaOrdenesUnicas = new ArrayList<>(ordenesUnicas);
                        for (Orden ord : listaOrdenesUnicas) {
                    %>
                    <tr>
                        <td><%= ord.getId()%></td>
                        <td>
                            <% if (ord.getCli() != null) {%>
                            <%= ord.getCli().getNombre()%>
                            <% } else { %>
                            null
                            <% } %>
                        </td>
                        <td>
                            <% if (ord.getDetallesOrden() != null && !ord.getDetallesOrden().isEmpty()) { %>
                            <ul class="list-unstyled">
                                <% for (DetalleOrden detalle : ord.getDetallesOrden()) {%>
                                <li>
                                    <div class="d-flex justify-content-between align-items-center">
                                        <span><%= detalle.getNombreOrd()%></span>
                                        <span class="d-flex justify-content-between align-items-center">
                                            Cantidad: <%= detalle.getCantidad()%>
                                        </span>
                                    </div>
                                </li>
                                <% } %>
                            </ul>
                            <% } else { %>
                            No hay productos
                            <% }%>
                        </td>
                        <td><%= ord.getCreateAt()%></td>
                        <td><%= ord.getEstado()%></td>
                        <td><%= ord.getMonto()%></td>
                        <td>
                            <form action="svCrearPdf" method="get">
                                <input type="hidden" class="form-control" value="<%=ord.getId()%>" placeholder="" name="idOrden" required>
                                <button type="submit" class="btn btn-dark">Generar PDF</button>
                            </form>
                        </td>
                    </tr>
                    <% 
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="7">No hay órdenes disponibles.</td>
                    </tr>
                    <% }%>
                </tbody>
            </table>
        </div>
    </div>
</div>
<%@ include file="/include/admin_footer.jsp" %>
