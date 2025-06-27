<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ include file="fragmentos/VerificarSesion.jspf"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pago de cuota</title>
<!-- Boostrap -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet"
 integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT" crossorigin="anonymous">
 <!-- Bootstrap Iconos -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css"
	rel="stylesheet">
<!-- Favicon  -->
<link rel="icon" href="assets/bank.png" type="image/png" />
 
 <style>
 body {
	background-color: #f8f9fa;
	display: flex;
	flex-direction: column;
	min-height: 100vh;
	background-image: url('assets/client-bg.jpg');
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

<div class="container mt-4">
  <div class="row justify-content-center">
    <div class="col-md-6">
      <div class="card shadow-sm border rounded p-4">
       
       <!-- Titulo -->
       <h3 class="fw-bold text-primary"><i class="bi bi-cash-coin"></i> Pago de cuota</h3>       
       <p class="text-muted mb-4">Revise los datos y seleccione una cuenta para realizar el pago.</p>
       
		<table class="table table-bordered text-center">
		  <thead class="table-primary">
		    <tr>
		      <th scope="col">Fecha pago</th>
		      <th scope="col">Valor Cuota</th>
		      <th scope="col">Nro. Cuota</th>
		    </tr>
		  </thead>
		  <tbody>		    
		    <tr>
		      <td>10/03/2024</td>
		      <td class="text-success fw-semibold">$ 1.000,00</td>
		      <td>2</td>
		    </tr>
		  </tbody>
		</table>
		
		<br>
		
		<form class="row g-2">
		<div class="input-group">
            <select class="form-select" id="selectCuenta">
              <option selected disabled></option>
              <option value="1">Caja de ahorro</option>
              <option value="2">Cuenta sueldo</option>
              <option value="2">Cuenta corriente</option>
            </select>
            <button class="btn btn-primary" type="button">
			  <i class="bi bi-check2-circle me-1"></i> Seleccionar
			</button>
            <button class="btn btn-secondary" type="button">
			  <i class="bi bi-x-circle me-1"></i> Limpiar
			</button>
          </div>
			 
		<br>
			 
		<div class="d-grid gap-2 col-6 mx-auto">
		<!-- Button trigger modal -->
		<button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
		 Confirmar pago<i class="bi bi-check2"></i>
		</button>
		</div>
	
		<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered">
		    <div class="modal-content">
		      <div class="modal-header">
		      	<h4 class="modal-title fw-bold text-primary mt-3">Confirmación de pago <i class="bi bi-check2-all"></i></h4>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" onclick="window.location.href='CuotasPendientes.jsp'"></button>
		      </div>
		      <div class="modal-body">
		       <i class="bi bi-stars"></i> Su pago fue realizado exitosamente! <i class="bi bi-stars"></i>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" onclick="window.location.href='CuotasPendientes.jsp'">
		        Cerrar</button>
		      </div>
		    </div>
		  </div>
		</div> 		
		
		</form>
		
		</div>
      </div>
    </div>
  </div>
 <jsp:include page="Footer.html" />
</body>
</html>