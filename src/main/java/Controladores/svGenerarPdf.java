/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controladores;

import Modelo.DetalleOrden;
import Modelo.Orden;
import com.itextpdf.text.BaseColor;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 *
 * @author Bruno Sandoval
 */
@WebServlet(name = "svGenerarPdf", urlPatterns = {"/svGenerarPdf"})
public class svGenerarPdf extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=boleta.pdf");

        try {
            // Crear el documento PDF
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Fuente y tamaño de texto personalizados
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.DARK_GRAY);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.DARK_GRAY);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);

            // Agregar contenido al PDF
            document.add(new Paragraph("Boleta de Compra", titleFont));

            // Datos de la Empresa
            document.add(new Paragraph("Datos de la Empresa", headerFont));
            document.add(new Paragraph("Nombre de la Empresa: Haug S.A.", normalFont));
            document.add(new Paragraph("Dirección: Av. Separadora Industrial 456", normalFont));
            document.add(new Paragraph("Teléfono: 987-654-3210", normalFont));
            document.add(new Paragraph("Correo Electrónico: HaugContacto@gmail.com", normalFont));

            // Datos del Cliente
            Orden ord = (Orden) request.getSession().getAttribute("Orden");
            document.add(new Paragraph("Datos del Cliente", headerFont));
            document.add(new Paragraph("Nombre: " + ord.getCli().getNombre(), normalFont));
            document.add(new Paragraph("Dirección: " + ord.getCli().getDireccion(), normalFont));
            document.add(new Paragraph("Teléfono: " + ord.getCli().getTelefono(), normalFont));

            // Fecha
            document.add(new Paragraph("Fecha de la orden: " + ord.getCreateAt(), normalFont));

            // Tabla con detalles de la orden
            document.add(new Paragraph("Detalles de la Orden", headerFont));
            document.add(new Paragraph("                                   "));
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);

            PdfPCell cell = new PdfPCell(new Phrase("Producto", headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Precio Unitario", headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Cantidad", headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Total", headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            for (DetalleOrden detalle : ord.getDetallesOrden()) {
                table.addCell(new Phrase(detalle.getNombreOrd(), normalFont));
                table.addCell(new Phrase("s/. " + detalle.getPrecioOrden(), normalFont));
                table.addCell(new Phrase(String.valueOf(detalle.getCantidad()), normalFont));
                table.addCell(new Phrase("s/. " + (detalle.getCantidad() * detalle.getPrecioOrden()), normalFont));
            }

            document.add(table);

            // Subtotal y Total a Pagar
            document.add(new Paragraph("Subtotal: s/. " + ord.getMonto(), normalFont));
            document.add(new Paragraph("Total a Pagar: s/. " + ord.getMonto(), normalFont));

            document.close();
            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
