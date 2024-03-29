<%-- 
    Document   : menuAdministrador
    Created on : 28/02/2023, 02:19:55
    Author     : luis
--%>
<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
    <div class="container-fluid mx-3">
        <a class="navbar-brand" href="#"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-shop" viewBox="0 0 16 16">
                <path d="M2.97 1.35A1 1 0 0 1 3.73 1h8.54a1 1 0 0 1 .76.35l2.609 3.044A1.5 1.5 0 0 1 16 5.37v.255a2.375 2.375 0 0 1-4.25 1.458A2.371 2.371 0 0 1 9.875 8 2.37 2.37 0 0 1 8 7.083 2.37 2.37 0 0 1 6.125 8a2.37 2.37 0 0 1-1.875-.917A2.375 2.375 0 0 1 0 5.625V5.37a1.5 1.5 0 0 1 .361-.976l2.61-3.045zm1.78 4.275a1.375 1.375 0 0 0 2.75 0 .5.5 0 0 1 1 0 1.375 1.375 0 0 0 2.75 0 .5.5 0 0 1 1 0 1.375 1.375 0 1 0 2.75 0V5.37a.5.5 0 0 0-.12-.325L12.27 2H3.73L1.12 5.045A.5.5 0 0 0 1 5.37v.255a1.375 1.375 0 0 0 2.75 0 .5.5 0 0 1 1 0zM1.5 8.5A.5.5 0 0 1 2 9v6h1v-5a1 1 0 0 1 1-1h3a1 1 0 0 1 1 1v5h6V9a.5.5 0 0 1 1 0v6h.5a.5.5 0 0 1 0 1H.5a.5.5 0 0 1 0-1H1V9a.5.5 0 0 1 .5-.5zM4 15h3v-5H4v5zm5-5a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v3a1 1 0 0 1-1 1h-2a1 1 0 0 1-1-1v-3zm3 0h-2v3h2v-3z"/>
            </svg> Usuario: ${user.nombre}</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
            <ul class="navbar-nav me-auto mb-2 mb-md-0">
                <li class="nav-item">
                    <div class="dropdown mx-3">
                        <a href="#" class="d-flex align-items-center text-white text-decoration-none dropdown-toggle" id="dropdownUser1" data-bs-toggle="dropdown" aria-expanded="false">
                            <strong>Gesti�n de usuarios</strong>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-dark text-small shadow" aria-labelledby="dropdownUser1">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/ControlUsuariosTienda?tarea=usuariosTienda">Usuarios de tienda</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/ControlUsuariosSupervisor?tarea=listar">Usuarios supervisores</a></li>
                            <li><a class="dropdown-item" href="usuariosBodega.jsp">Usuarios de bodega</a></li>
                        </ul>
                    </div>
                </li>
                <li class="nav-item">
                    <div class="dropdown mx-3">
                        <a href="#" class="d-flex align-items-center text-white text-decoration-none dropdown-toggle" id="dropdownUser2" data-bs-toggle="dropdown" aria-expanded="false">
                            <strong>Reportes</strong>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-dark text-small shadow" aria-labelledby="dropdownUser2">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/ControlReportesAdmin?tarea=listarTiendasMasPedidos">Reporte de las 5 tiendas con m�s pedidos</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/ControlReportesAdmin?tarea=listarUsuarioMasEnvios">Reporte de los 5 usuarios con m�s env�os generados</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/ControlReportesAdmin?tarea=listarUsuariosMasPedidos">Reporte de los 5 usuarios con m�s pedidos generados</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/ControlReportesAdmin?tarea=listarProductosMasSolicitados">Reporte de productos m�s solicitados</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/ControlReportesAdmin?tarea=listarProductosDa�ados">Reporte de productos da�ados</a></li>
                        </ul>
                    </div>
                </li>
            </ul>
            <form class="d-flex">
                <div class="dropdown mx-5">
                    <a href="#" class="d-flex align-items-center text-white text-decoration-none dropdown-toggle" id="dropdownUser3" data-bs-toggle="dropdown" aria-expanded="false">
                        <img src="../../resources/img/user.png" alt="" width="32" height="32" class="rounded-circle me-2">
                            <strong>${user.nombreUsuario}</strong>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-dark text-small shadow" aria-labelledby="dropdownUser3">
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/Close">Salir</a></li>
                    </ul>
                </div>
            </form>

        </div>
    </div>
</nav>