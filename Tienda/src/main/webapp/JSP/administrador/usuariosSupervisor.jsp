<%-- 
    Document   : usuariosSupervisor
    Created on : 3/03/2023, 23:17:25
    Author     : luis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="en">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <title>Usuario de tienda</title>

        <link rel="canonical" href="https://getbootstrap.com/docs/5.1/examples/navbar-fixed/">
        <!-- Bootstrap core CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <!-- ajax -->
        <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
        <!-- icons -->
        <script src="https://kit.fontawesome.com/6d0db64a1f.js" crossorigin="anonymous"></script>
        <!-- dataTables -->
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.11.3/datatables.min.css"/>
        <!-- js -->
        <script src="../../resources/js/usuarioTienda.js" type="text/javascript"></script>
        <link href="../../resources/css/general.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <jsp:include page="menuAdministrador.jsp"></jsp:include>
            <div class="container mt-5">
                <div class="card-header">
                    <br>
                    <div class="text-center">
                        <h1>Lista de usuarios supervisores</h1>
                        <hr>
                    </div>
                    <div class="row">
                        <div class="col col-md-6">
                            <a href="#"
                               class="btn btn-primary btn-block w-50" data-bs-toggle="modal" 
                               data-bs-target="#modalAddUserSupervisor" 
                               data-bs-whatever="@mdo"><i class="fas fa-plus"></i> Agregar nuevo</a>
                        </div>
                    </div>
                    <h3 class="text-success">${msj}</h3> 
            </div>
            <div class="card-body">
                <table id="storeTable" class="table table-striped">
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col">Código</th>
                            <th scope="col">Nombre</th>
                            <th scope="col">Nombre de usuario</th>
                            <th scope="col">Tipo de usuario</th>
                            <th scope="col">Estado</th>
                            <th scope="col">Activar/Desactivar</th>
                            <th scope="col">Editar usuario</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${usuariosSupervisor}" var="u">
                            <tr>
                                <td>${u.codigo}</td>
                                <td>${u.nombre}</td>
                                <td>${u.nombreUsuario}</td>
                                <td>${u.tipo}</td>
                                <td>
                                    <c:if test="${u.estado==true}">Activo</c:if>
                                    <c:if test="${u.estado==false}">Desactivado</c:if>
                                    </td>
                                    <td>
                                    <c:if test="${u.estado==true}">
                                        <a href="${pageContext.request.contextPath}/ControlAdministrador?tarea=activarDesactivar&codigo=${u.codigo}" 
                                           class="btn btn-danger btn-block" ><i class="fas fa-trash-alt"></i> Desactivar</a>
                                    </c:if>
                                    <c:if test="${u.estado==false}">
                                        <a href="${pageContext.request.contextPath}/ControlAdministrador?tarea=activarDesactivar&codigo=${u.codigo}" 
                                           class="btn btn-success btn-block" ><i class="fas fa-user-cog"></i> Activar</a>
                                    </c:if>
                                </td>
                                <td>
                                    <a href="#"
                                       onclick="openEditSupervsor('${pageContext.request.contextPath}/ControlUsuariosSupervisor?tarea=openEdit&codigo=${u.codigo}')"
                                       class="btn btn-warning btn-block" ><i class="fas fa-user-edit"></i> Editar</a>
                                </td>
                            </tr>
                        </c:forEach>
                </table>
            </div>
        </div>
        <!-- para llamar al modal -->
        <div class="modal fade" id="modalEditSupervisor" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header bg-warning">
                        <h5 class="modal-title" id="exampleModalLabel">Editar usuario tienda</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body" id="modal-content">

                    </div>
                </div>
            </div>
        </div>
        <!-- boostrap-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
        <!-- dataTables-->
        <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
        <script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.11.3/datatables.min.js"></script>
        <!-- dataTables arhivo-local -->
        <script src="../../resources/js/dataTables.js" type="text/javascript"></script>
    </body>
</html>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.addHeader("Pragma", "no-cache");
    if (session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
%>