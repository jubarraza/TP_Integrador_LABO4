<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="entidad.Cuenta" %>
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
      background-color: #e9ecef !important;
    }
  </style>
</head>
<body>
<jsp:include page="Nav.jsp" />

<%
    Cuenta cuenta = (Cuenta) request.getAttribute("cuenta");
%>

  <main class="container py-5">
    <h2 class="text-center mb-4">Editar Cuenta <i class="bi bi-pen"></i></h2>

    <div class="form-container">
      <form method="post" action="ModificarCuentaServlet" id="formCuenta">
          <input type="hidden" name="accion" value="modificar" />
          <input type="hidden" name="numeroCuenta" value="<%= cuenta.getNumDeCuenta() %>" />
        <!-- DNI del cliente con botón validar -->
        <div class="mb-3">
            <label for="dni" class="form-label">DNI del Cliente</label>
            <div class="input-group">
                <input type="text" class="form-control readonly" id="dni" value="<%= cuenta.getCliente().getDni() %>" readonly>
            </div>
        </div>

        <!-- Tipo de cuenta -->
        <div class="mb-3">
        <label for="tipoCuenta" class="form-label">Tipo de Cuenta</label>
        <select id="tipoCuenta" name="tipoCuenta" class="form-select">
            <option disabled>Seleccionar tipo</option>
            <option value="1" <%= cuenta.getTipoCuenta().getIdTipoCuenta() == 1 ? "selected" : "" %>>Cuenta sueldo</option>
            <option value="2" <%= cuenta.getTipoCuenta().getIdTipoCuenta() == 2 ? "selected" : "" %>>Caja de ahorro</option>
            <option value="3" <%= cuenta.getTipoCuenta().getIdTipoCuenta() == 3 ? "selected" : "" %>>Cuenta corriente</option>
        </select>
        </div>

        <!-- Número de cuenta -->
        <div class="mb-3">
            <label for="numeroCuenta" class="form-label">Número de Cuenta</label>
            <input type="text" class="form-control readonly" name="numeroCuenta" value="<%= cuenta.getNumDeCuenta() %>" readonly>
        </div>

        <!-- CBU -->
        <div class="mb-3">
            <label for="cbu" class="form-label">CBU</label>
            <input type="text" class="form-control readonly" value="<%= cuenta.getCbu() %>" readonly>
        </div>

        <!-- Saldo -->
        <div class="mb-3">
            <label for="saldo" class="form-label">Saldo</label>
            <input type="text" class="form-control readonly" value="$<%= cuenta.getSaldo() %>" readonly>
        </div>

        <!-- Estado -->
        <div class="form-check form-switch">
          <input class="form-check-input" type="checkbox" id="estadoSwitch" name="estado" <%= cuenta.Estado() ? "checked" : "" %>
            onchange="document.getElementById('lblEstado').innerText = this.checked ? 'Activa' : 'Inactiva';"         >
          <label class="form-check-label" id="lblEstado" for="estadoSwitch">
            <%= cuenta.Estado() ? "Activa" : "Inactiva" %>
          </label>
        </div>

        <!-- Botones de acción -->
        <div class="d-flex justify-content-end gap-3 mt-4">
            <!-- Ver btn solo si la cuenta se encuentra inactiva -->
            <% if (!cuenta.Estado()) { %>
                <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#modalEliminar">
                    <i class="fas fa-trash-alt me-1"></i> Eliminar
                </button>
            <% } %>
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalGuardar">
                <i class="fas fa-save me-1"></i> Guardar
            </button>
        </div>
      </form>
    </div>
  </main>

  <!-- Modal Confirmación de Guardado -->
  <div class="modal fade" id="modalGuardar" tabindex="-1" aria-labelledby="modalGuardarLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="modalGuardarLabel">Confirmar guardado</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
      </div>
      <div class="modal-body">
        ¿Deseás guardar los cambios en esta cuenta?
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
        <button type="submit" class="btn btn-primary" form="formCuenta">Guardar</button>
      </div>
    </div>
  </div>
</div>

  <!-- Modal Confirmación de Eliminación -->
  <div class="modal fade" id="modalEliminar" tabindex="-1" aria-labelledby="modalEliminarLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="modalEliminarLabel">Confirmar eliminación</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
        </div>
        <div class="modal-body">
          ¿Estás seguro que querés eliminar esta cuenta?
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
          <button type="button" class="btn btn-danger" >Eliminar</button>
        </div>
      </div>
    </div>
  </div>
<jsp:include page="Footer.html" />
<!-- <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"></script> -->
</body>
</html>
