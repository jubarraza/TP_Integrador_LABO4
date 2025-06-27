<%@page import="java.util.List" %>
<%@page import="entidad.TipoDeCuenta" %>

<%@ include file="fragmentos/VerificarSesion.jspf"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>ABM de Cuentas</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css">
  <style>
    html, body {
      height: 100%;
    }
    body {
      display: flex;
      flex-direction: column;
      min-height: 100vh;
      background-image: url('assets/bg2.jpg');
      background-size: cover;
      background-position: center;
      background-repeat: no-repeat;
    }
    main {
      flex: 1;
    }
    .form-container {
      max-width: 700px;
      margin: 0 auto;
      background-color: #ffffffcc;
      border-radius: 12px;
      padding: 30px;
      box-shadow: 0 0 15px rgba(0,0,0,0.1);
    }
    .readonly {
      background-color: #e9ecef;
    }
  </style>
</head>
<body>
<jsp:include page="Nav.jsp" />
  <main class="container py-5">
    <h2 class="text-center mb-4">Nueva Cuenta</h2>

    <div class="form-container">
      <form method="post" action="InsertCuentasServlet">
        <!-- DNI del cliente con botón validar -->
        <div class="mb-3">
          <label for="dni" class="form-label">DNI del Cliente</label>
          <input type="text" class="form-control" name="txtDni" placeholder="Ingrese DNI" required>
        </div>

        <!-- Tipo de cuenta -->
        <div class="mb-3">
          <label for="tipoCuenta" class="form-label">Tipo de Cuenta</label>
          <select name="tipoCuenta" class="form-select">
             <%
		        List<TipoDeCuenta> tipos = (List<TipoDeCuenta>) request.getAttribute("tipoCuenta");
		        if (tipos != null) {
		            for (TipoDeCuenta tipo : tipos) {
		    %>
		                <option value="<%= tipo.getIdTipoCuenta() %>">
		                	<%= tipo.getDescripcion() %>
		                </option>
		    <%
		            }
		        } else {
		    %>
		        <option value="">No hay tipos disponibles</option>
		    <%
		        }
		    %>
          </select>
        </div>

        <!-- Número de cuenta -->
        <div class="mb-3">
          <label for="numeroCuenta" class="form-label">Número de Cuenta</label>
          <input type="text" class="form-control readonly" name="txtNumeroCuenta" required>
        </div>

        <!-- CBU -->
        <div class="mb-3">
          <label for="cbu" class="form-label">CBU</label>
          <input type="text" class="form-control readonly" name="txtCbu" required>
        </div>

        <!-- Saldo -->
        <div class="mb-3">
          <label for="saldo" class="form-label">Saldo</label>
          <input type="text" class="form-control readonly" id="saldo" value="$10.000" readonly>
        </div>

        <!-- Estado -->
        <div class="mb-3">
          <label for="estado" class="form-label">Estado</label>
          <input type="text" class="form-control readonly" id="estado" value="Activa" readonly>
        </div>

        <!-- Botones de acción -->
        <div class="d-flex justify-content-end gap-3 mt-4">
          <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalGuardar">
            <i class="fas fa-save me-1"></i> Registar
          </button>
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
            <i class="fas fa-trash-alt me-1"></i> Cancelar
          </button>
        </div>
        
          <!-- Modal Confirmación de Guardado -->
		  <div class="modal fade" id="modalGuardar" tabindex="-1" aria-labelledby="modalGuardarLabel" aria-hidden="true">
		    <div class="modal-dialog modal-dialog-centered">
		      <div class="modal-content">
		        <div class="modal-header">
		          <h5 class="modal-title" id="modalGuardarLabel">Confirmar registro</h5>
		          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
		        </div>
		        <div class="modal-body">
		          ¿Deseás regsitar esta cuenta?
		        </div>
		        <div class="modal-footer">
		          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
		          <button type="submit" class="btn btn-primary" name="btnAceptar" value="Aceptar">Registrar</button>
		        </div>
		      </div>
		    </div>
		  </div>
        
        
      </form>
    </div>
  </main>
	
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
<!-- <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"></script> -->
</body>
</html>
