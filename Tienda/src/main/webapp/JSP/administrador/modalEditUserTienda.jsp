<%-- 
    Document   : modalEditUserTienda
    Created on : 4/03/2023, 12:03:44
    Author     : luis
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form method="POST" action="${pageContext.request.contextPath}/ControlUsuariosTienda?tarea=editar">
    <div class="row">
        <input hidden="" value="${usuario.codigo}" name="codigoUsuario">
        <div class="mb-3 form-group">
            <label for="recipient-name" class="col-form-label">Codigo:</label>
            <input type="text" name="codigo" class="form-control" id="recipient-name" readonly="" value="${usuario.codigo}">
        </div>
        <div class="mb-3 form-group">
            <label for="recipient-name" class="col-form-label">Nombre:</label>
            <input type="text" name="nombre" class="form-control" id="recipient-name" required="" value="${usuario.nombre}">
        </div>
        <div class="mb-3 form-group">
            <label for="recipient-name" class="col-form-label">Nombre de usuario:</label> 
            <input type="text" name="username" class="form-control" id="recipient-name" required="" value="${usuario.nombreUsuario}">
        </div>
        <div class="mb-3 form-group">
            <label for="recipient-name" class="col-form-label">Contraseña:</label>
            <input type="text" name="password" class="form-control" id="recipient-name" required="" value="${usuario.password}">
        </div>
        <div class="mb-3 form-group">
            <label for="recipient-name" class="col-form-label">Correo eléctronico</label>
            <input type="email" name="email" class="form-control" id="recipient-name" required="" value="${usuario.email}">
        </div>
        <div class="mb-3 form-group">
            <label for="recipient-name" class="col-form-label">Tienda actual</label>
            <input type="text" name="tiendaActual" class="form-control" id="recipient-name" required="" value="${usuario.codigoTienda}">
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
