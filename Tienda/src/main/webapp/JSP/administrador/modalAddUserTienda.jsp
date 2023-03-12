<%-- 
    Document   : modalAddUserTienda
    Created on : 4/03/2023, 00:47:14
    Author     : luis
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="modal fade" id="modalAddUserTienda" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg-info">
                <h5 class="modal-title" id="exampleModalLabel">Crear usuario tienda</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form method="POST" action="${pageContext.request.contextPath}/ControlUsuariosTienda?tarea=add">
                    <div class="row">
                        <div class="mb-3 form-group">
                            <label for="recipient-name" class="col-form-label">Codigo:</label>
                            <input type="text" name="codigo" class="form-control" id="recipient-name" required="">
                        </div>
                        <div class="mb-3 form-group">
                            <label for="recipient-name" class="col-form-label">Nombre:</label>
                            <input type="text" name="nombre" class="form-control" id="recipient-name" required="">
                        </div>
                        <div class="mb-3 form-group">
                            <label for="recipient-name" class="col-form-label">Nombre de usuario:</label> 
                            <input type="text" name="username" class="form-control" id="recipient-name" required="">
                        </div>
                        <div class="mb-3 form-group">
                            <label for="recipient-name" class="col-form-label">Contraseña:</label>
                            <input type="text" name="password" class="form-control" id="recipient-name" required="">
                        </div>
                        <div class="mb-3 form-group">
                            <label for="recipient-name" class="col-form-label">Correo eléctronico</label>
                            <input type="email" name="email" class="form-control" id="recipient-name" required="">
                        </div>
                        <div class="mb-3 form-group">
                            <label for="exampleDataList" class="form-label">Seleccionar tienda:</label>
                            <input class="form-control" name="tienda" list="datalistOptions" id="exampleDataList" placeholder="Elegir tienda..." required="">
                            <datalist id="datalistOptions">
                                <c:forEach items="${tiendas}" var="t">
                                    <option value="${t.codigo}">
                                    </c:forEach>
                            </datalist>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        <button type="submit" class="btn btn-primary">Guardar cambios</button>
                    </div>
                </form>
            </div>

        </div>
    </div>
</div>
