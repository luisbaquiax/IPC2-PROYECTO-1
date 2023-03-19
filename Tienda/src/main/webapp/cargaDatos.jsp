<%-- 
    Document   : cargaDatos
    Created on : 16/03/2023, 01:14:25
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
        <title>Carga de datos</title>

        <link rel="canonical" href="https://getbootstrap.com/docs/5.1/examples/navbar-fixed/">
        <!-- Bootstrap core CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <!-- icons -->
        <script src="https://kit.fontawesome.com/6d0db64a1f.js" crossorigin="anonymous"></script>
        <!-- dataTables -->
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.11.3/datatables.min.css"/>
        <link href="resources/css/general.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>


        <div class="container">
            <nav>
                <div class="nav nav-tabs fixed-top navbar-light" id="nav-tab" role="tablist">
                    <button class="nav-link active" id="nav-productos-tab" data-bs-toggle="tab" data-bs-target="#nav-productos" 
                            type="button" role="tab" aria-controls="nav-productos" aria-selected="true">Productos</button>
                    <button class="nav-link" id="nav-tiendas-tab" data-bs-toggle="tab" data-bs-target="#nav-tiendas" 
                            type="button" role="tab" aria-controls="nav-tiendas" aria-selected="false">Tiendas</button>
                    <button class="nav-link" id="nav-usuarios-tab" data-bs-toggle="tab" data-bs-target="#nav-users" 
                            type="button" role="tab" aria-controls="nav-users" aria-selected="false">Usuarios</button>
                    <button class="nav-link" id="nav-pedidos-tab" data-bs-toggle="tab" data-bs-target="#nav-pedidos" 
                            type="button" role="tab" aria-controls="nav-pedidos" aria-selected="false">Pedidos</button>
                    <button class="nav-link" id="nav-envios-tab" data-bs-toggle="tab" data-bs-target="#nav-envios" 
                            type="button" role="tab" aria-controls="nav-envios" aria-selected="false">Envios</button>
                    <button class="nav-link" id="nav-incidencias-tab" data-bs-toggle="tab" data-bs-target="#nav-incidencias" 
                            type="button" role="tab" aria-controls="nav-incidencias" aria-selected="false">Incidencias</button>
                    <button class="nav-link" id="nav-devoluciones-tab" data-bs-toggle="tab" data-bs-target="#nav-devoluciones" 
                            type="button" role="tab" aria-controls="nav-devoluciones" aria-selected="false">Devoluciones</button>
                    <button class="nav-link" id="nav-devoluciones-tab" data-bs-toggle="tab" data-bs-target="#nav-errores" 
                            type="button" role="tab" aria-controls="nav-errores" aria-selected="false">Errores en el archivo de entrada</button>
                    <form class="" method="POST" action="${pageContext.request.contextPath}/ControlArchivo" enctype="multipart/form-data">
                        <div class="input-group">
                            <input name="archivo" accept=".json" type="file" class="form-control" id="inputGroupFile04" aria-describedby="inputGroupFileAddon04" aria-label="Upload">
                            <button class="btn btn-outline-secondary" type="submit" id="inputGroupFileAddon04">Procesar datos</button>
                            <a class="btn btn-block btn-primary mx-5"
                               href="${pageContext.request.contextPath}/ControlArchivo?tarea=subirDatos"">Subir datos</a>
                        </div>
                    </form>
                </div>
            </nav>
            <c:if test="${msjeDatos}!=null">
                <div class="alert alert-primary alert-dismissible fade show mt-5" role="alert">
                    <strong>Mensaje: </strong> ${msjeDatos}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>

            <c:if test="${manejoDatos!=null}">
                <div class="tab-content mt-5" id="nav-tabContent">
                    <div class="tab-pane fade show active" id="nav-productos" role="tabpanel" aria-labelledby="nav-productos-tab">
                        <div class="container">
                            <div class="card-header">
                                <div class="text-center">
                                    <h1>Productos a ingresar</h1>
                                </div>
                            </div>
                            <div class="card-body">
                                <table id="" class="table table-striped">
                                    <thead class="thead-dark">
                                        <tr>
                                            <th scope="col">Código</th>
                                            <th scope="col">Nombre</th>
                                            <th scope="col">Costo</th>
                                            <th scope="col">Precio</th>
                                            <th scope="col">Existencia</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${manejoDatos.productos}" var="u">
                                            <tr>
                                                <td>${u.codigo}</td>
                                                <td>${u.nombre}</td>
                                                <td><fmt:formatNumber value="${u.costo}" type="currency"></fmt:formatNumber></td>
                                                <td><fmt:formatNumber value="${u.precio}" type="currency"></fmt:formatNumber></td>
                                                <td>${u.existencia}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="tab-pane fade" id="nav-tiendas" role="tabpanel" aria-labelledby="nav-tiendas-tab">
                        <div class="container">
                            <div class="card-header">
                                <div class="text-center">
                                    <h1>Tiendas a ingresar</h1>
                                </div>
                            </div>
                            <div class="card-body">
                                <table id="" class="table table-striped">
                                    <thead class="thead-dark">
                                        <tr>
                                            <th scope="col">Código</th>
                                            <th scope="col">Direccion</th>
                                            <th scope="col">Tipo</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${manejoDatos.tiendas}" var="u">
                                            <tr>
                                                <td>${u.codigo}</td>
                                                <td>${u.direccion}</td>
                                                <td>${u.tipo}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="tab-pane fade" id="nav-users" role="tabpanel" aria-labelledby="nav-users-tab">
                        <div class="container">
                            <div class="card-header">
                                <div class="text-center">
                                    <h1>Usuarios a ingresar</h1>
                                </div>
                            </div>
                            <div class="card-body">
                                <table id="" class="table table-striped">
                                    <thead class="thead-dark">
                                        <tr>
                                            <th scope="col">Código</th>
                                            <th scope="col">Nombre</th>
                                            <th scope="col">Nombre de usuario</th>
                                            <th scope="col">Tipo</th>
                                            <th scope="col">Contraseña</th>
                                            <th scope="col">Correo electrónico</th>
                                            <th scope="col">Estado</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${manejoDatos.usuarios}" var="u">
                                            <tr>
                                                <td>${u.codigo}</td>
                                                <td>${u.nombre}</td>
                                                <td>${u.nombreUsuario}</td>
                                                <td>${u.tipo}</td>
                                                <td>${u.password}</td>
                                                <td>${u.email}</td>
                                                <td>
                                                    <c:if test="${u.estado==true}">Activo</c:if>
                                                    </td>
                                                </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="tab-pane fade" id="nav-pedidos" role="tabpanel" aria-labelledby="nav-pedidos-tab">
                        <div class="container">
                            <div class="card-header">
                                <div class="text-center">
                                    <h1>Pedidos a ingresar</h1>
                                </div>
                            </div>
                            <div class="card-body">
                                <table id="" class="table table-striped">
                                    <thead class="thead-dark">
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col">Fecha</th>
                                            <th scope="col">Total</th>
                                            <th scope="col">Usuario tienda</th>
                                            <th scope="col">Código tienda</th>
                                            <th scope="col">Estado</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${manejoDatos.pedidos}" var="u">
                                            <tr>
                                                <td>${u.id}</td>
                                                <td>${u.fecha}</td>
                                                <td><fmt:formatNumber value="${u.total}" type="currency"></fmt:formatNumber></td>
                                                <td>${u.usuarioTienda}</td>
                                                <td>${u.codigoTienda}</td>
                                                <td>${u.estado}</td>
                                            </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="tab-pane fade" id="nav-envios" role="tabpanel" aria-labelledby="nav-envios-tab">
                        <div class="container">
                            <div class="card-header">
                                <div class="text-center">
                                    <h1>Envíos</h1>
                                </div>
                            </div>
                            <div class="card-body">
                                <table id="" class="table table-striped">
                                    <thead class="thead-dark">
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col">Fecha salida</th>
                                            <th scope="col">Fecha recibido</th>
                                            <th scope="col">Usuario tienda</th>
                                            <th scope="col">Código tienda</th>
                                            <th scope="col">Estado</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${manejoDatos.envios}" var="u">
                                            <tr>
                                                <td>${u.id}</td>
                                                <td>${u.fechaSalida}</td>
                                                <td>${u.fechaLlegada}</td>
                                                <td><fmt:formatNumber value="${u.total}" type="currency"></fmt:formatNumber></td>
                                                <td>${u.usuarioBodega}</td>
                                                <td>${u.codigoTienda}</td>
                                                <td>${u.estado}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- incidencias -->
                    <div class="tab-pane fade" id="nav-incidencias" role="tabpanel" aria-labelledby="nav-incidencias-tab">
                        <div class="container">
                            <div class="card-header">
                                <div class="text-center">
                                    <h1>Incidencias a ingresar</h1>
                                </div>
                            </div>
                            <div class="card-body">
                                <table id="" class="table table-striped">
                                    <thead class="thead-dark">
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col">Fecha</th>
                                            <th scope="col">Usuario tienda</th>
                                            <th scope="col">Código tienda</th>
                                            <th scope="col">Estado</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${manejoDatos.incidencias}" var="u">
                                            <tr>
                                                <td>${u.id}</td>
                                                <td>${u.fecha}</td>
                                                <td>${u.usuarioTienda}</td>
                                                <td>${u.codigoTienda}</td>
                                                <td>${u.estado}</td>
                                            </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- devoluciones -->
                    <div class="tab-pane fade" id="nav-devoluciones" role="tabpanel" aria-labelledby="nav-devoluciones-tab">
                        <div class="container">
                            <div class="card-header">
                                <div class="text-center">
                                    <h1>Devoluciones a ingresar</h1>
                                </div>
                            </div>
                            <div class="card-body">
                                <table id="" class="table table-striped">
                                    <thead class="thead-dark">
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col">Fecha</th>
                                            <th scope="col">Total</th>
                                            <th scope="col">Usuario tienda</th>
                                            <th scope="col">Código tienda</th>
                                            <th scope="col">Estado</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${manejoDatos.devolucions}" var="u">
                                            <tr>
                                                <td>${u.id}</td>
                                                <td>${u.fecha}</td>
                                                <td><fmt:formatNumber value="${u.total}" type="currency"></fmt:formatNumber></td>
                                                <td>${u.usuarioTienda}</td>
                                                <td>${u.codigoTienda}</td>
                                                <td>${u.estado}</td>
                                            </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- lista de errores -->
                    <div class="tab-pane fade" id="nav-errores" role="tabpanel" aria-labelledby="nav-errores-tab">
                        <div class="container">
                            <div class="card-header">
                                <div class="text-center">
                                    <h1>Errores en la entrada de datos</h1>
                                </div>
                            </div>
                            <div class="card-body">
                                <table id="" class="table table-striped">
                                    <thead class="thead-dark">
                                        <tr>
                                            <th scope="col">Tipo</th>
                                            <th scope="col">Informacion</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${manejoDatos.errores}" var="u">
                                            <tr>
                                                <td>${u.tipo}</td>
                                                <td>${u.informacion}</td>
                                            </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>      
        </div>
        <!-- boostrap-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
        <!-- dataTables-->
        <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
        <script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.11.3/datatables.min.js"></script>
        <!-- dataTables arhivo-local -->
        <script src="resources/js/dataTables.js" type="text/javascript"></script>
    </body>
</html>
