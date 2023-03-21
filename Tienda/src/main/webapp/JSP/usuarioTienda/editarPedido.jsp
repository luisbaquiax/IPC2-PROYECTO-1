<%-- 
    Document   : editarPedido
    Created on : 19/03/2023, 21:46:27
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
        <title>Pedidos rechazados</title>

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
                    <br>
                    <div class="text-center">
                        <h1>Edición de pedidos</h1>
                    </div>
                    <div class="row text-center">
                        <div class="col-auto">
                            <a href="${pageContext.request.contextPath}/ControlPedidoTienda?tarea=guardarCambios"
                           class="btn btn-primary btn-block" ><i class="fa-solid fa-floppy-disk-circle-arrow-right"></i> Guardar cambios</a>
                    </div>
                </div>
                <hr>
                <c:if test="${msjDetallePedidoRechazado!=null}">
                    <div class="alert alert-primary alert-dismissible fade show" role="alert">
                        ${msjDetallePedidoRechazado}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                <form method="POST" action="${pageContext.request.contextPath}/ControlPedidoTienda?tarea=agregarProductoPedidoRechazado">
                    <div class="row g-3 mt-1">
                        <div class="col">
                            <label for="exampleDataList" class="form-label">Productos</label>
                            <input name="codigoProducto" required="" class="form-control" list="datalistOptions" id="exampleDataList" placeholder="Búsque el producto...">
                            <datalist id="datalistOptions">
                                <c:forEach items="${productos}" var="producto">
                                    <option value="${producto.codigo},${producto.nombre}"/>
                                </c:forEach>
                            </datalist>
                        </div>
                        <div class="col">
                            <label for="exampleDataList" class="form-label">Cantidad:</label>
                            <input name="cantidadProducto" required="" type="number"
                                   class="form-control" placeholder="Username" value="1" min="1" aria-describedby="basic-addon1">
                        </div>
                        <div class="col">
                            <br>
                            <button class="btn btn-primary btn-block mt-2 w-100" type="submit"><i class="fas fa-plus"></i> Agregar Producto</button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="card-body">
                <table id="storeTable" class="table table-striped">
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col">Pedido</th>
                            <th scope="col">Producto</th>
                            <th scope="col">Cantidad</th>
                            <th scope="col">Precio unitario</th>
                            <th scope="col">Subtotal</th>
                            <th scope="col">Acción</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${productosPedido}" var="d">
                            <tr>
                                <th>${d.idPedido}</th>
                                <th>${d.codigoProducto}</th>
                                <th>${d.cantidad}</th>
                                <td><fmt:formatNumber value="${d.precioUnitario}" type="currency"/></td>
                                <td><fmt:formatNumber value="${d.subtotal}" type="currency"/></td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/ControlPedidoTienda?tarea=editarDetallePedido&id=${d.idPedido}" 
                                       class="btn btn-warning btn-block" ><i class="fa-solid fa-angles-down"></i> Editar</a>
                                    <a href="${pageContext.request.contextPath}/ControlPedidoTienda?tarea=quitarDetallePedido&id=${d.idPedido}&codigo=${d.codigoProducto}" 
                                       class="btn btn-danger btn-block" ><i class="fas fa-trash-alt"></i> Quitar</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <!-- boostrap-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
        <!-- dataTables-->
        <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
        <script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.11.3/datatables.min.js"></script>
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