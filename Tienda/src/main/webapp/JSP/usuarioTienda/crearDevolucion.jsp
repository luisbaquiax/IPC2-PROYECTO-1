<%-- 
    Document   : crearDevolucion
    Created on : 21/03/2023, 02:16:13
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
        <title>Generar devolución</title>

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
        <jsp:include page="menuTienda.jsp"></jsp:include>
            <div class="container mt-5">
                <div class="card-header">
                    <div class="text-center">
                        <h1>Agregar productos a la devolución</h1>
                        <hr>
                    </div>
                <c:if test="${msjDevoluciones!=null}">
                    <div class="alert alert-primary alert-dismissible fade show" role="alert">
                        ${msjDevoluciones}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                <form method="POST" action="${pageContext.request.contextPath}/ControlDevolciones?tarea=agregarProducto">
                    <div class="row g-3">
                        <div class="col-md-3">
                            <label for="exampleDataList" class="form-label">Productos en el envío:</label>
                            <input name="codigoProducto" required="" class="form-control" list="datalistOptions" id="exampleDataList" 
                                   placeholder="Elegir producto... codigo,cantidad">
                            <datalist id="datalistOptions">
                                <c:forEach items="${detalleEnvioDev}" var="producto">
                                    <option value="${producto.codigoProducto},${producto.cantidad}"/>
                                </c:forEach>
                            </datalist>
                        </div>
                        <div class="col-md-3">
                            <label for="exampleDataList" class="form-label">Cantidad:</label>
                            <input name="cantidad" required="" type="number"
                                   class="form-control" placeholder="" value="1" min="1" aria-describedby="basic-addon1">
                        </div>
                        <div class="col-md-3">
                            <label for="exampleDataList1" class="form-label">Motivos de la devolución:</label>
                            <input name="motivo" required="" class="form-control" list="datalistOptions1" id="exampleDataList1" 
                                   placeholder="Elegir motivo...">
                            <datalist id="datalistOptions1">
                                <c:forEach items="${motivosDevolucion}" var="m">
                                    <option value="${m.motivo}"/>
                                </c:forEach>
                            </datalist>
                        </div>
                        <div class="col-auto">
                            <br>
                            <button class="btn btn-primary btn-block mt-2 w-100" type="submit"><i class="fa-solid fa-plus fa-beat"></i> Agregar Producto</button>
                        </div>
                    </div>
                </form>
                <form method="POST" action="${pageContext.request.contextPath}/ControlDevolciones?tarea=realizarDevolucion">
                    <div class="row g-3 mt-1">
                        <div class="col-auto">
                            <label for="exampleDataList" class="form-label">Fecha de la devolución:</label>
                            <input name="fecha" required="" type="date"
                                   class="form-control" aria-describedby="basic-addon1">
                        </div>
                        <div class="col-auto">
                            <br>
                            <button class="btn btn-success btn-block mt-2 w-100" type="submit"><i class="fa-solid fa-paper-plane"></i> Generar devolución</button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="card-body">
                <table id="storeTable" class="table table-striped">
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col">Producto</th>
                            <th scope="col">Cantidad</th>
                            <th scope="col">Precio unitario</th>
                            <th scope="col">Subtotal</th>
                            <th scope="col">Motivo</th>
                            <th scope="col">Accion</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${listaDetalleDev}" var="i">
                            <tr>
                                <td>${i.codigoProducto}</td>
                                <td>${i.cantidad}</td>
                                <td>${i.costoUnitario}</td>
                                <td>${i.subTotal}</td>
                                <td>${i.motivo}</td>
                                <td>
                                    <a class="btn btn-block btn-danger" 
                                       href="${pageContext.request.contextPath}/ControlDevolciones?tarea=quitarProducto&codigo=${i.codigoProducto}&motivo=${i.motivo}">
                                        <i class="fas fa-trash-alt"></i> Quitar
                                    </a>
                                </td>
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