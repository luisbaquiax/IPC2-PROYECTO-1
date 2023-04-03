<%-- 
    Document   : reporteEnviosRecibidos
    Created on : 2 abr. 2023, 19:54:36
    Author     : Luis
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
        <title>Reporte de envíos</title>

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
            <div class="container mt-4">
                <div class="row">
                    <div class="col-md-9">
                        <div class="card-header">
                            <br>
                            <div class="text-center">
                                <h1>Envíos recibidos</h1>
                                <hr>
                            </div>
                            <div class="row">
                                <div class="col col-md-6">
                                    <a href="${pageContext.request.contextPath}/ControlReporteEnvios?tarea=enviosRecibidos"
                                   class="btn btn-primary btn-block w-25" ><i class="fas fa-list-ol"></i> Ver todos</a>
                            </div>
                        </div>
                        <div class="row">
                            <form method="POST" action="${pageContext.request.contextPath}/ControlReporteEnvios?tarea=enviosRecibidosFecha">
                                <div class="row g-3 mt-1">
                                    <div class="col-auto">
                                        <label for="labelFecha1" class="form-label">Fecha 1:</label>
                                        <input name="fecha1" required="" type="date"
                                               class="form-control" aria-describedby="basic-addon1">
                                    </div>
                                    <div class="col-auto">
                                        <label for="labelFecha2" class="form-label">Fecha 2:</label>
                                        <input name="fecha2" required="" type="date"
                                               class="form-control" aria-describedby="basic-addon1">
                                    </div>
                                    <div class="col-auto">
                                        <br>
                                        <button class="btn btn-info btn-block mt-2" type="submit"><i class="fas fa-search"></i> Listar por fecha</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="card-body">
                        <c:if test="${msjEnvioRecibidoReporte!=null}">
                            <h3 class="text-danger">${msjEnvioRecibidoReporte}</h3>
                        </c:if>
                        <table id="storeTable" class="table table-striped">
                            <thead class="thead-dark">
                                <tr>
                                    <th scope="col">ID</th>
                                    <th scope="col">Fecha salida</th>
                                    <th scope="col">Fecha recibido</th>
                                    <th scope="col">Total</th>
                                    <th scope="col">Estado</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${reporteEnviosRecibidos}" var="p">
                                    <tr>
                                        <th>${p.id}</th>
                                        <th>${p.fechaSalida}</th>
                                        <th>${p.fechaLlegada}</th>
                                        <td><fmt:formatNumber value="${p.total}" type="currency"/></td>
                                        <td>${p.estado}</td>
                                    </tr>
                                </c:forEach>
                        </table>
                    </div>
                </div>
                <div class="col-md-3 mt-5">
                    <div class="card text-center bg-danger text-white mb-3">
                        <div class="card-body">
                            <h3>No. Incidencias activas</h3>
                            <h4 class="display-4">
                                ${cantidadIncidenciasActivas}
                            </h4>
                        </div>
                    </div>
                    <div class="card text-center bg-warning text-white mb-3">
                        <div class="card-body">
                            <h3>No. devoluciones activas</h3>
                            <h4 class="display-4">
                               ${cantidadDevolucionesActivas}
                            </h4>
                        </div>
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
