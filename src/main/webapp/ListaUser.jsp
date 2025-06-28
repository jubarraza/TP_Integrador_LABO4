<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="entidad.Cliente"%>
<%@ include file="fragmentos/VerificarSesion.jspf"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Administración de Clientes</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<link rel="stylesheet"
	href="https://cdn.datatables.net/2.3.2/css/dataTables.bootstrap5.min.css" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/2.3.2/js/dataTables.js"></script>
<script
	src="https://cdn.datatables.net/2.3.2/js/dataTables.bootstrap5.min.js"></script>
<style>
html, body {
	height: 100%;
	margin: 0;
	display: flex;
	flex-direction: column;
}

body {
	background-color: #f8f9fa;
	background-image: url('assets/bg1.jpg');
	background-size: cover;
	background-position: center;
	background-repeat: no-repeat;
	flex: 1;
	overflow-x: hidden;
}

main.container-fluid {
	background-color: #ffffff;
	padding: 30px;
	border-radius: 8px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	margin-top: 30px;
	margin-bottom: 30px;
	flex-grow: 1;
	max-width: 95%;
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
</style>
</head>
<body>

	<jsp:include page="Nav.jsp" />
	<main class="container-fluid">
		<div class="d-flex justify-content-between align-items-center mb-4">
			<h2 class="fw-bold">
				<i class="bi bi-person-lines-fill"></i> Administración de Clientes
			</h2>
			<a href="ABMUsuarios.jsp" class="btn btn-primary btn-lg"> <i
				class="fas fa-plus-circle"></i> Agregar Usuario
			</a>
		</div>

		<form method="get" action="listarClientes">
			<input type="submit" value="Mostrar Clientes"
				class="btn btn-info mb-3">
		</form>

		<%
		List<Cliente> listaUsuarios = (List<Cliente>) request.getAttribute("listaUsuarios");
		%>

		<div class="table-responsive">
			<table id="clientesTable"
				class="table table-striped table-hover w-100">
				<thead class="table-dark">
					<tr>
						<th>ID</th>
						<th>DNI</th>
						<th>CUIL</th>
						<th>ID USUARIO</th>
						<th>NOMBRE</th>
						<th>APELLIDO</th>
						<th>NACIONALIDAD</th>
						<th>FECHA NAC</th>
						<th>DIRECCION</th>
						<th>LOCALIDAD</th>
						<th>TELEFONO</th>
						<th>FECHA ALTA</th>
						<th>ESTADO</th>
						<th>Editar</th>
						<th>Eliminar</th>
					</tr>
				</thead>
				<tbody>
					<%
					if (listaUsuarios != null) {
						for (Cliente c : listaUsuarios) {
							if (c == null)
						continue;
					%>
					<tr>
						<td><%=c.getIdCliente()%></td>
						<td><%=c.getDni()%></td>
						<td><%=c.getCuil()%></td>
						<td><%=c.getUser() != null ? c.getUser().getIdUsuario() : "Sin usuario"%></td>
						<td><%=c.getNombre()%></td>
						<td><%=c.getApellido()%></td>
						<td><%=c.getNacionalidad()%></td>
						<td><%=c.getFechaNacimiento()%></td>
						<td><%=c.getDireccion()%></td>
						<td><%=c.getLocalidad()%></td>
						<td><%=c.getTelefono()%></td>
						<td><%=c.getFechaAlta()%></td>
						<td><%=c.Estado() ? "<span class='badge bg-success'>Activo</span>" : "<span class='badge bg-danger'>Inactivo</span>"%>
						</td>

						<td>
							<%
							if (c.getUser() != null) {
							%> <a
							href="perfil?action=edit&idUsuario=<%=c.getUser().getIdUsuario()%>"
							class="btn btn-outline-primary btn-sm btn-action"> <i
								class="bi bi-pen"></i>
						</a> <%
 }
 %>
						</td>
						<td>
							<%
							if (c.getUser() != null) {
							%>
							<form action="<%=request.getContextPath()%>/admin/usuarios"
								method="post"
								onsubmit="return confirm('¿Estás seguro que deseas eliminar este usuario?');">
								<input type="hidden" name="action" value="delete" /> <input
									type="hidden" name="idUsuario"
									value="<%=c.getUser().getIdUsuario()%>" />
								<button type="submit"
									class="btn btn-outline-danger btn-sm btn-action">
									<i class="bi bi-trash"></i> Eliminar
								</button>
							</form> <%
 }
 %>
						</td>
						<%
						}
						}
						%>
					
				</tbody>
			</table>
		</div>
	</main>

	<jsp:include page="Footer.html" />
	<script>
		$(document)
				.ready(
						function() {
							$('#clientesTable')
									.DataTable(
											{
												"paging" : true,
												"pageLength" : 10,
												"lengthChange" : false,
												"searching" : true,
												"ordering" : true,
												"info" : true,
												"autoWidth" : false,
												"scrollX" : true,
												"language" : {
													"url" : "https://cdn.datatables.net/plug-ins/1.13.1/i18n/es-ES.json"
												}
											});
						});
	</script>
</body>
</html>
