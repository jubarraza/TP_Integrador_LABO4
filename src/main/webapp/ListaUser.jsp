<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="entidad.Cliente" %>

<%@ include file="fragmentos/VerificarSesion.jspf"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lista Clientes</title>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="https://cdn.datatables.net/2.3.2/css/dataTables.dataTables.css" />
<script src="https://cdn.datatables.net/2.3.2/js/dataTables.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$('#table_id').DataTable();
	});
</script>
</head>
<body>

	<jsp:include page="Nav.jsp"/>

	<div class="container mb-4 mt-4">
		<section class="row justify-content-center">
			
			<article class="col-12 text-center mb-4">
				<h1 class="fw text-primary">Lista de Clientes</h1>
			</article>
			<article class="mb-3">
                <a href="ABMUsuarios.jsp" class="btn btn-primary">
                    <i class="bi bi-person-fill-add"></i> Agregar Usuario
                </a>
            </article>

			<form method="get" action="listarClientes">
				<input type="submit" value="Mostrar Clientes" class="btn btn-primary mb-3">
			</form>

			<%
				List<Cliente> listaUsuarios = null;
				if(request.getAttribute("listaUsuarios")!=null){
					listaUsuarios = (List<Cliente>)request.getAttribute("listaUsuarios");
				}
			%>

			<article class="col-12">
				<table id="table_id" class="display table table-striped">
					<thead>
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
						</tr>
					</thead>
					<tbody>
					<%
					if (listaUsuarios != null) {
					    for (Cliente c : listaUsuarios) {
					        if (c == null) continue;
					%>
					    <tr>
					        <td><%= c.getIdCliente() %></td>
					        <td><%= c.getDni() %></td>
					        <td><%= c.getCuil() %></td>
					        <td><%= c.getUser() != null ? c.getUser().getIdUsuario() : "Sin usuario" %></td>
					        <td><%= c.getNombre() %></td>
					        <td><%= c.getApellido() %></td>
					        <td><%= c.getNacionalidad()  %></td>
					        <td><%= c.getFechaNacimiento() %></td>
					        <td><%= c.getDireccion() %></td>
					        <td><%= c.getLocalidad() %></td> 
					        <td><%= c.getTelefono() %></td>
					        <td><%= c.getFechaAlta() %></td>
					        <td><%= c.Estado() ? "Activo" : "Inactivo" %></td>
					    </tr>
					<%
					    }
					}
					%>


					</tbody>
				</table>
			</article>

		</section>
	</div>

	<jsp:include page="Footer.html" />
</body>
</html>

