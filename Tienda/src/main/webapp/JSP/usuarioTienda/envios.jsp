<%-- 
    Document   : envios
    Created on : 2/03/2023, 23:30:52
    Author     : luis
--%>
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
        <title>Env�os</title>

        <link rel="canonical" href="https://getbootstrap.com/docs/5.1/examples/navbar-fixed/">
        <!-- Bootstrap core CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <!-- icons -->
        <script src="https://kit.fontawesome.com/6d0db64a1f.js" crossorigin="anonymous"></script>
        <!-- ajax -->
        <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
        <!-- dataTables -->
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.11.3/datatables.min.css"/>
        <link href="../../resources/css/general.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <jsp:include page="menuTienda.jsp"></jsp:include>
            <div class="container mt-5">
                <div class="card-header">
                    <br>
                    <div class="text-center">
                        <h1>Listado de env�os</h1>
                    </div>
                    <div class="row">
                        <div class="col col-md-6">
                            <a href="${pageContext.request.contextPath}/ControlEnviosTienda?tarea=verTodos"
                           class="btn btn-primary btn-block w-25" ><i class="fas fa-list-ol"></i> Ver todos</a>
                    </div>
                </div>
                <form method="POST" action="${pageContext.request.contextPath}/ControlEnviosTienda?tarea=buscarEnvio">
                    <div class="row g-3 mt-1">
                        <h3 class="text-success">${msj}</h3>
                        <div class="col">
                            <label for="exampleDataList" class="form-label">Buscar por ID:</label>
                            <input name="idEnvio" required="" type="number"
                                   class="form-control" placeholder="Username" value="1" min="1" aria-describedby="basic-addon1">
                        </div>
                        <div class="col">
                            <br>
                            <button class="btn btn-info btn-block mt-2 w-25" type="submit"><i class="fas fa-search"></i></button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="card-body">
                <table id="storeTable" class="table table-striped">
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Fecha salida</th>
                            <th scope="col">Total</th>
                            <th scope="col">Estado</th>
                            <th scope="col">Detalle</th>
                            <th scope="col">Marcar</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${envios}" var="p">
                            <tr>
                                <th>${p.id}</th>
                                <th>${p.fechaSalida}</th>
                                <td><fmt:formatNumber value="${p.total}" type="currency"/></td>
                                <td>${p.estado}</td>
                                <td>
                                    <a href="#"
                                       onclick="openDetalleEnvio('${pageContext.request.contextPath}/ControlEnviosTienda?tarea=openDetalleEnvio&id=${p.id}')"
                                       class="btn btn-warning btn-block" ><i class="fa-solid fa-angles-down"></i> Mostrar detalle</a>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/ControlEnviosTienda?tarea=marcarEnvio&id=${p.id}" 
                                       class="btn btn-primary btn-block" ><i class="fas fa-check"></i>Como recibido</a>
                                </td>
                            </tr>
                        </c:forEach>
                </table>
            </div>
        </div>

        <!-- para llamar al modal -->
        <div class="modal fade" id="modalDetalleEnvio" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header bg-warning">
                        <h5 class="modal-title" id="exampleModalLabel">Detalle del env�o</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body" id="modal-detalle-envio">

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
        <script src="../../resources/js/envio.js" type="text/javascript"></script>
    </body>
</html>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.addHeader("Pragma", "no-cache");
    if (session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
%>