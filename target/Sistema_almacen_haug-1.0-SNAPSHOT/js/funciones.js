$(document).ready(function () {
    $(".btnDelete").click(function () {
        var idp = $(this).closest("tr").find(".idp").val();
        eliminar(idp);
    });

    function eliminar(idp) {
        var url = "svAgregarOrden?accion=Delete";
        $.ajax({
            type: 'post',
            url: url,
            data: "idp=" + idp,
            success: function (data, textStatus, jqXHR) {
                alert("Registro eliminado");
                // Aquí puedes agregar código adicional si es necesario
            }
        });
    }
    
    $(".Cantidad").click(function () {
        var idp = $(this).closest("tr").find(".idpro").val();
        var cantidad = $(this).closest("tr").find(".Cantidad").val();
        var url = "svAgregarOrden?accion=ActualizarCantidad";
        $.ajax({
            type: 'post',
            url: url,
            data: "idp=" + idp + "&Cantidad=" + cantidad,
            success: function (data, textStatus, jqXHR) {
                location.href = "svAgregarOrden?accion=Carrito";
            }
        });
    });
    $(".Productos").change(function () {
        var idp = $(this).val();
        var url = "svAgregarOrden?accion=AgregarCarrito&id=" + idp; // Agregar el ID al final de la URL
        $.ajax({
            type: 'post',
            url: url,
            success: function (data, textStatus, jqXHR) {
                location.href = "svAgregarOrden?accion=Carrito";
            }
        });
    });
    $(".Clientes").change(function () {
        var idCli = $(this).val();
        var url = "svAgregarOrden?accion=AgregarCliente";
        $.ajax({
            type: 'post',
            url: url,
            data: "idCli="+ idCli,
            success: function (data, textStatus, jqXHR) {
                location.href = "svAgregarOrden?accion=Carrito";
            }
        });
    });
    $(".Estado").change(function () {
        var estadoSeleccionado = $(this).val();
        var url = "svAgregarOrden?accion=ActualizarEstado"; // Cambiar la acción según tu lógica
        $.ajax({
            type: 'post',
            url: url,
            data: "estado=" + estadoSeleccionado,
            success: function (data, textStatus, jqXHR) {
                // Redirección después de que la llamada AJAX se haya completado
                location.href = "svAgregarOrden?accion=Carrito";
            }
        });
    });
    
    var fechaInput = document.getElementById("fecha");
    var fechaActual = new Date();
    var day = fechaActual.getDate();
    var month = fechaActual.getMonth() + 1;
    var year = fechaActual.getFullYear();

    if (day < 10) {
        day = "0" + day;
    }

    if (month < 10) {
        month = "0" + month;
    }

    var fechaFormateada = day + "/" + month + "/" + year;
    fechaInput.value = fechaFormateada;


});
