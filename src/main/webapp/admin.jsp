<%@page import="java.util.ArrayList"%>
<%@page import="Modelo.Producto"%>
<%@page import="Modelo.Controller"%>


<%
String userType = (String) session.getAttribute("tipo"); // Obtiene el tipo de usuario de la sesión
if (userType != null) {
    // Aquí puedes utilizar userType para personalizar la página según el tipo de usuario
    if (userType.equals("Bodeguero")) {%>
    <%@ include file="/include/admin_Bodeguero.jsp" %>
    <%} else if (userType.equals("Personal de Inventario")) {%>
    <%@ include file="/include/admin_PersonalInventario.jsp" %>
   <% } else {%>
        <%@ include file="/include/admin_header.jsp" %>
    <%}
} else {
    // El tipo de usuario no está definido en la sesión, puedes manejarlo adecuadamente.
}
%>
<section>
    <p class="fs-1 fw-bold text-center"><span class="text-dark">Nuestros Productos</p>

    <div class="container px-4 px-lg-5 mt-5">
        <div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">

            <%  Controller control = new Controller();
                List<Producto> listaProductos = new ArrayList<>();
                listaProductos = control.encontrarTodosProductosConProveedor();

                for (Producto prod : listaProductos) {
            %>
            <div class="col mb-5">
                <div class="card h-100">
                    <img src="<%= ("img_productos/"+prod.getImagen())%>" class="card-img-top" alt="...">         
                    <div class="card-body p-4">
                        <div class="text-center">
                            <h5 class="card-title fw-bold"><%= prod.getNombre()%></h5>
                            <p class="card-text fs-6 fw-lighter"><%= prod.getDescripcion()%></p>
                            <div class="card-text fw-bold">
                                S/<%= prod.getPrecio()%>
                            </div>
                        </div>
                    </div>
                    <div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
                        <div class="text-center">
                            <a class="btn btn-outline-dark mt-auto" href="svAgregarOrden?accion=AgregarCarrito&id=<%= prod.getId() %>">Añadir a la orden</a>

                        </div>
                    </div>
                </div>
            </div>
            <%}%>
        </div>
    </div>
</section>
<!-- Footer -->
<%@ include file="/include/admin_footer.jsp" %>