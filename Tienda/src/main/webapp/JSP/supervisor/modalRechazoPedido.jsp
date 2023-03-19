<%-- 
    Document   : modalRechazoPedido
    Created on : 12/03/2023, 02:26:51
    Author     : luis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- para dar formato el texto-->
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- SELECCIONAMOS LA LOCALIDAD -->
<fmt:setLocale value="es_GT"/>
<div class="row">
    <div class="col-md-6">
        <div class="card-body">
            <table id="storeTable" class="table table-striped">
                <thead class="thead-dark">
                <h3>Productos en el pedido</h3>
                <tr>
                    <th scope="col">ID-pedido</th>
                    <th scope="col">Producto</th>
                    <th scope="col">Cantidad</th>
                    <th scope="col">Precio unitario</th>
                    <th scope="col">Subtotal</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${detallePedido}" var="detalleP">
                        <tr>
                            <td>${detalleP.idPedido}</td>
                            <td>${detalleP.codigoProducto}</td>
                            <td>${detalleP.cantidad}</td>
                            <td><fmt:formatNumber value="${detalleP.precioUnitario}" type="currency"></fmt:formatNumber></td>
                            <td><fmt:formatNumber value="${detalleP.subtotal}" type="currency"></fmt:formatNumber></td>
                            </tr>
                    </c:forEach>
            </table>
        </div>
    </div>
    <div class="col-md-6">
        <form method="POST" action="${pageContext.request.contextPath}/ControlReportePedidosSupervisor?tarea=rechazarPedido">
            <div class=" col-auto">
                <label for="exampleFormControlTextarea1" class="form-label">Escriba el motivo del rechazo</label>
                <textarea class="form-control" name="motivoRechazo" id="exampleFormControlTextarea1" rows="20"></textarea>
            </div>
            <div class="col-auto">
                <br>
                <button type="submit" class="btn btn-primary mb-1 w-100">Guardar cambios</button>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
    </div>
</div>
