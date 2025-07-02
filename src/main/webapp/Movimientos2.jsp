<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="fragmentos/VerificarSesion.jspf"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Movimientos</title>
<!-- Bootstrap -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet"
 integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT" crossorigin="anonymous">
<!-- Bootstrap Iconos -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
<!-- Hoja de estilos -->
<link rel="StyleSheet" href="Styles.css" type="text/css" /> 
<!-- Favicon -->
<link rel="icon" href="assets/bank.png" type="image/png" />
<style>
  body {
	display: flex;
	flex-direction: column;
	min-height: 100vh;
	background-image: url('assets/bg1.jpg');
	background-size: cover;
	background-position: center;
	background-repeat: no-repeat;
  }
  .container {
	flex: 1;
  }
</style>
</head>
<body>
<jsp:include page="Nav.jsp"/>

<form action="MovimientoServlet" method="post">
 <div class="container mt-3">
  <div class="card shadow-sm p-4">
    <!-- Título -->        
	<h3 class="fw-bold text-primary"><i class="bi bi-person-lines-fill "></i> Mis cuentas</h3>
	
	<!-- Tabla de cuentas -->
	<table class="table table-bordered text-center">
	  <thead class="table-primary">
	    <tr>
	      <th>Saldo Total</th>
	      <th>Cuenta sueldo</th>
	      <th>Caja de ahorro</th>
	      <th>Cuenta corriente</th>
	      <th>Saldo disponible</th>
	    </tr>
	  </thead>
	  <tbody>
	    <tr>
	      <td>$ 16.000,00</td>
	      <td>$ 6.000,00</td>
	      <td>$ 10.000,00</td>
	      <td>-</td>
	      <td>$ 6.000,00</td>
	    </tr>
	  </tbody>
	</table>
	
	<!-- Subtítulo -->
	<h4 class="fw-bold text-primary mt-2"><i class="bi bi-search"></i> Consulta</h4>
	
	<div class="row align-items-end mb-3 g-2">
	  <div class="col-md-4">
	    <!-- Select de cuentas -->
	    <select class="form-select" name="cuenta">
	      <option selected disabled>Seleccione una cuenta</option>
		  <option value="sueldo">Cuenta sueldo</option>
		  <option value="ahorro">Caja de ahorro</option>
		  <option value="corriente">Cuenta corriente</option>
		</select>
	  </div>
	  
	  <div class="col-md-4">
	    <!-- Select de operación -->
        <select class="form-select" name="operacion">
          <option selected disabled>Seleccione una operación</option>
		  <option value="movimientos">Movimientos</option>
		  <option value="transferencias">Transferencias</option>
		  <option value="prestamos">Préstamos</option>
		</select>
	  </div>
	  
	  <!-- Botones -->
	  <div class="col-md-4 d-flex gap-1">
	  	<button class="btn btn-primary" type="submit">
		  <i class="bi bi-check2-circle me-1"></i>Aplicar
		</button>
	  	<button class="btn btn-secondary" type="reset">
		  <i class="bi bi-x-circle me-1"></i>Limpiar
		</button>
		<button class="btn btn-info" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasRight" aria-controls="offcanvasRight">
		  <i class="bi bi-info-circle"></i> Información
		</button>		
	  </div>
	</div>
 </div>
 
 <% if (request.getAttribute("cuentaSeleccionada") != null) { %>
    <div class="alert alert-success mt-3">
        <strong>Cuenta:</strong> <%= request.getAttribute("cuentaSeleccionada") %><br>
        <strong>Operación:</strong> <%= request.getAttribute("operacionSeleccionada") %>
    </div>
<% } %>
 
</div>
</form>

<!-- Offcanvas de información -->
<div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasRight" aria-labelledby="offcanvasRightLabel">
  <div class="offcanvas-header">
    <h5 class="offcanvas-title" id="offcanvasRightLabel">Información de la cuenta</h5>
    <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
  </div>
  <div class="offcanvas-body">
    <p><strong>CBU:</strong> 0000500000000000000009</p>
    <p><strong>N° Cuenta:</strong> 001-1234567-8</p>
    <p><strong>Alias:</strong> Grupo.11</p>
    <h5 class="mt-3">Acceso rápido</h5>
    <!-- Aquí podés agregar accesos rápidos adicionales si querés -->
  </div>
</div>

<!-- Scripts de Bootstrap -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"
 integrity="sha384-2Q6x0VKoAayqGuIEbD9UEUtnzRb+k9aZB3yDjKZ2r6HGQ/HPBg3EM6JZP9YLUoOj" crossorigin="anonymous"></script>
</body>
</html>
