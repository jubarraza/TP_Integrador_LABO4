<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<%@ include file="fragmentos/VerificarSesion.jspf"%>
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
        <form>

          <div class="mb-3">
            <label for="monto" class="form-label">Monto deseado</label>
            <input type="number" class="form-control" id="monto" placeholder="$">
          </div>

          <div class="mb-3">
            <label for="cuotas" class="form-label">Cantidad de cuotas</label>
            <select class="form-select" id="cuotas">
              <option selected disabled>Seleccionar</option>
              <option>3</option>
              <option>6</option>
              <option>12</option>
              <option>24</option>
              <option>36</option>
              <option>48</option>
            </select>
          </div>

          <div class="mb-3">
            <label for="cuentaDestino" class="form-label">Cuenta para recibir el préstamo</label>
            <select class="form-select" id="cuentaDestino">
              <option selected disabled>Seleccionar</option>
              <option>Cuenta Corriente - 4321 8765 3210</option>
              <option>Caja de Ahorro - 1234 5678 9123</option>
            </select>
          </div>

          <div class="mb-4">
            <label for="motivo" class="form-label">Motivo del préstamo</label>
            <textarea class="form-control" id="motivo" rows="3" placeholder="Ej: compra de electrodomésticos, viaje familiar, etc"></textarea>
          </div>

          <div class="d-grid mb-3">
            <button type="button" class="btn btn-primary">
              <i class="fas fa-calculator me-1"></i> Simular
            </button>
          </div>

        </form>

        <div class="simulacion-card">
          <h5 class="mb-4">Resultado de la simulación</h5>

          <div class="text-start">
            <p><strong>Tasa de interés:</strong> 28% anual</p>
            <p><strong>Monto total a devolver:</strong> $150.000</p>
            <p><strong>Cuota mensual estimada:</strong> $12.500</p>
            <p><strong>Primer vencimiento (cuota 1):</strong> 10/07/2025</p>
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
      <div class="modal-content border-success">
        <div class="modal-header">
          <h5 class="modal-title" id="confirmarPrestamoLabel">
            <i class="fas fa-check-circle text-success me-2"></i>Confirmar solicitud de prestamo
          </h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
        </div>
        <div class="modal-body">
          <p class="mb-2">Por favor revisá la información antes de confirmar:</p>
          <ul class="list-group list-group-flush small">
            <li class="list-group-item"><strong>Monto solicitado:</strong> $100.000</li>
            <li class="list-group-item"><strong>Tasa de interés:</strong> 28% anual</li>
            <li class="list-group-item"><strong>Monto total a devolver:</strong> $150.000</li>
            <li class="list-group-item"><strong>Cuota mensual estimada:</strong> $12.500</li>
            <li class="list-group-item"><strong>Primer vencimiento:</strong> 10/07/2025</li>
          </ul>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
          <button type="button" class="btn btn-success" onclick="confirmarPrestamo()">Confirmar</button>
        </div>
      </div>
    </div>
  </div>

  <jsp:include page="Footer.html" />
  <script>
    function confirmarPrestamo() {
      window.location.href = "ListadoDePrestamos.jsp";
    }
  </script>
</body>
</html>
