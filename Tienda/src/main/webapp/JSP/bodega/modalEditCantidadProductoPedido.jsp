<%-- 
    Document   : modificarCantidadPedidoSolicitado
    Created on : 10/03/2023, 23:58:04
    Author     : luis
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form method="POST" action="${pageContext.request.contextPath}/ControlBodegaEnvios?tarea=modificarCantidad">
    <div class="row">
        <div class="mb-3 form-group">
            <label for="recipient-name" class="col-form-label">Codigo:</label>
            <input type="text" name="codigo" class="form-control" id="recipient-name" required="" value="${producto.codigoProducto}" readonly="">
        </div>
        <div class="mb-3 form-group">
            <label for="recipient-name" class="col-form-label">Nueva cantidad:</label>
            <input type="number" name="cantidad" class="form-control" id="recipient-name" required="" min="1" value="${producto.cantidad}">
        </div>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
        <button type="submit" class="btn btn-primary">Guardar cambios</button>
    </div>
</form>
