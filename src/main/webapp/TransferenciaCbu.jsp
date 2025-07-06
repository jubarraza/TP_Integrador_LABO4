<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="java.util.List, entidad.Cuenta" %>
    <%@ include file="fragmentos/VerificarSesion.jspf"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nueva Transferencia - Novabank</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
 <link rel="stylesheet" href="Style.css"/>
 
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
            background-color: rgba(255, 255, 255, 0.9);
            padding: 2rem;
            border-radius: 12px;
            box-shadow: 0 0 15px rgba(0,0,0,0.3);
            margin-top: 2rem;
            margin-bottom: 2rem;
        }
        h2 {
            font-weight: 700;
            margin-bottom: 1.5rem;
        }
        table th, table td {
            vertical-align: middle !important;
        }
    </style>
</head>
<body>

<jsp:include page="Nav.jsp"/> 

<%
    List<Cuenta> cuentasCliente = (List<Cuenta>) request.getAttribute("cuentasCliente");
%>

  <main class="container mt-5">
    <div class="row justify-content-center align-items-center">
      <div class="col-lg-8">
        <div class="card p-4">
          <h3 class="fw mb-4 text-center text-primary"><i class="bi bi-arrow-left-right"></i> Nueva Transferencia </h3>

          <form>
          	<!-- Cuenta de Origen -->
			<div class="mb-3">
			  <label for="cuentaOrigen" class="form-label text-primary"><i class="bi bi-wallet2 me-1"></i>Cuenta Origen</label>
			  <select class="form-select" id="cuentaOrigen" name="cuentaOrigen" required>
			    <option selected disabled>Seleccionar cuenta</option>
			    <% if (cuentasCliente != null) {
			        for (Cuenta cuenta : cuentasCliente) { %>
			          <option value="<%= cuenta.getNumDeCuenta() %>">
			            <%= cuenta.getTipoCuenta().getDescripcion() %> - $<%= String.format("%.2f", cuenta.getSaldo()) %>
			          </option>
			    <%  }
			       } %>	
			  </select>
			</div>

            <div class="mb-3">
              <label for="cbu" class="form-label text-primary"><i class="bi bi-upc-scan me-1"></i>CBU/CVU de destino</label>
              <input type="text" class="form-control" id="cbu" placeholder="Ingrese CBU o CVU" required>
            </div>

            <div class="mb-3">
              <label for="monto" class="form-label text-primary"><i class="bi bi-cash-coin me-1"></i>Monto a transferir ($)</label>
              <input type="number" class="form-control" id="monto" placeholder="0.00" required>
            </div>

            <div class="mb-3">
              <label for="detalle" class="form-label text-primary"><i class="bi bi-pencil-square me-1"></i>Detalle</label>
              <textarea class="form-control" id="detalle" rows="3" placeholder="Motivo de la transferencia"></textarea>
            </div>

            <div class="d-flex justify-content-end gap-2">
            <a type="button" class="btn btn-outline-secondary" href="Transferencias.jsp"><i class="bi bi-backspace"></i> Volver</a>
              <button type="reset" class="btn btn-secondary"><i class="bi bi-x-circle me-1"></i>Cancelar</button>
              <button type="submit" class="btn btn-primary"><i class="bi bi-check-circle me-1"></i>Confirmar Transferencia</button>
            </div>
          </form>

        </div>
      </div>
    </div>
  </main>
<jsp:include page="Footer.html" />
</body>
</html>