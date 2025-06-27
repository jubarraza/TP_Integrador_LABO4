<%@ page import="entidad.Cuenta" %>
<%@ page import="entidad.TipoDeCuenta" %>
<%@ include file="fragmentos/VerificarSesion.jspf"%>

<%
    Cuenta cuenta = (Cuenta) request.getAttribute("cuenta");
    if (cuenta == null) {
%>
    <div class="alert alert-danger">No se pudo cargar la cuenta.</div>
<%
        return;
    }
%>

<form action="ModificarCuentaServlet" method="post">
  <!-- Número de cuenta (hidden) -->
  <input type="hidden" name="numCuenta" value="<%=cuenta.getNumDeCuenta()%>">

  <!-- DNI del cliente -->
  <div class="mb-3">
    <label class="form-label">DNI del Cliente</label>
    <input type="text" class="form-control readonly" value="<%=cuenta.getCliente().getDni()%>" readonly>
  </div>

  <!-- Tipo de cuenta -->
  <div class="mb-3">
    <label for="tipoCuenta" class="form-label">Tipo de Cuenta</label>
    <select id="tipoCuenta" name="tipoCuenta" class="form-select">
      <option disabled>Seleccionar tipo</option>
      <option value="1" <%= cuenta.getTipoCuenta().getIdTipoCuenta() == 1 ? "selected" : "" %>>Caja de Ahorro</option>
      <option value="2" <%= cuenta.getTipoCuenta().getIdTipoCuenta() == 2 ? "selected" : "" %>>Cuenta Corriente</option>
    </select>
  </div>

  <!-- N° Cuenta -->
  <div class="mb-3">
    <label class="form-label">Número de Cuenta</label>
    <input type="text" class="form-control readonly" value="<%=cuenta.getNumDeCuenta()%>" readonly>
  </div>

  <!-- CBU -->
  <div class="mb-3">
    <label class="form-label">CBU</label>
    <input type="text" class="form-control readonly" value="<%=cuenta.getCbu()%>" readonly>
  </div>

  <!-- Saldo -->
  <div class="mb-3">
    <label class="form-label">Saldo</label>
    <input type="text" class="form-control readonly" value="$<%=String.format("%.2f", cuenta.getSaldo())%>" readonly>
  </div>

  <!-- Estado -->
  <div class="mb-3">
    <label class="form-label">Estado</label>
    <input type="text" class="form-control readonly" value="<%=cuenta.Estado() ? "ACTIVA" : "INACTIVA"%>" readonly>
  </div>

  <!-- Botones -->
  <div class="d-flex justify-content-end gap-3">
    <button type="submit" class="btn btn-primary">
      <i class="fas fa-save me-1"></i> Guardar
    </button>
    <a href="ListarCuentasServlet" class="btn btn-secondary">Cancelar</a>
  </div>
</form>
