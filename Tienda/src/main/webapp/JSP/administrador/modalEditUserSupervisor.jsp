<%-- 
    Document   : modalEditUserSupervisor
    Created on : 4/03/2023, 22:55:04
    Author     : luis
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form method="POST" action="${pageContext.request.contextPath}/ControlUsuariosSupervisor?tarea=editar">
    <div class="row">
        <input hidden="" value="${usuarioSupervisor.codigo}" name="codigoUsuario">
        <div class="mb-3 form-group">
            <label for="recipient-name" class="col-form-label">Codigo:</label>
            <input type="text" name="codigo" class="form-control" id="recipient-name" readonly="" value="${usuarioSupervisor.codigo}">
        </div>
        <div class="mb-3 form-group">
            <label for="recipient-name" class="col-form-label">Nombre:</label>
            <input type="text" name="nombre" class="form-control" id="recipient-name" required="" value="${usuarioSupervisor.nombre}">
        </div>
        <div class="mb-3 form-group">
            <label for="recipient-name" class="col-form-label">Nombre de usuario:</label> 
            <input type="text" name="username" class="form-control" id="recipient-name" required="" value="${usuarioSupervisor.nombreUsuario}">
        </div>
        <div class="mb-3 form-group">
            <label for="recipient-name" class="col-form-label">Contraseña:</label>
            <input type="text" name="password" class="form-control" id="recipient-name" required="" value="${usuarioSupervisor.password}">
        </div>
        <div class="mb-3 form-group">
            <label for="recipient-name" class="col-form-label">Correo eléctronico</label>
            <input type="email" name="email" class="form-control" id="recipient-name" required="" value="${usuarioSupervisor.email}">
        </div>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
        <button type="submit" class="btn btn-primary">Guardar cambios</button>
    </div>
</form>