<%-- 
    Document   : reporteTiendas
    Created on : 4/03/2023, 18:10:48
    Author     : luis
--%>
<%@page import="java.util.List"%>
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
        <title>Reportes</title>

        <link rel="canonical" href="https://getbootstrap.com/docs/5.1/examples/navbar-fixed/">
        <!-- Bootstrap core CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <!-- icons -->
        <script src="https://kit.fontawesome.com/6d0db64a1f.js" crossorigin="anonymous"></script>
        <!-- dataTables -->
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.11.3/datatables.min.css"/>
        <link href="../../resources/css/general.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <jsp:include page="menuAdministrador.jsp"></jsp:include>
        <c:if test="${reporte=='1' || reporte =='2'}">
            <div class="container mt-5">
                <div class="card-header">
                    <br>
                    <div class="text-center">
                        <h1>Lista de tiendas con más pedidos</h1>
                        <hr>
                    </div>
                    <form class="row g-3" method="POST" action="${pageContext.request.contextPath}/ControlReportesAdmin?tarea=listarTiendasMasPedidosFiltrado">
                        <div class="col-auto">
                            <label for="inputPassword2" class="">Estado del pedido</label>
                            <input class="form-control" name="estado" list="datalistOptions" id="exampleDataList" placeholder="Elegir estado..." required="">
                            <datalist id="datalistOptions">
                                <c:forEach items="${estadosPedido}" var="estadoP">
                                    <option value="${estadoP.estado}">
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
                            <button type="submit" class="btn btn-primary mb-3"><i class="fas fa-search"></i>Realizar consulta</button>
                        </div>
                        <div class="col-auto">
                            <br>
                            <a class="btn btn-primary mb-3" href="${pageContext.request.contextPath}/ControlReportesAdmin?tarea=listarTiendasMasPedidos"> 
                                <i class="fas fa-list"></i>
                                Mostrar todos</a>
                        </div>
                    </form>
                </div>
                <div class="card-body">
                    <table id="storeTable" class="table table-striped">
                        <thead class="table-dark">
                            <tr>
                                <th scope="col">Número de pedidos</th>
                                <th scope="col">Código de tienda</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% List<String[]> tiendasMasPedidos = (List<String[]>) request.getSession().getAttribute("listaTiendasMasPedidos");
                                for (int i = 0; i < tiendasMasPedidos.size(); i++) {%>
                            <tr>
                                <td><% out.print(tiendasMasPedidos.get(i)[0]);%></td>
                                <td><% out.print(tiendasMasPedidos.get(i)[1]);%></td>
                            </tr>
                            <% } %>
                    </table>
                </div>
            </div>
        </c:if>
        <c:if test="${reporte=='3' || reporte == '4'}">
            <div class="container mt-5">
                <div class="card-header">
                    <br>
                    <div class="text-center">
                        <h1>Lista de usuarios con más envíos generados</h1>
                        <hr>
                    </div>
                    <form class="row g-3" method="POST" action="${pageContext.request.contextPath}/ControlReportesAdmin?tarea=listarUsuarioMasEnviosFiltrado">
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
                            <button type="submit" class="btn btn-primary mb-3"><i class="fas fa-search"></i>Realizar consulta</button>
                        </div>
                        <div class="col-auto">
                            <br>
                            <a class="btn btn-primary mb-3" href="${pageContext.request.contextPath}/ControlReportesAdmin?tarea=listarUsuarioMasEnvios"> 
                                <i class="fas fa-list"></i>
                                Mostrar todos</a>
                        </div>
                    </form>
                </div>
                <div class="card-body">
                    <table id="storeTable" class="table table-striped">
                        <thead class="table-dark">
                            <tr>
                                <th scope="col">Número de pedidos</th>
                                <th scope="col">Código de usuario</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% List<String[]> usuarioMasEnvios = (List<String[]>) request.getSession().getAttribute("listaUsuariosMasEnvios");
                                for (int i = 0; i < usuarioMasEnvios.size(); i++) {%>
                            <tr>
                                <td><% out.print(usuarioMasEnvios.get(i)[0]);%></td>
                                <td><% out.print(usuarioMasEnvios.get(i)[1]);%></td>
                            </tr>
                            <% } %>
                    </table>
                </div>
            </div>

        </c:if>
        <c:if test="${reporte=='5' || reporte == '6'}">
            <div class="container mt-5">
                <div class="card-header">
                    <br>
                    <div class="text-center">
                        <h1>Lista de usuarios con más pedidos generados</h1>
                        <hr>
                    </div>
                    <form class="row g-3" method="POST" action="${pageContext.request.contextPath}/ControlReportesAdmin?tarea=listarUsuariosMasPedidosFiltrado">
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
                            <button type="submit" class="btn btn-primary mb-3"><i class="fas fa-search"></i>Realizar consulta</button>
                        </div>
                        <div class="col-auto">
                            <br>
                            <a class="btn btn-primary mb-3" href="${pageContext.request.contextPath}/ControlReportesAdmin?tarea=listarUsuariosMasPedidos"> 
                                <i class="fas fa-list"></i>
                                Mostrar todos</a>
                        </div>
                    </form>
                </div>
                <div class="card-body">
                    <table id="storeTable" class="table table-striped">
                        <thead class="table-dark">
                            <tr>
                                <th scope="col">Número de pedidos</th>
                                <th scope="col">Código de usuario</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% List<String[]> tiendasMasEnvios = (List<String[]>) request.getSession().getAttribute("listarUsuariosMasPedidos");
                                for (int i = 0; i < tiendasMasEnvios.size(); i++) {%>
                            <tr>
                                <td><% out.print(tiendasMasEnvios.get(i)[0]);%></td>
                                <td><% out.print(tiendasMasEnvios.get(i)[1]);%></td>
                            </tr>
                            <% } %>
                    </table>
                </div>
            </div>
        </c:if>

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