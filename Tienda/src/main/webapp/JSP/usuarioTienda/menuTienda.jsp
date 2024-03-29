<%-- 
    Document   : menuTienda
    Created on : 28/02/2023, 02:20:21
    Author     : luis
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
    <div class="container-fluid mx-3">
        <a class="navbar-brand" href="#"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-shop" viewBox="0 0 16 16">
                <path d="M2.97 1.35A1 1 0 0 1 3.73 1h8.54a1 1 0 0 1 .76.35l2.609 3.044A1.5 1.5 0 0 1 16 5.37v.255a2.375 2.375 0 0 1-4.25 1.458A2.371 2.371 0 0 1 9.875 8 2.37 2.37 0 0 1 8 7.083 2.37 2.37 0 0 1 6.125 8a2.37 2.37 0 0 1-1.875-.917A2.375 2.375 0 0 1 0 5.625V5.37a1.5 1.5 0 0 1 .361-.976l2.61-3.045zm1.78 4.275a1.375 1.375 0 0 0 2.75 0 .5.5 0 0 1 1 0 1.375 1.375 0 0 0 2.75 0 .5.5 0 0 1 1 0 1.375 1.375 0 1 0 2.75 0V5.37a.5.5 0 0 0-.12-.325L12.27 2H3.73L1.12 5.045A.5.5 0 0 0 1 5.37v.255a1.375 1.375 0 0 0 2.75 0 .5.5 0 0 1 1 0zM1.5 8.5A.5.5 0 0 1 2 9v6h1v-5a1 1 0 0 1 1-1h3a1 1 0 0 1 1 1v5h6V9a.5.5 0 0 1 1 0v6h.5a.5.5 0 0 1 0 1H.5a.5.5 0 0 1 0-1H1V9a.5.5 0 0 1 .5-.5zM4 15h3v-5H4v5zm5-5a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v3a1 1 0 0 1-1 1h-2a1 1 0 0 1-1-1v-3zm3 0h-2v3h2v-3z"/>
            </svg> Tienda: ${tienda.codigo}</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
            <ul class="navbar-nav me-auto mb-2 mb-md-0">
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="crearPedido.jsp">Crear pedido</a>
                </li>
                <c:if test="${tienda.tipo=='supervisada' || tienda.tipo == 'SUPERVISADA'}">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page"
                           href="${pageContext.request.contextPath}/ControlPedidoTienda?tarea=listarPedidosRechazados">Pedidos rechazados</a>
                    </li>
                </c:if>
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" 
                       href="${pageContext.request.contextPath}/ControlEnviosTienda?tarea=verTodos">Env�os</a>
                </li>
                <div class="dropdown mx-3 mt-2">
                    <a href="#"class="d-flex align-items-center text-white text-decoration-none dropdown-toggle" 
                       id="dropdownUser2" data-bs-toggle="dropdown" aria-expanded="false">
                        Incidencias
                    </a>
                    <ul class="dropdown-menu dropdown-menu-dark text-small shadow" aria-labelledby="dropdownUser2">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/ControlIncidenciasTienda?tarea=listarEnviosRecibidos">Crear incidencias</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/ControlIncidenciasTienda?tarea=incidenciasSolucionas">Incidencias solucionadas</a></li>
                    </ul>
                </div>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/ControlDevolciones?tarea=listarEnviosRecibidos">Crear devoluci�n</a>
                </li>
                <div class="dropdown mx-3 mt-2">
                    <a href="#" class="d-flex align-items-center text-white text-decoration-none dropdown-toggle" id="dropdownUser3" data-bs-toggle="dropdown" aria-expanded="false">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-card-checklist" viewBox="0 0 16 16">
                            <path d="M14.5 3a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-.5.5h-13a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h13zm-13-1A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h13a1.5 1.5 0 0 0 1.5-1.5v-9A1.5 1.5 0 0 0 14.5 2h-13z"/>
                            <path d="M7 5.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5zm-1.496-.854a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 1 1 .708-.708l.146.147 1.146-1.147a.5.5 0 0 1 .708 0zM7 9.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5zm-1.496-.854a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 0 1 .708-.708l.146.147 1.146-1.147a.5.5 0 0 1 .708 0z"/>
                        </svg>
                        Reportes
                    </a>
                    <ul class="dropdown-menu dropdown-menu-dark text-small shadow" aria-labelledby="dropdownUser3">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/ControlReporteTienda?tarea=reporteProductos">Reporte de productos</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/ControlReporteTienda?tarea=reportePedidos">Reporte de pedidos realizados</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/ControlReporteIncidencias?tarea=reporteIncidencias">Reporte  de incidencias generadas</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/ControlReporteDevoluciones?tarea=reporteDevoluciones">Reporte  de devoluciones generadas</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/ControlReporteEnvios?tarea=enviosRecibidos">Reporte de env�os recibidos</a></li>
                    </ul>
                </div>
            </ul>
            <form class="d-flex">
                <div class="dropdown mx-5">
                    <a href="#" class="d-flex align-items-center text-white text-decoration-none dropdown-toggle" id="dropdownUser4" data-bs-toggle="dropdown" aria-expanded="false">
                        <img src="../../resources/img/user.png" alt="" width="32" height="32" class="rounded-circle me-2">
                            <strong>${user.nombreUsuario}</strong>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-dark text-small shadow" aria-labelledby="dropdownUser4">
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/Close">Salir</a></li>
                    </ul>
                </div>
            </form>
        </div>
    </div>
</nav>