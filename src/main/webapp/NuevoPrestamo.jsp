<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<%@ include file="fragmentos/VerificarSesion.jspf"%>
	<%@page import="java.util.List" %>
	<%@page import="entidad.Cuenta" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Nuevo Préstamo</title>
  <link rel="icon" href="assets/bank.png" type="image/png" />
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
  <link rel="stylesheet" href="Styles.css">
  <style>
    html, body {
      height: 100%;
    }
    body {
      display: flex;
      flex-direction: column;
      min-height: 100vh;
      background-image: url('assets/bg1.jpg');
      background-size: cover;
      background-position: center;
      background-repeat: no-repeat;
    }
    main {
      flex: 1;
    }
    .form-container {
      max-width: 600px;
      margin: 0 auto;
    }
    .simulacion-card {
      background-color: #f8f9fa;
      border: 1px solid #dee2e6;
      border-radius: 10px;
      padding: 20px;
      margin-top: 30px;
      text-align: center;
    }
  </style>
</head>
<body>

  <jsp:include page="Nav.jsp" />

  <main class="container mt-5">
    <h2 class="mb-4 text-center">Nuevo Préstamo</h2>

    <div class="form-container">
      <div class="card border-primary shadow-sm p-4">
        <form action="GetionPrestamoServelet" method="post">

          <div class="mb-3">
            <label for="monto" class="form-label">Monto deseado</label>
            <input type="number" class="form-control" name="txtMonto" placeholder="$">
          </div>

          <div class="mb-3">
            <label for="cuotas" class="form-label">Cantidad de cuotas</label>
            <select class="form-select" name="cantidadCuotas">
              <option selected disabled>Seleccionar</option>
              <option>6</option>
              <option>12</option>
              <option>24</option>
              <option>36</option>
              <option>48</option>
            </select>
          </div>

          <div class="mb-3">
            <label for="cuentaDestino" class="form-label">Cuenta para recibir el préstamo</label>
            <select class="form-select" name="numCuenta">
              <option selected disabled>Seleccionar</option>
             
             <%
            List<Cuenta> cuentas = (List<Cuenta>) request.getAttribute("cuentasTotal");
            if (cuentas != null && !cuentas.isEmpty()) {
                for (Cuenta cuenta : cuentas) {
	        %>
	                    <option value="<%= cuenta.getNumDeCuenta() %>">
	                        <%= cuenta.getTipoCuenta().getDescripcion() %> - <%= cuenta.getNumDeCuenta() %>
	                    </option>
	        <%
	                }
	            } else {
	        %>
	                <option disabled>No hay cuentas disponibles</option>
	        <%
	            }
	        %>
  
             
            </select>
          </div>

          <div class="d-grid mb-3">
            <button type="submit" class="btn btn-primary">
              <i class="fas fa-calculator me-1"></i> Simular
            </button>
          </div>

        </form>

        <div class="simulacion-card">
          <h5 class="mb-4">Resultado de la simulación</h5>

          <div class="text-start">
            <p><strong>Tasa de interés:</strong> ${tna}%</p>
            <p><strong>Monto total a devolver:</strong> $${montoTotal}</p>
            <p><strong>Cuota mensual estimada:</strong> $${cuotaMensual}</p>
            <p><strong>Primer vencimiento (cuota 1):</strong> ${primerVencimiento}</p>
          </div>

          <div class="text-center mt-4">
            <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#confirmarPrestamoModal">
              <i class="fas fa-check-circle me-1"></i> Pedir préstamo
            </button>
          </div>
        </div>
      </div>
    </div>
  </main>

  <div class="modal fade" id="confirmarPrestamoModal" tabindex="-1" aria-labelledby="confirmarPrestamoLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
       <form action="InsertarPrestamoServlet" method="post">
      <div class="modal-content border-success">
        <div class="modal-header">
          <h5 class="modal-title" id="confirmarPrestamoLabel">
            <i class="fas fa-check-circle text-success me-2"></i>Confirmar solicitud de préstamo
          </h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
        </div>
        <div class="modal-body">
          <p class="mb-2">Por favor revisá la información antes de confirmar:</p>
          <ul class="list-group list-group-flush small">
            <li class="list-group-item"><strong>Monto solicitado:</strong> $${monto}</li>
            <li class="list-group-item"><strong>Tasa de interés:</strong> ${tna}%</li>
            <li class="list-group-item"><strong>Monto total a devolver:</strong> $${montoTotal}</li>
            <li class="list-group-item"><strong>Cuota mensual estimada:</strong> $${cuotaMensual}</li>
            <li class="list-group-item"><strong>Primer vencimiento:</strong> ${primerVencimiento}</li>
          </ul>
          
          <!-- Campos para realizar el insert -->
          <input type="hidden" name="numCuenta" value="${param.numCuenta}" />
          <input type="hidden" name="monto" value="${monto}" />
          <input type="hidden" name="cuotas" value="${param.cantidadCuotas}" />
          <input type="hidden" name="cuotaMensual" value="${cuotaMensual}" />
          
        </div>

        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
          <button type="submit" class="btn btn-success">Confirmar</button>
        </div>
      </div>
    </form>
    </div>
  </div>
	
	<%
	    String mensajeExito = (String) request.getAttribute("mensajeExito");
	    String mensajeError = (String) request.getAttribute("mensajeError");
    	if (mensajeExito != null) {
	%>
    	<%= mensajeExito %>
	<%
    	} else if (mensajeError != null) {
	%>
    	<%= mensajeError %>
	<%
    	}
	%>
	
  <jsp:include page="Footer.html" />
</body>
</html>
