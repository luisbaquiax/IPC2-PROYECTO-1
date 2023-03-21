<%-- 
    Document   : modalDetalleEnvio
    Created on : 20/03/2023, 02:12:56
    Author     : luis
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- para dar formato el texto-->
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- SELECCIONAMOS LA LOCALIDAD -->
<fmt:setLocale value="es_GT"/>
<div class="row">
    <div class="card-body">
        <table id="storeTable" class="table table-striped">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">Envío</th>
                    <th scope="col">Producto</th>
                    <th scope="col">Cantidad</th>
                    <th scope="col">Precio unitario</th>
                    <th scope="col">Subtotal</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${detalleDelEnvio}" var="productosEnvio">
                    <tr>
                        <td>${productosEnvio.idEnvio}</td>
                        <td>${productosEnvio.codigoProducto}</td>
                        <td>${productosEnvio.cantidad}</td>
                        <td><fmt:formatNumber value="${productosEnvio.precioUnitario}" type="currency"></fmt:formatNumber></td>
                        <td><fmt:formatNumber value="${productosEnvio.subtotal}" type="currency"></fmt:formatNumber></td>
                        </tr>
                </c:forEach>
        </table>
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
</div>