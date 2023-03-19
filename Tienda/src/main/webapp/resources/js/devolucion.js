/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function openDetalleDevolucion(context) {
    $.ajax({
        type: 'GET',
        url: context,
        success: function (result) {
            $('#modal-dev-detalle').html(result);
            $('#modalDetalleDevolucion').modal('show');
        }
    }).fail(function (jqXHR, textStatus, errorThrown) {
        alert("No se pudo realizar la acción. Lo sentimos.");
        console.log(jqXHR);
        console.log(textStatus);
        console.log(errorThrown);
    });
}

function openModalRechazarDevolucion(context) {
    $.ajax({
        type: 'GET',
        url: context,
        success: function (result) {
            $('#modal-dev-rechazo').html(result);
            $('#modalRechazarDevolucion').modal('show');
        }
    }).fail(function (jqXHR, textStatus, errorThrown) {
        alert("No se pudo realizar la acción. Lo sentimos.");
        console.log(jqXHR);
        console.log(textStatus);
        console.log(errorThrown);
    });
}

