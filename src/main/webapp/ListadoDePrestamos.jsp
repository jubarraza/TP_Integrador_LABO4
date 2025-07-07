<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List, entidad.Prestamo, java.text.NumberFormat, java.util.Locale" %>
<%@ include file="fragmentos/VerificarSesion.jspf"%>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tus Préstamos</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css">

<style>
body {
	background-color: #f8f9fa;
}

.container {
	background-color: #ffffff;
	padding: 30px;
	border-radius: 8px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	margin-top: 30px;
}

h2 {
	color: #007bff;
	margin-bottom: 20px;
}

.btn-action {
	margin-right: 5px;
}

.table-responsive {
	margin-top: 20px;
}

.request-section {
	background-color: #eaf6ff; /* Un azul claro para destacar */
	padding: 25px;
	border-radius: 8px;
	margin-bottom: 30px;
	text-align: center;
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
}

.request-section h3 {
	color: #0056b3;
	margin-bottom: 15px;
}

.request-section .icon-large {
	font-size: 3rem;
	color: #007bff;
	margin-bottom: 15px;
}

.filtrosprestamos {
	background-color: #e9ecef;
	padding: 15px;
	border-radius: 5px;
	margin-bottom: 20px;
}
</style>
</head>
<body>
	<jsp:include page="Nav.jsp" />

	<!-- Toast para el mensaje de exito/error post creacion -->
	>
	<%
	String toastExito = (String) session.getAttribute("toastExito");
	String toastError = (String) session.getAttribute("toastError");
	session.removeAttribute("toastExito");
	session.removeAttribute("toastError");
	%>

	<div class="position-fixed top-0 end-0 p-3" style="z-index: 1100">
		<div id="toastMensaje"
			class="toast align-items-center text-white 
        <%=(toastExito != null) ? "bg-success" : (toastError != null) ? "bg-danger" : ""%> 
        border-0"
			role="alert" aria-live="assertive" aria-atomic="true"
			style="<%=(toastExito != null || toastError != null) ? "display:block;" : "display:none;"%>">

			<div class="d-flex">
				<div class="toast-body">
					<%=(toastExito != null) ? toastExito : toastError%>
				</div>
				<button type="button" class="btn-close btn-close-white me-2 m-auto"
					data-bs-dismiss="toast" aria-label="Cerrar"></button>
			</div>
		</div>
	</div>

	<div class="container">
		<div class="d-flex justify-content-between align-items-center mb-4">
			<h2>Tus Préstamos</h2>
			<form action="GetionPrestamoServelet" method="get">
				<button type="submit" class="btn btn-primary btn-lg">
					<i class="fas fa-plus-circle"></i> Solicitar Nuevo Préstamo
				</button>
			</form>
		</div>

		<hr class="my-4">

		<div class="filtrosPrestamos">
			<form action="ListarPrestamosServ" method="get">
				<div class="row g-3 align-items-end">
					<div class="col-md-4">
						<label for="filtroEstado" class="form-label">Filtrar por
							Estado:</label> <select class="form-select" id="filtroEstado"
							name="filtroEstado">
							<option value="">Todos los estados</option>
							<option value="Pendiente de aprobación">Pendiente de
								Aprobación</option>
							<option value="Activo">Activo</option>
							<option value="Rechazado">Rechazado</option>
							<option value="Finalizado">Finalizado</option>
						</select>
					</div>
					<div class="col-md-auto">
						<button type="submit" class="btn btn-info" name="btnAplicarFiltro">
							<i class="fas fa-filter"></i> Aplicar Filtro
						</button>
					</div>
					<div class="col-md-auto">
						<button type="button" class="btn btn-secondary"
							name="btnLimpiarFiltro">
							<i class="fas fa-times"></i> Limpiar Filtro
						</button>
					</div>
				</div>
			</form>
		</div>

		<div class="table-responsive">
			<table id="tablaPrestamos" class="table table-striped table-hover">
				<thead>
					<tr>
						<th>Fecha Alta</th>
						<th>Importe Solicitado</th>
						<th>Cuota ($)</th>
						<th>Cuotas Totales</th>
						<th>Cuotas Pendientes</th>
						<th>Estado</th>
						<th>Acciones</th>
					</tr>
				</thead>
				<tbody>
					<%
					List<Prestamo> listaPrestamos = (List<Prestamo>) request.getAttribute("listaPrestamos");
					NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));

					if (listaPrestamos != null && !listaPrestamos.isEmpty()) {
						for (Prestamo p : listaPrestamos) {
							String estadoTexto = "Pendiente";
							String estadoClase = "bg-warning text-dark";
							boolean puedePagar = false;

							if (p.isFinalizado()) {
						estadoTexto = "Finalizado";
						estadoClase = "bg-info";
							} else if (p.isAprobado()) {
						estadoTexto = "Activo";
						estadoClase = "bg-success";
						puedePagar = true;
							} else if (p.isEstado()) {
						estadoTexto = "Rechazado";
						estadoClase = "bg-danger";
							}
					%>
					<tr>
						<td><%=p.getFecha()%></td>
						<td><%=formatter.format(p.getImportePedido())%></td>
						<td><%=formatter.format(p.getImporteMensual())%></td>
						<td><%=p.getCuotas()%></td>
						<td>Falta Lógica</td>
						<td><span class="badge <%=estadoClase%>"><%=estadoTexto%></span></td>
						<td>
							<%
							if (p.isAprobado()) {
							%> <a
							href="DetallePrestamoServlet?idPrestamo=<%=p.getIdPrestamo()%>"
							class="btn btn-info btn-sm btn-action"> <i
								class="fas fa-list-ol"></i> Ver Cuotas
						</a> <%
 } else {
 %>

							<button class="btn btn-secondary btn-sm btn-action" disabled>
								<i class="fas fa-list-ol"></i> Ver Cuotas
							</button> <%
 }
 %>
						</td>
					</tr>
					<%
					}
					} else {
					%>
					<tr>
						<td colspan="7" class="text-center">No tienes préstamos para
							mostrar.</td>
					</tr>
					<%
					}
					%>
				</tbody>
			</table>



		</div>
		<nav aria-label="Navegación de páginas"
			class="pagination-container d-flex justify-content-center">
			<ul class="pagination">
				<li class="page-item disabled"><span class="page-link">&laquo;</span>
				</li>
				<li class="page-item active"><a class="page-link" href="#">1</a></li>
				<li class="page-item"><a class="page-link" href="#">2</a></li>
				<li class="page-item"><a class="page-link" href="#">3</a></li>
				<li class="page-item"><a class="page-link" href="#"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>
		</nav>
	</div>
	<jsp:include page="Footer.html" />
	<!--      <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script> 
Se deja comentado porque sino genera conflicto con la declaracion que tiene el nav y no funcionan los dropdowns-->
	<script>
    window.addEventListener("DOMContentLoaded", () => {
        const toastEl = document.getElementById('toastMensaje');
        if (toastEl) {
            const toast = new bootstrap.Toast(toastEl, { delay: 5000 });
            toast.show();
        }
    });
</script>

</body>
</html>
