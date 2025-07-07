<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.List, entidad.Cuota, java.text.NumberFormat, java.util.Locale"%>
<%@ include file="fragmentos/VerificarSesion.jspf"%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Cuotas Pendientes</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css"
	rel="stylesheet">
<!--     <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet"> -->

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link
	href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap"
	rel="stylesheet">
<link rel="StyleSheet" href="Styles.css" type="text/css" />
<link rel="icon" href="assets/bank.png" type="image/png" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">


<style>
body {
	background-color: var(--body-color);
	font-family: "Inter", sans-serif;
	color: var(--text-color);
	display: flex;
	flex-direction: column;
	min-height: 100vh;
	background-image: url('assets/bg1.jpg');
	background-size: cover;
	background-position: center;
	background-repeat: no-repeat;
}

.main-content {
	padding-top: 30px;
	padding-bottom: 50px;
	min-height: calc(100vh - 120px);
	/* Ajustar según la altura real de tu nav y footer */
}

.section-header {
	background-color: var(--primary-color);
	color: var(--second-color);
	padding: 15px;
	border-radius: 8px;
	margin-bottom: 30px;
	text-align: center;
}

.table-container {
	background-color: var(--second-color);
	border-radius: 10px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
	padding: 20px;
}

.table th {
	background-color: var(--primary-alpha-color);
	color: var(--second-color);
	vertical-align: middle;
	text-align: center;
}

.table td {
	vertical-align: middle;
	text-align: center;
	color: var(--text-color);
}

.table tbody tr:nth-child(even) {
	background-color: #f8f9fa;
}

.table-responsive {
	margin-top: 20px;
	border-radius: 8px;
	overflow: hidden;
}

.btn-pay {
	background-color: #28a745;
	border-color: #28a745;
	color: white;
	border-radius: 5px;
	transition: background-color 0.3s ease, transform 0.2s ease;
}

.btn-pay:hover {
	background-color: #218838;
	border-color: #1e7e34;
	transform: translateY(-1px);
}

.status-paid {
	color: #28a745;
	font-weight: bold;
}

.status-pending {
	color: #ffc107;
	font-weight: bold;
}

.pagination .page-item.active .page-link {
	background-color: var(--primary-color);
	border-color: var(--primary-color);
	color: var(--second-color);
}

.pagination .page-link {
	color: var(--primary-color);
	border: 1px solid var(--primary-alpha-color);
}

.pagination .page-link:hover {
	color: var(--second-color);
	background-color: var(--primary-alpha-color);
	border-color: var(--primary-color);
}
</style>
</head>
<body>
	<jsp:include page="Nav.jsp" />

	<div class="container main-content">
		<div class="section-header">
			<h1>Cuotas Pendientes de Pago</h1>
		</div>

		<div class="table-container">
			<div class="table-responsive">
				<table class="table table-hover table-striped">
					<thead>
						<tr>
							<th>Nro. Cuota</th>
							<th>Monto</th>
							<th>Préstamo Asociado</th>
							<th>Estado</th>
							<th>Fecha de Pago</th>
							<th>Acciones</th>
						</tr>
					</thead>
					<tbody>
						<%
        List<Cuota> listaCuotas = (List<Cuota>) request.getAttribute("listaCuotas");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));

        if (listaCuotas != null && !listaCuotas.isEmpty()) {
            for (Cuota cuota : listaCuotas) {
    %>
						<tr>
							<td><%= cuota.getNumCuota() %></td>
							<td><%= formatter.format(cuota.getMonto()) %></td>
							<td><%= cuota.getIdPrestamo() %></td>
							<td>
								<% if (cuota.isEstado()) { %> <span class="status-pending">PENDIENTE</span>
								<% } else { %> <span class="status-paid">PAGADO</span> <% } %>
							</td>
							<td><%= (cuota.getFechaPago() != null) ? cuota.getFechaPago() : "---" %></td>
							<td>
								<% if (cuota.isEstado()) { %>
								<a href="PrepararPagoCuotaServlet?idCuota=<%= cuota.getIdPagoDeCuota() %>" class="btn btn-sm btn-pay">Pagar</a> <% } %>
							</td>
						</tr>
						<%
            }
        } else {
    %>
						<tr>
							<td colspan="6" class="text-center">No se encontraron cuotas
								para este préstamo.</td>
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
	</div>

	<jsp:include page="Footer.html" />

	<!--      <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script> 
Se deja comentado porque sino genera conflicto con la declaracion que tiene el nav y no funcionan los dropdowns-->
</body>
</html>
