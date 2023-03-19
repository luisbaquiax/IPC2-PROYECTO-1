<%-- 
    Document   : modalRechazarDevolucion
    Created on : 13/03/2023, 23:42:04
    Author     : luis
--%>
<meta charset="utf-8">
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
                    <tr>
                        <th scope="col">ID-devolución</th>
                        <th scope="col">Motivo</th>
                        <th scope="col">Producto</th>
                        <th scope="col">Cantidad</th>
                        <th scope="col">Precio unitario</th>
                        <th scope="col">Subtotal</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${productosDevolucion}" var="productosDev">
                        <tr>
                            <td>${productosDev.idDevolucion}</td>
                            <td>${productosDev.motivo}</td>
                            <td>${productosDev.codigoProducto}</td>
                            <td>${productosDev.cantidad}</td>
                            <td><fmt:formatNumber value="${productosDev.costoUnitario}" type="currency"></fmt:formatNumber></td>
                            <td><fmt:formatNumber value="${productosDev.subTotal}" type="currency"></fmt:formatNumber></td>
                            </tr>
                    </c:forEach>
            </table>
        </div>
    </div>
    <div class="col-md-6">
        <form method="POST" action="${pageContext.request.contextPath}/ControlBodegaDevoluciones?tarea=rechazar">
            <div class=" col-auto">
                <label for="exampleFormControlTextarea1" class="form-label">Escriba el motivo del rechazo</label>
                <textarea class="form-control" name="motivoDev" id="exampleFormControlTextarea1" rows="20"></textarea>
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
