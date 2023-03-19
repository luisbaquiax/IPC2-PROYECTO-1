<%-- 
    Document   : enviosGenerados
    Created on : 12/03/2023, 00:31:06
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
        <title>Envíos generados</title>

        <link rel="canonical" href="https://getbootstrap.com/docs/5.1/examples/navbar-fixed/">
        <!-- Bootstrap core CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <!-- ajax -->
        <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
        <!-- icons -->
        <script src="https://kit.fontawesome.com/6d0db64a1f.js" crossorigin="anonymous"></script>
        <!-- dataTables -->
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.11.3/datatables.min.css"/>
        <link href="../../resources/css/general.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <jsp:include page="menuBodega.jsp"></jsp:include>
            <div class="container mt-5">
                <div class="card-header">
                    <div class="text-center">
                        <h1>Lista de envíos generados</h1>
                        <hr>
                    </div>
                    <form class="row g-3" method="POST" action="${pageContext.request.contextPath}/ControlBodegaReporteEnvios?tarea=listarEnviosGeneradosPorTienda">
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
                        <label for="inputPassword2" class="">Fecha1: </label>
                        <input class="form-control" type="date" name="fecha1" id="inputPassword2" required="">
                    </div>
                    <div class="col-auto">
                        <label for="inputPassword2" class="">Fecha2: </label>
                        <input class="form-control"  type="date" name="fecha2" id="inputPassword2"  required="">
                    </div>
                    <div class="col-auto">
                        <br>
                        <button type="submit" class="btn btn-primary mb-3"><i class="fas fa-search"></i>Buscar por tienda</button>
                    </div>
                    <div class="col-auto">
                        <br>
                        <a class="btn btn-primary mb-3" href="${pageContext.request.contextPath}/ControlBodegaReporteEnvios?tarea=listarEnviosGenerados"> 
                            <i class="fas fa-list"></i>
                            Mostrar todos</a>
                    </div>
                </form>
                <h3 class="text-success">${msjBodegaReporteEnvio}</h3>
            </div>
            <div class="card-body">
                <table id="storeTable" class="table table-striped">
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Tienda</th>
                            <th scope="col">Fecha</th>
                            <th scope="col">Estado</th>
                            <th scope="col">Total</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${enviosGenerados}" var="envioGenerado">
                            <tr>
                                <td>${envioGenerado.id}</td>
                                <td>${envioGenerado.codigoTienda}</td>
                                <td>${envioGenerado.fechaSalida}</td>
                                <td>${envioGenerado.estado}</td>
                                <td><fmt:formatNumber value="${envioGenerado.total}" type="currency"></fmt:formatNumber></td>
                                </tr>
                        </c:forEach>
                </table>
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