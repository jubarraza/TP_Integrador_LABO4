<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
					<tr>
						<td>01/01/2024</td>
						<td>$ 10.000,00</td>
						<td>$ 1.000,00</td>
						<td>12</td>
						<td>3</td>
						<td><span class="badge bg-success">Activo</span></td>
						<td>
							<button type="submit" class="btn btn-success btn-sm btn-action"
								name="btnPagarCuota">
								<i class="fas fa-money-bill-wave"></i> Pagar Cuota
							</button>
						</td>
					</tr>
					<tr>
						<td>15/02/2024</td>
						<td>$ 5.000,00</td>
						<td>$ 208,33</td>
						<td>24</td>
						<td>24</td>
						<td><span class="badge bg-warning text-dark">Pendiente
								de aprobación</span></td>
						<td>
							<button class="btn btn-secondary btn-sm btn-action" disabled>
								<i class="fas fa-money-bill-wave"></i> Pagar Cuota
							</button>
						</td>
					</tr>
					<tr>
						<td>20/03/2024</td>
						<td>$ 20.000,00</td>
						<td>$ 555,56</td>
						<td>36</td>
						<td>0</td>
						<td><span class="badge bg-info">Finalizado</span></td>
						<td>
							<button class="btn btn-secondary btn-sm btn-action" disabled>
								<i class="fas fa-money-bill-wave"></i> Pagar Cuota
							</button>
						</td>
					</tr>
					<tr>
						<td>05/04/2024</td>
						<td>$ 7.500,00</td>
						<td>$ 625,00</td>
						<td>12</td>
						<td>8</td>
						<td><span class="badge bg-success">Activo</span></td>
						<td><a class="btn btn-success btn-sm btn-action"> <i
								class="fas fa-money-bill-wave"></i> Pagar Cuota
						</a></td>
					</tr>
					<tr>
						<td>25/05/2025</td>
						<td>$ 3.000,00</td>
						<td>$ 0,00</td>
						<td>0</td>
						<td>0</td>
						<td><span class="badge bg-danger">Rechazado</span></td>
						<td>
							<button class="btn btn-secondary btn-sm btn-action" disabled>
								<i class="fas fa-money-bill-wave"></i> Pagar Cuota
							</button>
						</td>
					</tr>
					<tr>
						<td>01/06/2025</td>
						<td>$ 15.000,00</td>
						<td>$ 416,67</td>
						<td>36</td>
						<td>5</td>
						<td><span class="badge bg-success">Activo</span></td>
						<td>
							<button class="btn btn-success btn-sm btn-action">
								<i class="fas fa-money-bill-wave"></i> Pagar Cuota
							</button>
						</td>
					</tr>
					<tr>
						<td>22/06/2024</td>
						<td>$ 6.000,00</td>
						<td>$ 166,67</td>
						<td>36</td>
						<td>0</td>
						<td><span class="badge bg-info">Finalizado</span></td>
						<td>
							<button class="btn btn-secondary btn-sm btn-action" disabled>
								<i class="fas fa-money-bill-wave"></i> Pagar Cuota
							</button>
						</td>
					</tr>
					<tr>
						<td>05/07/2024</td>
						<td>$ 4.000,00</td>
						<td>$ 333,33</td>
						<td>12</td>
						<td>4</td>
						<td><span class="badge bg-success">Activo</span></td>
						<td><a class="btn btn-success btn-sm btn-action"> <i
								class="fas fa-money-bill-wave"></i> Pagar Cuota
						</a></td>
					</tr>
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


</body>
</html>
