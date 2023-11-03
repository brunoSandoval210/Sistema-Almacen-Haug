<%@page import="Modelo.DetalleOrden"%>
<%@page import="Modelo.Orden"%>
<%@page import="java.util.List"%>
<%@ page import="com.itextpdf.text.Document" %>
<%@ page import="com.itextpdf.text.DocumentException" %>
<%@ page import="com.itextpdf.text.pdf.PdfWriter" %>
<%@ page import="java.io.FileOutputStream" %>
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
<div class="container mt-5">
    <div class="text-center">
        <img src="img/logofondoOsc.png" alt="Logo de la Empresa" class="img-fluid" style="max-width: 150px;">
        <h1 class="mt-3">Boleta de Compra</h1>
    </div>
    <div class="row">
        <div class="col-md-12 company-info">
            <!-- Datos de la Empresa -->
            <h3>Datos de la Empresa</h3>
            <p><strong>Nombre de la Empresa:</strong>Haug S.A.</p>
            <p><strong>Dirección:</strong> Av. Separadora Industrial 456</p>
            <p><strong>Teléfono:</strong> 987-654-3210</p>
            <p><strong>Correo Electrónico:</strong> HaugContacto@gmail.com</p>
        </div>
    </div>
    <%                Orden ord = (Orden) request.getSession().getAttribute("Orden");


    %>

    <div class="row mt-4">
        <div class="col-md-6">
            <h4>Datos del Cliente:</h4>
            <p>Nombre: <%=ord.getCli().getNombre()%></p>
            <p>Dirección:  <%= ord.getCli().getDireccion()%></p>
            <p>Teléfono:  <%= ord.getCli().getTelefono()%></p>
        </div>
        <div class="col-md-6 text-right">
            <h4>Fecha:</h4>
            <p>Fecha de la orden:  <%= ord.getCreateAt()%></p>
        </div>
    </div>

    <table class="table mt-4">
        <thead>
            <tr>
                <th>Producto</th>
                <th>Precio Unitario</th>
                <th>Cantidad</th>
                <th>Total</th>
            </tr>
        </thead>
        <tbody>
            <% for (DetalleOrden detalle : ord.getDetallesOrden()) {%>
            <tr>
                <td><%= detalle.getNombreOrd()%></td>
                <td> s/.<%= detalle.getPrecioOrden()%></td>
                <td><%= detalle.getCantidad()%></td>
                <td>s/.<%= (detalle.getCantidad()) * (detalle.getPrecioOrden())%></td>
            </tr>
            <%}%>

        </tbody>
    </table>

    <div class="row justify-content-end">
        <div class="col-md-6 text-right">
            <p><strong>Subtotal:</strong> s/.<%= ord.getMonto()%></p>
            <p><strong>Total a Pagar:</strong> s/.<%= ord.getMonto()%></p>
        </div>
    </div>
</div>
<%
// Generar el archivo PDF y guardarlo en el servidor
    /*Document document = new Document();
    String pdfFilePath = request.getServletContext().getRealPath("/Pdf/boleta.pdf");
    PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
    document.open();

// Agregar contenido al PDF aquí (similar a cómo lo haces en tu JSP)
    document.close();*/
%>
<a href="svGenerarPdf" class="btn btn-dark">Descargar PDF</a>


<%@ include file="/include/admin_footer.jsp" %>
