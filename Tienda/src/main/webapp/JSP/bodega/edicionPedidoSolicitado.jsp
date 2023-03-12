<%-- 
    Document   : edicionPedidoSolicitado
    Created on : 10/03/2023, 21:46:13
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
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <script src="../../resources/js/detallePedido.js" type="text/javascript"></script>
        <title>Listado de pedidos solicitados</title>

        <link rel="canonical" href="https://getbootstrap.com/docs/5.1/examples/navbar-fixed/">
        <!-- Bootstrap core CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <!-- CSS -->
        <link href="../../resources/css/general.css" rel="stylesheet" type="text/css"/>
        <!-- ajax -->
        <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
        <!-- icons -->
        <script src="https://kit.fontawesome.com/6d0db64a1f.js" crossorigin="anonymous"></script>
        <!-- dataTables -->
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.11.3/datatables.min.css"/>
        <!-- js -->
    </head>
    <body>
        <jsp:include page="menuBodega.jsp"></jsp:include>
            <div class="container mt-5">
                <div class="card-header">
                    <div class="text-center">
                        <h1>Lista de productos del pedido solicitado</h1>
                        <hr>
                    </div>
                    <form class="row g-3" method="POST" action="${pageContext.request.contextPath}/ControlBodegaEnvios?tarea=agregarProductoAlEnvio">
                    <div class="col-auto">
                        <label for="inputPassword2" class="">Productos:</label>
                        <input class="form-control" name="codigoProducto" list="datalistOptions" id="exampleDataList" placeholder="Producto a agregar..." required="">
                        <datalist id="datalistOptions">
                            <c:forEach items="${listaProductos}" var="listProducto">
                                <option value="${listProducto.codigo},${listProducto.nombre},Cantidad: ${listProducto.existencia}">
                                </c:forEach>
                        </datalist>
                    </div>
                    <div class="col-auto">
                        <label for="cantidadProductos">Cantidad:</label>
                        <input type="number" name="cantidad" class="form-control" id="inputPassword2" min="1" placeholder="Cantidad de productos" required="">
                    </div>
                    <div class="col-auto">
                        <br>
                        <button type="submit" class="btn btn-primary mb-3"><i class="fas fa-plus-circle"></i> Agregar producto</button>
                    </div>
                    <div class="col-auto">
                        <br> 
                        <a class="btn btn-success mb-3" href="${pageContext.request.contextPath}/ControlBodegaEnvios?tarea=realizarEnvioModificado&id=${pedidoSolicitado.id}"> 
                            <i class="fa-solid fa-share-from-square"></i>
                            Realizar envío</a>
                    </div>
                    <div class="col-auto">
                        <br> 
                        <a class="btn btn-danger mb-3" href="${pageContext.request.contextPath}/ControlBodegaEnvios?tarea=listarPedidosSolicitados"> 
                            Cancelar</a>
                    </div>
                </form>
                <h3 class="text-success">${msjedicionPedidoSolicitado}</h3> 
            </div>
            <div class="card-body">
                <table id="storeTable" class="table table-striped">
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col">Pedido</th>
                            <th scope="col">Código producto</th>
                            <th scope="col">Cantidad</th>
                            <th scope="col">Precio unitario</th>
                            <th scope="col">Subtotal</th>
                            <th scope="col">Acción</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${productosPedido}" var="productoPedido">
                            <tr>
                                <td>${productoPedido.idPedido}</td>
                                <td>${productoPedido.codigoProducto}</td>
                                <td>${productoPedido.cantidad}</td>
                                <td><fmt:formatNumber value="${productoPedido.precioUnitario}" type="currency"></fmt:formatNumber></td>
                                <td><fmt:formatNumber value="${productoPedido.subtotal}" type="currency"></fmt:formatNumber></td>
                                    <td>
                                        <a href="#" 
                                           onclick="openEditProductoSolicitado('${pageContext.request.contextPath}/ControlBodegaEnvios?tarea=openModalEditProductoSolicitado&codigoProducto=${productoPedido.codigoProducto}')"
                                        class="btn btn-warning btn-block">
                                        <i class="fa-solid fa-angles-down"></i> 
                                        Modificar cantidad</a>
                                    <a href="${pageContext.request.contextPath}/ControlBodegaEnvios?tarea=quitarProductoEnvio&codigoProducto=${productoPedido.codigoProducto}" 
                                       class="btn btn-danger btn-block" ><i class="fas fa-trash-alt"></i>Quitar</a>
                                </td>
                            </tr>
                        </c:forEach>
                </table>
            </div>
        </div>
        <!-- para llamar al modal -->
        <div class="modal fade" id="modalEditCantidadProductoPedido" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header bg-warning">
                        <h5 class="modal-title" id="exampleModalLabel">Editar cantidad del producto:</h5>
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
    </body>
</html>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.addHeader("Pragma", "no-cache");
    if (session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
%>