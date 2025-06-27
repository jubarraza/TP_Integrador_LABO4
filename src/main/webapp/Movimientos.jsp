<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="fragmentos/VerificarSesion.jspf"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Movimientos</title>
<!-- Boostrap -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet"
 integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT" crossorigin="anonymous">
 <!-- Bootstrap Iconos -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css"
	rel="stylesheet">
 <!-- Hoja de estilos -->
<link rel="StyleSheet" href="Styles.css" type="text/css" /> 
<!-- Favicon  -->
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

 <div class="container mt-3">
  <div class="card shadow-sm p-4">
    <!-- Titulo -->        
	 <h3 class="fw-bold text-primary"><i class="bi bi-person-lines-fill "></i> Mis cuentas</h3>
	
		<!-- Cuentas -->
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
	
	<!-- Subtitulo -->
	<h4 class="fw-bold text-primary mt-2"><i class="bi bi-search"></i> Consulta</h4>
	
		<div class="row align-items-end mb-3 g-2">
	      <div class="col-md-4">
	        <!-- Select de cuentas -->
	        <select class="form-select">
	          <option selected disabled>Seleccione una cuenta</option>
			  <option value="1">Cuenta sueldo</option>
			  <option value="2">Caja de ahorro</option>
			  <option value="2">Cuenta corriente</option>
			</select>
			</div>	  
			 <div class="col-md-4">
			 <!-- Select de operacion -->
		        <select class="form-select">
		          <option selected disabled>Seleccione una operación</option>
				  <option value="1">Movimientos</option>
				  <option value="2">Transferencias</option>
				  <option value="3">Prestamos</option>
				</select>
			</div>
			<!-- Botones aplicar, limpiar, informacion -->
			<div class="col-md-4 d-flex gap-1">
			  	<button class="btn btn-primary" type="button"><i class="bi bi-check2-circle me-1"></i>Aplicar</button>
			  	<button class="btn btn-secondary" type="button"><i class="bi bi-x-circle me-1"></i>Limpiar</button>
				<button class="btn btn-info" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasRight" aria-controls="offcanvasRight">
				 <i class="bi bi-info-circle"></i> Información
				</button>		
			</div>
		 </div>
		 
		<!-- Fuera de lienzo -->
		<div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasRight" aria-labelledby="offcanvasRightLabel">
		  <div class="offcanvas-header">
		    <h5 class="offcanvas-title" id="offcanvasRightLabel">Información de la cuenta</h5>
		    <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
		  </div>
		  <!-- Sector informacion de la cuenta -->
		  <div class="offcanvas-body">
		    Cbu:
			<a class="icon-link icon-link-hover" style="--bs-icon-link-transform: translate3d(0, -.125rem, 0);">
  			0000500000000000000009
			  <svg xmlns="http://www.w3.org/2000/svg" class="bi" viewBox="0 0 16 16" aria-hidden="true">
			    <path d="M4 1.5H3a2 2 0 0 0-2 2V14a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V3.5a2 2 0 0 0-2-2h-1v1h1a1 1 0 0 1 1 1V14a1 1 0 0 1-1 1H3a1 1 0 0 1-1-1V3.5a1 1 0 0 1 1-1h1v-1z"/>
			    <path d="M9.5 1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-3a.5.5 0 0 1-.5-.5v-1a.5.5 0 0 1 .5-.5h3zm-3-1A1.5 1.5 0 0 0 5 1.5v1A1.5 1.5 0 0 0 6.5 4h3A1.5 1.5 0 0 0 11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3z"/>
			  </svg>
			</a>	<br>
			N° Cuenta:
			<a class="icon-link icon-link-hover" style="--bs-icon-link-transform: translate3d(0, -.125rem, 0);">
  			001-1234567-8 
			  <svg xmlns="http://www.w3.org/2000/svg" class="bi" viewBox="0 0 16 16" aria-hidden="true">
			    <path d="M4 1.5H3a2 2 0 0 0-2 2V14a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V3.5a2 2 0 0 0-2-2h-1v1h1a1 1 0 0 1 1 1V14a1 1 0 0 1-1 1H3a1 1 0 0 1-1-1V3.5a1 1 0 0 1 1-1h1v-1z"/>
			    <path d="M9.5 1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-3a.5.5 0 0 1-.5-.5v-1a.5.5 0 0 1 .5-.5h3zm-3-1A1.5 1.5 0 0 0 5 1.5v1A1.5 1.5 0 0 0 6.5 4h3A1.5 1.5 0 0 0 11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3z"/>
			  </svg>
			</a>	<br>
			Alias:
			<a class="icon-link icon-link-hover" style="--bs-icon-link-transform: translate3d(0, -.125rem, 0);">
  			Grupo.11
			  <svg xmlns="http://www.w3.org/2000/svg" class="bi" viewBox="0 0 16 16" aria-hidden="true">
			    <path d="M4 1.5H3a2 2 0 0 0-2 2V14a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V3.5a2 2 0 0 0-2-2h-1v1h1a1 1 0 0 1 1 1V14a1 1 0 0 1-1 1H3a1 1 0 0 1-1-1V3.5a1 1 0 0 1 1-1h1v-1z"/>
			    <path d="M9.5 1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-3a.5.5 0 0 1-.5-.5v-1a.5.5 0 0 1 .5-.5h3zm-3-1A1.5 1.5 0 0 0 5 1.5v1A1.5 1.5 0 0 0 6.5 4h3A1.5 1.5 0 0 0 11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3z"/>
			  </svg>
			</a>	<br>
			<!-- Sector acceso rapido -->
			  <br> <h5 class="offcanvas-title" id="offcanvasRightLabel">Acceso rapido</h5> 
				<a class="icon-link icon-link-hover" style="--bs-link-hover-color-rgb: 25, 135, 84;" href="TransferenciaNueva.jsp">
	 				 Nueva transferencia
				  <svg xmlns="http://www.w3.org/2000/svg" class="bi" viewBox="0 0 16 16" aria-hidden="true">
				    <path d="M1 8a.5.5 0 0 1 .5-.5h11.793l-3.147-3.146a.5.5 0 0 1 .708-.708l4 4a.5.5 0 0 1 0 .708l-4 4a.5.5 0 0 1-.708-.708L13.293 8.5H1.5A.5.5 0 0 1 1 8z"/>
				  </svg>
				</a> <br>
			  <a class="icon-link icon-link-hover" style="--bs-link-hover-color-rgb: 25, 135, 84;" href="CuotasPendientes.jsp">
  			  	Cuotas pendientes
				  <svg xmlns="http://www.w3.org/2000/svg" class="bi" viewBox="0 0 16 16" aria-hidden="true">
				    <path d="M1 8a.5.5 0 0 1 .5-.5h11.793l-3.147-3.146a.5.5 0 0 1 .708-.708l4 4a.5.5 0 0 1 0 .708l-4 4a.5.5 0 0 1-.708-.708L13.293 8.5H1.5A.5.5 0 0 1 1 8z"/>
				  </svg>
				</a> <br>
		  	</div>
		  </div>

		<!-- Subtitulo -->
		<h4 class="fw-bold text-primary mt-3"><i class="bi bi-clipboard-data"></i> Movimientos</h4>			
				  		
		<!-- Tabla de movimientos-->		  		
		<div class="card-cuenta">
		  <table class="table table-hover text-center">
		    <thead class="table-secondary">
	          <tr class="text-center">
	            <th>Fecha</th>
	            <th>Tipo</th>
	            <th>Detalle</th>
	            <th>Monto</th>
	          </tr>
	        </thead>
	        <tbody class="text-center">
	          <tr>
	            <td>17/06/2025</td>
	            <td>Débito automático</td>
	            <td>Banco Novabank</td>
	            <td>$ 2.000,00</td>
	          </tr>
	          <tr>
	            <td>16/06/2025</td>
	            <td>Transferencia</td>
	            <td>Esteban Quito</td>
	            <td>$ 2.000,00</td>
	          </tr>
	        </tbody>
	      </table>	
	
		<!-- Paginación -->
		<div class="d-flex justify-content-between align-items-center mt-3">
	      <nav>
	        <ul class="pagination pagination-sm mb-0">
	          <li class="page-item disabled"><a class="page-link">«</a></li>
	          <li class="page-item active"><a class="page-link">1</a></li>
	          <li class="page-item"><a class="page-link">2</a></li>
	          <li class="page-item"><a class="page-link">3</a></li>
	          <li class="page-item"><a class="page-link">»</a></li>
	        </ul>
	      </nav>
		 <!-- Boton de busqueda -->
	      <div class="input-group" style="max-width: 250px;">
	        <span class="input-group-text">Buscar</span>
	        <input type="text" class="form-control" placeholder="Detalle">
	        <button class="btn btn-outline-secondary">
	          <i class="bi bi-search"></i>
	        </button>
	      </div>
	    </div>
	   </div>	
	    
    </div>
  </div>
  <jsp:include page="Footer.html" />
</body>
</html>