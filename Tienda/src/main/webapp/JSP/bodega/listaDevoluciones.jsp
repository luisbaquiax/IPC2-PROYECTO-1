<%-- 
    Document   : listaDevoluciones
    Created on : 13/03/2023, 23:03:58
    Author     : luis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- para dar formato el texto-->
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- SELECCIONAMOS LA LOCALIDAD -->
<fmt:setLocale value="es_GT"/>
<!doctype html>
<html lang="en">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <title>Reporte devoluciones</title>

        <link rel="canonical" href="https://getbootstrap.com/docs/5.1/examples/navbar-fixed/">
        <!-- Bootstrap core CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <!-- ajax -->
        <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
        <!-- icons -->
        <script src="https://kit.fontawesome.com/6d0db64a1f.js" crossorigin="anonymous"></script>
        <!-- dataTables -->
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.11.3/datatables.min.css"/>
        <!-- css -->
        <link href="../../resources/css/general.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <jsp:include page="menuBodega.jsp"></jsp:include>
            <div class="container mt-5">
                <div class="card-header">
                    <div class="text-center">
                        <h1>Lista de devoluciones activas</h1>
                        <hr>
                    </div>
                    <form class="row g-3" method="POST" action="${pageContext.request.contextPath}/ControlBodegaDevoluciones?tarea=listarDevolucionesPorTienda">
                    <div class="col-auto">
                        <label for="inputPassword2" class="">Tiendas a cargo:</label>
                        <input class="form-control" name="tienda" list="datalistOptions" id="exampleDataList" placeholder="Elegir tienda..." required="">
                        <datalist id="datalistOptions">
                            <c:forEach items="${tiendasBodega}" var="store">
                                <option value="${store.codigo}">
                                </c:forEach>
                        </datalist>
                    </div>
                    <div class="col-auto">
                        <br>
                        <button type="submit" class="btn btn-primary mb-3"><i class="fas fa-search"></i>Buscar por tienda</button>
                    </div>
                    <div class="col-auto">
                        <br>
                        <a class="btn btn-primary mb-3" href="${pageContext.request.contextPath}/ControlBodegaDevoluciones?tarea=listarDevoluciones"> 
                            <i class="fas fa-list"></i>
                            Mostrar todos</a>
                    </div>
                </form>
                <h3 class="text-success">${msjBodegaReporteDev}</h3>
            </div>
            <div class="card-body">
                <table id="storeTable" class="table table-striped">
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Fecha</th>
                            <th scope="col">Tienda</th>
                            <th scope="col">Estado</th>
                            <th scope="col">Total</th>
                            <th scope="col">Accion</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${listadoDevoluciones}" var="devolucionList">
                            <tr>
                                <td>${devolucionList.id}</td>
                                <td>${devolucionList.fecha}</td>
                                <td>${devolucionList.codigoTienda}</td>
                                <td>${devolucionList.estado}</td>
                                <td><fmt:formatNumber type="currency" value="${devolucionList.total}"></fmt:formatNumber></td>
                                    <td>
                                        <a href="#" 
                                           class="btn btn-warning btn-block" 
                                           onclick="openDetalleDevolucion('${pageContext.request.contextPath}/ControlBodegaDevoluciones?tarea=openModalDetalleDevolucion&id=${devolucionList.id}')"
                                        ><i class="fa-solid fa-angles-down"></i> Ver detalle</a>
                                    <c:if test="${devolucionList.estado=='ACTIVA'}">
                                        <a href="${pageContext.request.contextPath}/ControlBodegaDevoluciones?tarea=aprobar&id=${devolucionList.id}" 
                                           class="btn btn-success btn-block" ><i class="fa-solid fa-thumbs-up"></i> Aprobar</a>
                                    </c:if>
                                    <c:if test="${devolucionList.estado=='RECHAZADA'}">
                                        <a href="#"
                                           onclick="openModalRechazarDevolucion('${pageContext.request.contextPath}/ControlBodegaDevoluciones?tarea=openModalRechazar&id=${devolucionList.id}')"
                                           class="btn btn-danger btn-block" ><i class="fas fa-trash-alt"></i> Rechazar</a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                </table>
            </div>
        </div>
        <!-- para llamar al modal -->
        <div class="modal fade" id="modalDetalleDevolucion" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-xl">
                <div class="modal-content">
                    <div class="modal-header bg-warning">
                        <h5 class="modal-title" id="exampleModalLabel">Detalle de la devolución</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body" id="modal-dev-detalle">

                    </div>
                </div>
            </div>
        </div>
        <!-- para llamar al modal -->
        <div class="modal fade" id="modalRechazarDevolucion" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-xl">
                <div class="modal-content">
                    <div class="modal-header bg-warning">
                        <h5 class="modal-title" id="exampleModalLabel">Motivo del rechazo de la devolución</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body" id="modal-dev-rechazo">

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
        <!-- js -->
        <script src="../../resources/js/devolucion.js" type="text/javascript"></script>
    </body>
</html>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.addHeader("Pragma", "no-cache");
    if (session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
%>