/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    $('#storeTable').DataTable({
        "language": {
            "lengthMenu": "No. _MENU_ de registros a mostrar",
            "zeroRecords": "No hay registros - lo sentimos",
            "info": "Viendo página _PAGE_ de _PAGES_",
            "infoEmpty": "No hay registros",
            "infoFiltered": "(filtered from _MAX_ total records)",
            "sSearch": "Buscar",
            "oPaginate": {
                "sFirst": "Primero",
                "sLast": "Último",
                "sNext": "Siguiente",
                "sPrevious": "Anterior"
            },
            "sProcessing": "Espere..."
        }
    });
});